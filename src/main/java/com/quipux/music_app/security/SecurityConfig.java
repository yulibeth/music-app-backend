package com.quipux.music_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtUtil jwtUtil, JwtFilter jwtFilter) {
        this.jwtUtil = jwtUtil;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Deshabilitar CSRF
            .authorizeRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()  // Permitir acceso sin autenticación a /auth/login
                .requestMatchers("/auth/**").permitAll()  // Permitir todas las rutas de autenticación sin JWT
                .anyRequest().authenticated()  // Requiere autenticación para otras rutas
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Filtro JWT

        return http.build();
    }

    // Configuración global de CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permitir todas las rutas
                .allowedOrigins("http://localhost:4200")  // Cambia esta URL por la de tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);  // Si es necesario permitir credenciales
    }
}
