package com.herryboro.order66.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /**
        jwt 관련 코드
    */
//    private final TokenProvider tokenProvider;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * ▣ spring security, session 관련 설정
         */

          http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers( "/client/signUp").permitAll()
                .anyRequest().authenticated() // 위 패턴과 일치하지 않은 패턴은 모두 인증이 필요
            )
            .formLogin((form) ->
                form
                    .loginProcessingUrl("/auth")
                    .permitAll()
                    .usernameParameter("userId")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/client/home")
            )
            .logout((logout) ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/client/home")
                    .invalidateHttpSession(true));
        return http.build();


        /**
         * jwt, security 관련 설정
         */
//        http
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests((requests) ->
//                requests
//                    .requestMatchers( "/jwtApi/login").permitAll()
//                    .anyRequest().authenticated() // 위 패턴과 일치하지 않은 패턴은 모두 인증이 필요
//            )
//            .exceptionHandling(authenticationManager -> {
//                authenticationManager
//                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//                    .accessDeniedHandler(new JwtAccessDeniedHandler());
//
//            })
//            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용
//
//        return http.build();
    }
}
