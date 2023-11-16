package com.herryboro.order66.controller;

import com.herryboro.order66.dto.ClientInfoDTO;
import com.herryboro.order66.dto.UpdateClientInfoDto;
import com.herryboro.order66.mapper.ClientMapper;
import com.herryboro.order66.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Mock
    private ClientMapper clientMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        // mock에서 utf8로 인코딩 설정
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))  // Force UTF-8 Encoding
                .build();
    }

    /*
        ▣ Controller signUpClientUser 단위 test
        ▣ 설정된 valid로 인해 ClientMemberDTO의 input data 에러 발생시 test 실패
     */
    @Test
    void signUpClientUser() throws Exception {
        doNothing().when(clientService).signUp(any(ClientInfoDTO.class), any(PasswordEncoder.class));

        mockMvc.perform(post("/clients/signUp")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("clientId", "herryboro4")
            .param("clientName", "백경찬")
            .param("clientNickname", "해리보로5")
            .param("clientPassword", "goflqhfh!89")
            .param("passwardCheck", "goflqhfh!89")
            .param("clientEmail", "herryboro5@naver.com")
            .param("clientPhone", "010111111115")
        )
        .andExpect(status().isOk())
        .andExpect(content().string("회원 가입이 완료되었습니다."));

        verify(clientService).signUp(any(ClientInfoDTO.class), any(PasswordEncoder.class));
    }

    @Test
    void updateClientInfo() throws Exception {
        doNothing().when(clientService).updateClientInfo(any(UpdateClientInfoDto.class), eq(passwordEncoder));

        mockMvc.perform(
            post("/clients/updateClientInfo")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "28")
                .param("clientName", "수정백경찬")
                .param("clientNickname", "해리보로4수정")
                .param("clientPassword", "goflqhfh!894수정")
                .param("clientEmail", "herryboro4@naver.com")
                .param("clientPhone", "010111111114")
        )
        .andExpect(status().isOk());

        verify(clientService).updateClientInfo(any(UpdateClientInfoDto.class), eq(passwordEncoder));
    }
}