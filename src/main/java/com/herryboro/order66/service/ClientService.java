package com.herryboro.order66.service;

import com.herryboro.order66.dto.ClientMemberDTO;
import com.herryboro.order66.exception.PasswordMismatchException;
import com.herryboro.order66.mapper.ClientMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientMemberMapper clientMemberMapper;
//    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void signUp(ClientMemberDTO user) {
        if (!user.getClientPassword().equals(user.getPasswardCheck())) {
            throw new PasswordMismatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
//        user.setClientPassword(passwordEncoder.encode(user.getClientPassword()));
        clientMemberMapper.insertMember(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        ClientMemberDTO user = Optional
//            .ofNullable(clientMemberMapper.findByUsername(username))
//            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        return new User(user.getClientId(), user.getClientPassword(), new ArrayList<>());
//    }

//    public ClientMemberDTO getUserById(String id) {
//        return clientMemberMapper.getUserById(id);
//    }

//    public PasswordEncoder passwordEncoder() {
//        return this.passwordEncoder;
//    }
}
