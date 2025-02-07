package com.quipux.music_app.controller;

import com.quipux.music_app.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        // Generar un JWT para el usuario
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(token);  // Retorna el token al usuario
    }
}

