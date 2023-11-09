package com.herryboro.order66.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class StoreSecurityConfig {

    private final StoreAuthenticationProvider storeAuthenticationProvider;

    @Bean
    public SecurityFilterChain storeSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeRequests((requests) -> requests
                .requestMatchers( "/store/signUp", "/store/login")
                .permitAll()
                .requestMatchers("/store/**").hasAnyAuthority("ROLE_STORE")
                .anyRequest().authenticated()
            )
            .formLogin((form) ->
                form
                    .loginProcessingUrl("/store/login")
                    .permitAll()
                    .usernameParameter("userId")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/store/home")

            )
            .logout((logout) ->
                logout
                    .logoutUrl("/store/logout")
                    .logoutSuccessUrl("/store/login")
                    .invalidateHttpSession(true))
            .authenticationProvider(storeAuthenticationProvider);

        return http.build();
    }
}
