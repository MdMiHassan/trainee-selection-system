package com.example.tss.config;


import com.example.tss.constants.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig{
    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(c -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(
                                List.of("http://localhost:5173"));
                        config.setAllowedMethods(
                                List.of("GET", "POST", "PUT", "DELETE","PATCH"));
                        config.applyPermitDefaultValues();
                        return config;
                    };
                    c.configurationSource(source);})
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/notices","/api/v1/applicants/register","/api/v1/applicants/register/email/verify",
                                "/api/v1/auth/login","/api/v1/resource/{resourceId}")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/circulars","/api/v1/admits/verify/{admitCardId}")
                        .permitAll()
                        .requestMatchers("/api/v1/applicants/current/applications")
                        .hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.POST,"/api/v1/circulars/{circularId}/apply","/api/v1/applicants/profile")
                        .hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.GET,"/api/v1/applicants/profile","/api/v1/admits/current/{circularId}","/api/v1/notices")
                        .hasAuthority(Role.APPLICANT.name())
                        .requestMatchers("/api/v1/applicants", "/api/v1/applicants/{applicantId}", "/api/v1/applicants/{applicantId}/actions/lock"
                                , "/api/v1/circulars/{circularId}/applications", "/api/v1/circulars/{circularId}/rounds/next/applications/{applicationId}/actions/approve"
                                , "/api/v1/circulars/{circularId}/rounds/current/actions/end", "/api/v1/circulars/{circularId}/rounds/{roundId}",
                                "/api/v1/circulars/{circularId}/rounds/{roundId}/candidates", "/api/v1/circulars/{circularId}/rounds/{roundId}/candidates/{candidateId}"
                                , "/api/v1/evaluators","/api/v1/evaluators/{evaluatorId}/candidates/assign")
                        .hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/circulars/{circularId}","/api/v1/circulars/{circularId}/rounds")
                        .hasAnyAuthority(Role.ADMIN.name(),Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/info/{circularId}","/api/v1/circulars","/api/v1/circulars/{circularId}",
                                "/api/v1/evaluators/{evaluatorId}/candidates","/api/v1/circulars/{circularId}/rounds","/api/v1/notices")
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/v1/evaluators/{evaluatorId}/candidates")
                        .hasAuthority(Role.EVALUATOR.name())
                        .requestMatchers(HttpMethod.POST,"/api/v1/evaluators/current/candidates/marks")
                        .hasAuthority(Role.EVALUATOR.name())
                        .requestMatchers("/api/v1/resource/upload")
                        .hasAnyAuthority(Role.ADMIN.name(), Role.APPLICANT.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}