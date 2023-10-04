package com.herryboro.order66.service;

import com.herryboro.order66.dto.ClientMemberDTO;
import com.herryboro.order66.mapper.ClientMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientMemberMapper clientMemberMapper;
    public void signUp(ClientMemberDTO user) {
        clientMemberMapper.insertMember(user);
    }
}
