package com.jelisavacl.njt.controller;

import com.jelisavacl.njt.entity.User;
import com.jelisavacl.njt.repository.UserRepository;
import com.jelisavacl.njt.security.CustomUserDetails;
import com.jelisavacl.njt.security.CustomUserDetailsService;
import com.jelisavacl.njt.security.JwtUtil;
import com.jelisavacl.njt.service.AuthService;
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
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (!authService.register(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Korisnik vec postoji.");
        }
        else return ResponseEntity.ok("Registracija uspela!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        if(token != null) {
            return ResponseEntity.ok(token);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nije dobro nesto.");

    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam("token") String token) {

        try {
            authService.confirm(token);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Nije dobro nesto.");
        }


    }



    @Setter
    @Getter
    public static class AuthRequest {
        private String username;
        private String password;

    }
}
