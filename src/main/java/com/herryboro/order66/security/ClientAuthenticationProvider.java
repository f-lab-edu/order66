package com.herryboro.order66.security;

import com.herryboro.order66.dto.client.ClientInfoDTO;
import com.herryboro.order66.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ClientAuthenticationProvider implements AuthenticationProvider {

    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        ClientInfoDTO client = clientService.getUserByClientId(username);
        if (client != null && passwordEncoder.matches(password, client.getClientPassword())) {
            List<GrantedAuthority> roles =  new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
            return new UsernamePasswordAuthenticationToken(client.getId(), null, roles);
        } else {
            throw new BadCredentialsException("user id 또는 비밀번호가 잘못되었습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
