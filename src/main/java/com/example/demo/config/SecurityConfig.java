package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/dashboard.html",
                                "/admin.html",
                                "/loans.html",
                                "/transactions.html",
                                "/payments.html",
                                "/profile.html",
                                "/notifications.html",
                                "/audit_log.html",
                                "admin-login.html",
                                "/css/**",
                                "/js/**",
                                "/api/auth/**",
                                "/account-create.html", "/api/accounts/**"
                        ).permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.disable())
                .formLogin(form -> form
                        .loginPage("/index.html")
                        .loginProcessingUrl("/login") // Spring Security’s login processing
                        .defaultSuccessUrl("/dashboard.html", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password") // no-op encoder for demo
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
