package com.herryboro.order66.service;

import com.herryboro.order66.dto.ClientMemberDTO;
import com.herryboro.order66.exception.PasswordMismatchException;
import com.herryboro.order66.mapper.ClientMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientMemberMapper clientMemberMapper;
    public void signUp(ClientMemberDTO user) {
        if (!user.getClientPassword().equals(user.getPasswardCheck())) {
            throw new PasswordMismatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        clientMemberMapper.insertMember(user);
    }
}
