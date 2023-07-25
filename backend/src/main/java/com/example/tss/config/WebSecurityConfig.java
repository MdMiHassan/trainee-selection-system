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
                        .requestMatchers("v1/notices","/applicants/register","/applicants/register/email/verify",
                                "/auth/login","/resource/{resourceId}")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,"/circulars","/admits/verify/{admitCardId}")
                        .permitAll()
                        .requestMatchers("/applicants/current/applications")
                        .hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.POST,"/circulars/{circularId}/apply","/applicants/profile",
                                "/{circularId}/rounds/{roundId}/admits/generate")
                        .hasAuthority(Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.GET,"/applicants/profile","/admits/current/{circularId}")
                        .hasAuthority(Role.APPLICANT.name())
                        .requestMatchers("/applicants", "/applicants/{applicantId}", "/applicants/{applicantId}/actions/lock"
                                , "/circulars/{circularId}/applications", "/circulars/{circularId}/rounds/next/applications/{applicationId}/actions/approve"
                                , "/circulars/{circularId}/rounds/current/actions/end", "/circulars/{circularId}/rounds"
                                , "/circulars/{circularId}/rounds/{roundId}", "/circulars/{circularId}/rounds/{roundId}/candidates"
                                , "/circulars/{circularId}/rounds/{roundId}/candidates/{candidateId}"
                                , "/evaluators","/evaluators/{evaluatorId}/candidates/assign")
                        .hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/info/{circularId}","/circulars","/circulars/{circularId}",
                                "/evaluators/{evaluatorId}/candidates")
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/evaluators/{evaluatorId}/candidates")
                        .hasAuthority(Role.EVALUATOR.name())
                        .requestMatchers(HttpMethod.POST,"/evaluators/current/candidates/marks")
                        .hasAuthority(Role.EVALUATOR.name())
                        .requestMatchers("/resource/upload")
                        .hasAnyAuthority(Role.ADMIN.name(), Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.GET, "/circulars/{circularId}","/circulars/{circularId}/meta")
                        .hasAnyAuthority(Role.ADMIN.name(),Role.APPLICANT.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}