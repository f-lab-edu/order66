package com.herryboro.order66.service;

import com.herryboro.order66.dto.ClientMemberDTO;
import com.herryboro.order66.dto.UpdateClientInfoDto;
import com.herryboro.order66.exception.PasswordMismatchException;
import com.herryboro.order66.exception.RegistrationException;
import com.herryboro.order66.mapper.ClientMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientMemberMapper clientMemberMapper;

    public void signUp(ClientMemberDTO user, PasswordEncoder passwordEncoder) {
        if (!user.getClientPassword().equals(user.getPasswardCheck())) {
            throw new PasswordMismatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        user.setClientPassword(passwordEncoder.encode(user.getClientPassword()));

        try {
            clientMemberMapper.insertMember(user);
        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException(e.getMessage());
        }

    }

    // id로 user 정보 조회
    public ClientMemberDTO getUserById(Long id) {
        return clientMemberMapper.getUserById(id);
    }

    // clientId로 user 정보 조회
    public ClientMemberDTO getUserByClientId(String clientId) {
        return clientMemberMapper.getUserByClientId(clientId);
    }


    public void updateClientInfo(UpdateClientInfoDto user, PasswordEncoder passwordEncoder) {
        user.setClientPassword(passwordEncoder.encode(user.getClientPassword()));
        clientMemberMapper.updateClientInfo(user);
    }
}
