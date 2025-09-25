package com.jelisavacl.njt.controller;

import com.jelisavacl.njt.entity.User;
import com.jelisavacl.njt.repository.UserRepository;
import com.jelisavacl.njt.security.CustomUserDetails;
import com.jelisavacl.njt.security.CustomUserDetailsService;
import com.jelisavacl.njt.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {   // TODO: Rasporediti samo sta gde je treba da ide!

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Check if username or email already exist
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Korisnik vec postoji.");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String token = UUID.randomUUID().toString();
        user.setConfirmationToken(token);
        sendConfirmationEmail(user.getEmail(), token);

        // Save user
        userRepository.save(user);

        return ResponseEntity.ok("Registracija uspela!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            CustomUserDetails userDetails =
                (CustomUserDetails) userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam("token") String token) {
        User user = userRepository.findByConfirmationToken(token)
            .orElseThrow(() -> new RuntimeException("Token nije ispravan."));

        user.setEnabled(true);
        user.setConfirmationToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Nalog aktiviran.");
    }

    private void sendConfirmationEmail(String to, String token) {
        String link = "http://localhost:8080/api/auth/confirm?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Potvrda registracije");
        message.setText("Kliknite na link: " + link);

        mailSender.send(message);
    }

    @Setter
    @Getter
    public static class AuthRequest {
        private String username;
        private String password;

    }
}
