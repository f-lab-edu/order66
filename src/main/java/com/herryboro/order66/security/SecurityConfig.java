//package com.herryboro.order66.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//         .authorizeHttpRequests((requests) -> requests
//            .requestMatchers("/login", "/signUp", "/home").permitAll()
//            .anyRequest().authenticated()
//         )
//        .formLogin((form) -> form
//            .loginPage("/login")
//            .permitAll()
//            .defaultSuccessUrl("/")
//        )
//        .logout((logout) -> logout.permitAll().logoutSuccessUrl("/").invalidateHttpSession(true));
//        return http.build();
//    }
//}
