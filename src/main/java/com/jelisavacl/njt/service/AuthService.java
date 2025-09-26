package com.jelisavacl.njt.service;

import com.jelisavacl.njt.controller.AuthController;
import com.jelisavacl.njt.entity.User;
import com.jelisavacl.njt.repository.UserRepository;
import com.jelisavacl.njt.security.CustomUserDetails;
import com.jelisavacl.njt.security.CustomUserDetailsService;
import com.jelisavacl.njt.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.rmi.server.LogStream.log;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


    public boolean register(User user) {
        log.info("Pocela registracija");
        // Check if username or email already exist
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String token = UUID.randomUUID().toString();
        user.setConfirmationToken(token);
        // Save user
        userRepository.save(user);

        sendConfirmationEmail(user.getEmail(), token);

        return true;
    }

    private void sendConfirmationEmail(String to, String token) {
        log.info("Slanje mejla");
        String link = "http://localhost:8080/api/auth/confirm?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Potvrda registracije");
        message.setText("Kliknite na link: " + link);

        mailSender.send(message);
    }

    public String login(AuthController.AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            CustomUserDetails userDetails =
                (CustomUserDetails) userDetailsService.loadUserByUsername(request.getUsername());
            return jwtUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            return null;
        }
    }

    public void confirm(String token) {
        User user = userRepository.findByConfirmationToken(token)
            .orElseThrow(() -> new RuntimeException("Token nije ispravan."));

        user.setEnabled(true);
        user.setConfirmationToken(null);
        userRepository.save(user);
    }
}
