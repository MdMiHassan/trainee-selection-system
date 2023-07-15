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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/applicants/register", "/auth/login", "/admits/verify/{admitCardId}")
                        .permitAll()
                        .requestMatchers("/applicants", "/applicants/{applicantId}", "/applicants/{applicantId}/actions/lock"
                                , "/circulars/{circularId}/applications", "/circulars/{circularId}/rounds/next/applications/{applicationId}/actions/approve"
                                , "/circulars/{circularId}/rounds/current/actions/end", "/circulars/{circularId}/rounds"
                                , "/circulars/{circularId}/rounds/{roundId}", "/circulars/{circularId}/rounds/{roundId}/candidates"
                                , "/circulars/{circularId}/rounds/{roundId}/candidates/{candidateId}", "/evaluators")
                        .hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/circulars/{circularId}/apply")
                        .hasAuthority(Role.APPLICANT.name())
                        .requestMatchers("/resource/upload", "/resource/{roundId}")
                        .hasAnyAuthority(Role.ADMIN.name(), Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.GET,"/circulars", "/circulars/{circularId}")
                        .hasAnyAuthority(Role.ADMIN.name(), Role.APPLICANT.name())
                        .requestMatchers(HttpMethod.POST,"/circulars", "/circulars/{circularId}")
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/evaluators/{evaluatorId}/candidates")
                        .hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/evaluators/{evaluatorId}/candidates")
                        .hasAuthority(Role.EVALUATOR.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}