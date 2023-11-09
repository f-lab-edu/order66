//package com.herryboro.order66.security;
//
//import com.herryboro.order66.dto.ClientMemberDTO;
//import com.herryboro.order66.dto.StoreInfoDto;
//import com.herryboro.order66.service.ClientService;
//import com.herryboro.order66.service.StoreService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class AuthProvider implements AuthenticationProvider {
//
//    private final ClientService clientService;
//    private final StoreService storeService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String id = (String) authentication.getPrincipal();
//        String password = (String) authentication.getCredentials();
//        String errorMsseage;
//
////        PasswordEncoder passwordEncoder = clientService.passwordEncoder();
////        UsernamePasswordAuthenticationToken token;
//
//        // client 권한 저장
//        ClientMemberDTO userDto = clientService.getUserByClientId(id);
//
//        if (userDto != null && passwordEncoder.matches(password, userDto.getClientPassword())) { // 일치하는 user 정보가 있는지 확인
//            List<GrantedAuthority> roles = new ArrayList<>();
//            roles.add(new SimpleGrantedAuthority("ROLE_CLIENT")); // 권한 부여
//
//            // 인증된 user 정보를 담아 SecurityContextHolder에 저장되는 token
//            token = new UsernamePasswordAuthenticationToken(userDto.getId(), null, roles);
//            return token;
//        }
//        else if(userDto == null) errorMsseage = "계정이 존재하지 않습니다.";
//        else errorMsseage = "비밀번호가 일치하지 않습니다.";
//
//        // store 권한 저장
//        StoreInfoDto storeDto = storeService.getStoreByStoreId(id);
//        if (storeDto != null && passwordEncoder.matches(password, storeDto.getStorePassword())) {
//            List<GrantedAuthority> roles = new ArrayList<>();
//            roles.add(new SimpleGrantedAuthority("ROLE_STORE"));
//
//            // 인증된 user 정보를 담아 SecurityContextHolder에 저장되는 token
//            token = new UsernamePasswordAuthenticationToken(storeDto.getId(), null, roles);
//            return token;
//        }
//
//
//        throw new BadCredentialsException(errorMsseage);
//    }
//
//    @Bean
//    public AuthenticationProvider clientAuthenticationProvider() {
//        // Client-specific authentication logic
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
