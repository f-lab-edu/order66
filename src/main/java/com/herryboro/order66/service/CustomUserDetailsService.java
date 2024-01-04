package com.herryboro.order66.service;

import com.herryboro.order66.dto.client.ClientInfoDTO;
import com.herryboro.order66.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    public final ClientMapper clientMapper;

    @Override
    public UserDetails loadUserByUsername(final String clientId) {

        ClientInfoDTO client = clientMapper.getUserByClientId(clientId);
        if (client == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        return new User(client.getClientId(), client.getClientPassword(), new ArrayList<>());
    }
}
