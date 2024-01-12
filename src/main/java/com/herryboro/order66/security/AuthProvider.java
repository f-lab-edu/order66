//package com.herryboro.order66.security;
//
//import com.herryboro.order66.dto.client.ClientInfoDTO;
//import com.herryboro.order66.service.ClientService;
//import lombok.RequiredArgsConstructor;
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
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String id = (String) authentication.getPrincipal();
//        ClientInfoDTO user = clientService.getUserByClientId(id);
//        UsernamePasswordAuthenticationToken token;
//
//        if (user == null) {
//            throw new BadCredentialsException("계정이 존재하지 않습니다.");
//        }
//
//        String password = (String) authentication.getCredentials();
//
//        if (!passwordEncoder.matches(password, user.getClientPassword())) {
//            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//        } else {
//            List<GrantedAuthority> roles = new ArrayList<>();
//            roles.add(new SimpleGrantedAuthority("ROLE_CLIENT")); // 권한 부여
//
//            // 인증된 user 정보를 담아 SecurityContextHolder에 저장되는 token
//            token = new UsernamePasswordAuthenticationToken(user.getId(), null, roles);
//            return token;
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
