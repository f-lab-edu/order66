package com.herryboro.order66.controller;

import com.herryboro.order66.dto.client.ClientInfoDTO;
import com.herryboro.order66.dto.client.UpdateClientInfoDto;
import com.herryboro.order66.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        // verify시 캡쳐
        ArgumentCaptor<ClientInfoDTO> clientInfoCaptor = ArgumentCaptor.forClass(ClientInfoDTO.class);

        mockMvc.perform(post("/clients/signUp")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("clientId", "herryboro")
            .param("clientName", "백경찬")
            .param("clientNickname", "해리보로")
            .param("clientPassword", "goflqhfh!89")
            .param("passwardCheck", "goflqhfh!89")
            .param("clientEmail", "herryboro@naver.com")
            .param("clientPhone", "01011111111")
        )
        .andExpect(status().isOk())
        .andExpect(content().string("회원 가입이 완료되었습니다."));

        verify(clientService).signUp(clientInfoCaptor.capture(), any(PasswordEncoder.class));
        ClientInfoDTO captorValue = clientInfoCaptor.getValue();

        assertThat(captorValue.getClientId()).isEqualTo("herryboro");
        assertThat(captorValue.getClientName()).isEqualTo("백경찬");
        assertThat(captorValue.getClientNickname()).isEqualTo("해리보로");
        assertThat(captorValue.getClientPassword()).isEqualTo("goflqhfh!89");
        assertThat(captorValue.getClientEmail()).isEqualTo("herryboro@naver.com");
        assertThat(captorValue.getClientPhone()).isEqualTo("01011111111");
    }

    @Test
    void updateClientInfo() throws Exception {
        ArgumentCaptor<UpdateClientInfoDto> updateClientCaptor = ArgumentCaptor.forClass(UpdateClientInfoDto.class);

        mockMvc.perform(
            put("/clients/updateClientInfo")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "28")
                .param("clientName", "수정백경찬")
                .param("clientNickname", "해리보로수정")
                .param("clientPassword", "qlalfqjsgh!89")
                .param("clientEmail", "herryboro@naver.com")
                .param("clientPhone", "010111111114")
        )
        .andExpect(status().isOk());

        verify(clientService).updateClientInfo(updateClientCaptor.capture(), any(PasswordEncoder.class));
        UpdateClientInfoDto capture = updateClientCaptor.getValue();

        assertThat(capture.getId()).isEqualTo(28);
        assertThat(capture.getClientName()).isEqualTo("수정백경찬");
        assertThat(capture.getClientNickname()).isEqualTo("해리보로수정");
        assertThat(capture.getClientPassword()).isEqualTo("qlalfqjsgh!89");
        assertThat(capture.getClientEmail()).isEqualTo("herryboro@naver.com");
        assertThat(capture.getClientPhone()).isEqualTo("010111111114");
    }
}