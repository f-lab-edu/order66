package com.herryboro.order66.controller;

import com.herryboro.order66.dto.StoreInfoDto;
import com.herryboro.order66.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

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

    @Test
    void signUpStoreInfo() throws Exception {
        doNothing().when(storeService).signUp(any(StoreInfoDto.class),any(PasswordEncoder.class));

        mockMvc.perform(post("/store/signUp")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("storeId", "caffineking3")
            .param("ownerName", "이모씨")
            .param("storePassword", "caffeineking!3")
            .param("storePasswordCheck", "caffeineking!3")
            .param("ownerPhone", "010111111113")
            .param("storeName", "라떼킹 왕십리역3")
            .param("storePhone", "02111111113")
            .param("taxId", "1234567893")
            .param("storeAddress", "서울 성동구 왕십리광장로 172")
            .param("storeAddressDetail", "2층 이티-01호3")
            .param("storeOpenTime", "{\n" +
                    "    \"schedules\": [\n" +
                    "        {\n" +
                    "            \"dayofweek\": \"월 화 수 목 금\",\n" +
                    "            \"time\": \"07:00 ~ 21:30\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"dayofweek\": \"토 일\",\n" +
                    "            \"time\": \"09:00 ~ 19:00\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}"
            )
            .param("storeProfile", "20200101_000052_729.jpg")
        )
        .andExpect(status().isOk())
        .andExpect(content().string("회원 가입이 완료되었습니다."));

        verify(storeService).signUp(any(StoreInfoDto.class), any(PasswordEncoder.class));
    }
}