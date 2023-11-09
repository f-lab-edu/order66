package com.herryboro.order66.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.herryboro.order66.dto.*;
import com.herryboro.order66.exception.PasswordMismatchException;
import com.herryboro.order66.exception.RegistrationException;
import com.herryboro.order66.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    public final StoreMapper storeMapper;
    private MenuDto menuDto;

    public void signUp(StoreInfoDto storeInfoDto ,PasswordEncoder passwordEncoder) {
        if (!storeInfoDto.getStorePassword().equals(storeInfoDto.getStorePasswordCheck())) {
            throw new PasswordMismatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        storeInfoDto.setStorePassword(passwordEncoder.encode(storeInfoDto.getStorePassword()));

        try {
            storeMapper.insertStoreInfo(storeInfoDto);
        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException(e.getMessage());
        }

    }

    public void addMenuGroup(MenuDto menuDto) {
        int registeredMenuGroupCount = storeMapper.checkRegistrationMenuGroup(menuDto.getStoreId());
        int registeredMenuCount = storeMapper.checkRegistrationMenu(menuDto.getMenuGroupId());

        /*
            메뉴 그룹 및 메뉴 순서 지정 로직
            1. menu group order 순서 지정
            2. menu 순서 지정
         */

        // registeredMenuGroupCount가 0이면 최초 등록이므로 메뉴 그룹 순서를 1롤 지정
        if(registeredMenuGroupCount == 0) menuDto.setMenuGroupOrder(1);
        // 이미 등록되어 있는 menu group이 있으면, 등록되어 있는 menu group order 중 가장 큰 값을 가져와 +1하고 다시 setMenuGroupOrder
        else {
            int maxMenuGroupOrder = storeMapper.getMaxMenuGroupOrder(menuDto.getStoreId());
            menuDto.setMenuGroupOrder(maxMenuGroupOrder + 1);
        }

        // 메뉴 그룹 정보 등록
        storeMapper.registerMenuGroupInfo(menuDto);
        // menu group 등록 후 menu_group_id 가져와서 dto set
        Long menuGroupId = storeMapper.getMenuGroupId(menuDto);
        menuDto.setMenuGroupId(menuGroupId);

        // registeredMenuCount가 0이면 최초 등록이므로 메뉴 순서를 1롤 지정
        if (registeredMenuCount == 0) menuDto.setMenuOrder(1);
        // 이미 등록되어 있는 menu가 있다면, 등록되어 있는 menu order 중 가장 큰 값을 가져와 +1하고 다시 setMenuOrder
        else {
            int maxMenuOrder = storeMapper.getMaxMenuOrder(menuDto.getMenuGroupId());
            menuDto.setMenuOrder(maxMenuOrder + 1);
        }

        // 메뉴 정보 등록
        storeMapper.registerMenuInfo(menuDto);
        // menu 등록 후 menu_id 가져와서 dto set
        Long menuId = storeMapper.getMenuId(menuDto);
        menuDto.setMenuGroupId(menuId);

        // 옵션 등록
        String menuOptions = menuDto.getMenuOptions();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OptionsWrapper optionsWrapper = objectMapper.readValue(menuOptions, OptionsWrapper.class);
            List<Option> optionList = optionsWrapper.getOptions();

            // Now you have a List of Option objects
            for (Option option : optionList) {
                System.out.println("Option Name: " + option.getOptionName() + ", Price: " + option.getPrice());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StoreInfoDto getStoreByStoreId(String storeId) {
        return storeMapper.getStoreByStoreId(storeId);
    }

    // id로 store 정보 조회
    public StoreInfoDto getStoreById(Long id) {
        return storeMapper.getStoreById(id);
    }

}
