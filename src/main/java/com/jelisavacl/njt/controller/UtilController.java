package com.jelisavacl.njt.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/util")
public class UtilController {

    // Ovo za sad drzi vodu, smisliti nesto elegantnije
    @PostMapping("/ping")
    public ResponseEntity<String> check() {
        return ResponseEntity.ok("Ne bi video ovo da nisi ulogovan.");
    }
}
