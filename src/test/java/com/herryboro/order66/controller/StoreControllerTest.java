package com.herryboro.order66.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herryboro.order66.dto.store.*;
import com.herryboro.order66.service.StoreService;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    private MenuDto menuDto;

    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        // mock에서 utf8로 인코딩 설정
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))  // Force UTF-8 Encoding
                .build();

        // 초기 menuDto 객체 생성
        menuDto = new MenuDto();

        // json 데이터 변환용 jackson 객체
        objectMapper = new ObjectMapper();
    }

    // Option String json data -> json 파싱
    public List<Option> options (String jsonData) throws JsonProcessingException {
        return objectMapper.readValue(jsonData, new TypeReference<List<Option>>() {});
    }

    @Test
    void signUp_storeInfo() throws Exception {
        ArgumentCaptor<StoreInfoDto> captor = ArgumentCaptor.forClass(StoreInfoDto.class);

        mockMvc.perform(post("/store/signUp")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("storeId", "caffineking")
            .param("ownerName", "이모씨")
            .param("storePassword", "qlalfqjsgh!89")
            .param("storePasswordCheck", "qlalfqjsgh!89")
            .param("ownerPhone", "01011111111")
            .param("storeName", "라떼킹 왕십리역")
            .param("storePhone", "0211111111")
            .param("taxId", "1234567893")
            .param("storeAddress", "서울 성동구 왕십리광장로 172")
            .param("storeAddressDetail", "2층 이티-01호")
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

        verify(storeService).signUp(captor.capture(), any(PasswordEncoder.class));

        StoreInfoDto captoredStoreInfo = captor.getValue();

        assertThat(captoredStoreInfo.getStoreId()).isEqualTo("caffineking");
        assertThat(captoredStoreInfo.getOwnerName()).isEqualTo("이모씨");
        assertThat(captoredStoreInfo.getStorePassword()).isEqualTo("qlalfqjsgh!89");
        assertThat(captoredStoreInfo.getStorePasswordCheck()).isEqualTo("qlalfqjsgh!89");
        assertThat(captoredStoreInfo.getOwnerPhone()).isEqualTo("01011111111");
        assertThat(captoredStoreInfo.getStoreName()).isEqualTo("라떼킹 왕십리역");
        assertThat(captoredStoreInfo.getStorePhone()).isEqualTo("0211111111");
        assertThat(captoredStoreInfo.getTaxId()).isEqualTo("1234567893");
        assertThat(captoredStoreInfo.getStoreAddress()).isEqualTo("서울 성동구 왕십리광장로 172");
        assertThat(captoredStoreInfo.getStoreAddressDetail()).isEqualTo("2층 이티-01호");
        assertThat(captoredStoreInfo.getStoreOpenTime()).isEqualTo(
    "{\n" +
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
        );
    }

    @Test
    public void registerMenuGroup_validInput() throws Exception {
        ArgumentCaptor<MenuGroupDto> menuGroupDtoArgumentCaptor = ArgumentCaptor.forClass(MenuGroupDto.class);

        mockMvc.perform(post("/store/addMenuGroup")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("storeId", "1") //
            .param("groupName", "Test Group")
        )
        .andExpect(status().isOk())
        .andExpect(content().string("메뉴 그룹 등록되었습니다."));

        verify(storeService).registerMenuGroup(menuGroupDtoArgumentCaptor.capture());
        MenuGroupDto menuGroupValue = menuGroupDtoArgumentCaptor.getValue();

        assertThat(menuGroupValue.getStoreId()).isEqualTo(1);
        assertThat(menuGroupValue.getGroupName()).isEqualTo("Test Group");
    }

    @Test
    public void registerMenu_validInput_success() throws Exception {
        ArgumentCaptor<MenuDto> menuDtoArgumentCaptor = ArgumentCaptor.forClass(MenuDto.class);

        mockMvc.perform(post("/store/addMenu")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("menuGroupId", "1")
            .param("menuName", "아메리카노")
            .param("menuPrice", "1500")
            .param("menuPhoto", "coffee.jpg")
            .param("menuOptions",
        "[\n" +
                    "{\n" +
                        "\"optionName\": \"HOT\",\n" +
                        "\"price\": 0\n" +
                    "},\n" +
                    "{\n" +
                        "\"optionName\": \"ICE\",\n" +
                        "\"price\": 100\n" +
                    "},\n" +
                    "{\n" +
                        "\"optionName\": \"샷 추가\",\n" +
                        "\"price\": 1000\n" +
                    "}\n" +
                "]")
        )
        .andExpect(status().isOk())
        .andExpect(content().string("메뉴 등록 되었습니다."));

        verify(storeService).registerMenu(menuDtoArgumentCaptor.capture());
        MenuDto captorValue = menuDtoArgumentCaptor.getValue();

        assertThat(captorValue.getMenuGroupId()).isEqualTo(1);
        assertThat(captorValue.getMenuName()).isEqualTo("아메리카노");
        assertThat(captorValue.getMenuPrice()).isEqualTo(1500);
        assertThat(captorValue.getMenuPhoto()).isEqualTo("coffee.jpg");

        List<Option> options = options(captorValue.getMenuOptions());

        assertThat(options.get(0).getOptionName()).isEqualTo("HOT");
        assertThat(options.get(1).getOptionName()).isEqualTo("ICE");
        assertThat(options.get(2).getOptionName()).isEqualTo("샷 추가");
    }

    /* 메뉴 그룹 정보 수정 test */

    // 메뉴 그룹 수정
    @Test
    public void updateMenuGroup() throws Exception {
        List<MenuGroupDto> menuGroups = Arrays.asList(
            new MenuGroupDto(1L, "Signature Latte"),
            new MenuGroupDto(2L, "Coffee Latte")
        );

        MenuGroupList menuGroupListDto = new MenuGroupList(menuGroups);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(menuGroupListDto);

        mockMvc
            .perform(
                put("/store/updateMenuGroup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent)
            )
            .andExpect(status().isOk());

        // list를 캡쳐
        ArgumentCaptor<List<MenuGroupDto>> menuGroupCaptor = ArgumentCaptor.forClass(List.class);

        verify(storeService).updateMenuGroupInfo(menuGroupCaptor.capture());
        List<MenuGroupDto> captorValue = menuGroupCaptor.getValue();

        assertThat(captorValue.get(0).getGroupName()).isEqualTo("Signature Latte");
        assertThat(captorValue.get(1).getGroupName()).isEqualTo("Coffee Latte");
    }

    // 메뉴 정보 수정
    @Test
    public void updateMenu() throws Exception {
        // menu captor용
        ArgumentCaptor<MenuDto> menuDtoArgumentCaptor = ArgumentCaptor.forClass(MenuDto.class);

        // option data
        String jsonData = "[\n" +
                "        {\n" +
                "            \"id\" : 5,\n" +
                "                \"optionName\": \"HOT\",\n" +
                "                \"price\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\" : 6,\n" +
                "                \"optionName\": \"ICE\",\n" +
                "                \"price\": 200\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\" : 8,\n" +
                "                \"optionName\": \"와사비 추가\",\n" +
                "                \"price\": 1500\n" +
                "        }\n" +
                "]";

        mockMvc.perform(
                put("/store/updateMenu")
                        .param("id", "1")
                        .param("menuName", "라떼킹 cream 라떼")
                        .param("menuPrice", "2900")
                        .param("menuPhoto", "latte.jpg")
                        .param("menuOptions", jsonData)
        ).andExpect(status().isOk());

        verify(storeService).updateMenu(menuDtoArgumentCaptor.capture());

        MenuDto captorValue = menuDtoArgumentCaptor.getValue();
        assertThat(captorValue.getId()).isEqualTo(1);
        assertThat(captorValue.getMenuName()).isEqualTo("라떼킹 cream 라떼");
        assertThat(captorValue.getMenuPrice()).isEqualTo(2900);
        assertThat(captorValue.getMenuPhoto()).isEqualTo("latte.jpg");

        // option
        List<Option> options = options(captorValue.getMenuOptions());
        assertThat(options.get(2).getOptionName()).isEqualTo("와사비 추가");
    }

    // 메뉴 그룹, 정보 order 수정
    @Test
    public void updateOrdering() throws Exception {
        MenuDto menu1 = new MenuDto(1L, 1);
        MenuDto menu2 = new MenuDto(2L, 2);
        List<MenuDto> menusOfMenugroup1 = Arrays.asList(menu1, menu2);

        MenuDto menu3 = new MenuDto(3L, 1);
        List<MenuDto> menusOfMenugroup2 = Arrays.asList(menu3);

        MenuGroupDto menuGroup1 = new MenuGroupDto(1L, 2, menusOfMenugroup1);
        MenuGroupDto menuGroup2 = new MenuGroupDto(2L, 1, menusOfMenugroup2);

        List<MenuGroupDto> menuGroups = Arrays.asList(menuGroup1, menuGroup2);

        String jsonString = objectMapper.writeValueAsString(menuGroups);

        mockMvc.perform(
            put("/store/updateOrdering")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString)
        ).andExpect(status().isOk())
        .andExpect(content().string("순서 수정되었습니다."));

        // 캡쳐용
        ArgumentCaptor<List<MenuGroupDto>> listArgumentCaptor = ArgumentCaptor.forClass(List.class);

        // 호출확인
        verify(storeService).updateOrdering(listArgumentCaptor.capture());

        // service 메서드 호출시 캡쳐된 value
        List<MenuGroupDto> captorValue = listArgumentCaptor.getValue();

        // 값 일치 학인
        assertThat(captorValue.get(0).getId()).isEqualTo(1);
        assertThat(captorValue.get(0).getMenuGroupOrder()).isEqualTo(2);
        assertThat(captorValue.get(0).getMenus().get(1).getId()).isEqualTo(2);
        assertThat(captorValue.get(0).getMenus().get(1).getMenuOrder()).isEqualTo(2);
    }
}