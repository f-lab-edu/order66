package com.herryboro.order66.service;

import com.herryboro.order66.dto.client.ClientInfoDTO;
import com.herryboro.order66.dto.client.UpdateClientInfoDto;
import com.herryboro.order66.exception.DuplicateRegistrationException;
import com.herryboro.order66.exception.InvalidInputException;
import com.herryboro.order66.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientMapper clientMapper;

    public void signUp(ClientInfoDTO clientInfo, PasswordEncoder passwordEncoder) {
        if (!clientInfo.getClientPassword().equals(clientInfo.getPasswardCheck())) {
            throw new InvalidInputException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        clientInfo.setClientPassword(passwordEncoder.encode(clientInfo.getClientPassword()));

        try {
            clientMapper.insertMember(clientInfo);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRegistrationException(e.getMessage());
        }
    }

    // id로 user 정보 조회
    public ClientInfoDTO getUserById(Long id) {
        return clientMapper.getUserById(id);
    }

    // clientId로 user 정보 조회
    public ClientInfoDTO getUserByClientId(String clientId) {
        return clientMapper.getUserByClientId(clientId);
    }

    public void updateClientInfo(UpdateClientInfoDto clientInfo, PasswordEncoder passwordEncoder) {
        clientInfo.setClientPassword(passwordEncoder.encode(clientInfo.getClientPassword()));
        clientMapper.updateClientInfo(clientInfo);
    }
}
