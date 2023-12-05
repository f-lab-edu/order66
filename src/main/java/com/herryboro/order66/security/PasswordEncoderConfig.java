package com.herryboro.order66.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityConfig에 @Bean으로 PasswordEncoder를 등록했을때 순환 참조 에러가 계속 발생함
 * PasswordEncoderConfig를 따로 만들어서 bean등록
 */
@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
