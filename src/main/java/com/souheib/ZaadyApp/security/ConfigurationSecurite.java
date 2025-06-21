package com.souheib.ZaadyApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurite {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FiltreDeSecurite filtreDeSecurite() {
        return new FiltreDeSecurite();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactivation CSRF pour appels REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/utilisateurs/authentifier").permitAll()
                .requestMatchers("/accueil","/choixVote", "/evenements/creerEvenement").authenticated()
                .requestMatchers("/evenements/**").hasRole("ADMIN") // ROLE_ADMIN
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            );

        // Ajoute notre filtre personnalisé
        http.addFilterBefore(filtreDeSecurite(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public static class FiltreDeSecurite extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            String path = request.getRequestURI();

            // Routes publiques — autorisées sans authentification
            if (path.equals("/") || path.startsWith("/login") || 
                path.startsWith("/css") || path.startsWith("/js") || 
                path.startsWith("/images") || path.equals("/utilisateurs/authentifier")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Vérifie si l’utilisateur est authentifié
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non authentifié ou session expirée");
                return;
            }

            // Sinon, on laisse passer
            filterChain.doFilter(request, response);
        }
    }
}
