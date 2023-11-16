package com.herryboro.order66.service;

import com.herryboro.order66.dto.ClientInfoDTO;
import com.herryboro.order66.exception.DuplicateRegistrationException;
import com.herryboro.order66.exception.InvalidInputException;
import com.herryboro.order66.mapper.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ClientServiceTest {
    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientService clientService;

    private ClientInfoDTO user;

    @BeforeEach
    void setUp() {
        user = new ClientInfoDTO();
        user.setClientPassword("password");
        user.setPasswardCheck("password");
    }

    /*
        ▣ ClientService signUp 단위 test
    */

    // 회원 가입 성공 case
    @Test
    void signUp_success() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doNothing().when(clientMapper).insertMember(user);

        clientService.signUp(user, passwordEncoder);

        verify(clientMapper).insertMember(user);
    }

    // 비밀번호 <-> 비밀번호 확인 불일치 test
    @Test
    void signUp_passwordMismatchException() {
        user.setClientPassword("differentPassword");

        assertThrows(InvalidInputException.class, () -> {
            clientService.signUp(user, passwordEncoder);
        });
    }

    //
    @Test
    void signUp_duplicateRegistrationException() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        doThrow(new DuplicateRegistrationException("이미 가입되어 있는 계정입니다.")).when(clientMapper).insertMember(any(ClientInfoDTO.class));

        assertThrows(DuplicateRegistrationException.class, () -> {
            clientService.signUp(user, passwordEncoder);
        });
    }
}