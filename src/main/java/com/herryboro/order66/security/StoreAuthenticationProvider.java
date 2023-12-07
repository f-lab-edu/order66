package com.herryboro.order66.security;

import com.herryboro.order66.dto.StoreInfoDto;
import com.herryboro.order66.service.StoreService;
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
public class StoreAuthenticationProvider implements AuthenticationProvider {

    private final StoreService storeService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String storeId = authentication.getName();
        String password = authentication.getCredentials().toString();

        StoreInfoDto store = storeService.getStoreByStoreId(storeId);

        if (store != null && passwordEncoder.matches(password, store.getStorePassword())) {
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_STORE"));
            return new UsernamePasswordAuthenticationToken(store.getId(), null, roles);
        } else {
            throw new BadCredentialsException("id 또는 비밀번호가 잘못되었습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
