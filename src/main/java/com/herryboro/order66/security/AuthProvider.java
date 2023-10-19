//package com.herryboro.order66.security;
//
//import com.herryboro.order66.dto.ClientMemberDTO;
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
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String id = (String) authentication.getPrincipal();
//        String password = (String) authentication.getCredentials();
//
////        PasswordEncoder passwordEncoder = clientService.passwordEncoder();
//        UsernamePasswordAuthenticationToken token;
//        ClientMemberDTO userDto = clientService.getUserById(id);
//
//        if (userDto != null && passwordEncoder.matches(password, userDto.getClientPassword())) { // 일치하는 user 정보가 있는지 확인
//            List<GrantedAuthority> roles = new ArrayList<>();
//            roles.add(new SimpleGrantedAuthority("USER")); // 권한 부여
//
//            // 인증된 user 정보를 담아 SecurityContextHolder에 저장되는 token
//            token = new UsernamePasswordAuthenticationToken(userDto.getClientId(), null, roles);
//
//            return token;
//        }
//
//        throw new BadCredentialsException("No such user or wrong password.");
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//}
