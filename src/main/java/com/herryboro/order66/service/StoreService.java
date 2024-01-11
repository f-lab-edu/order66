package com.herryboro.order66.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.herryboro.order66.dto.store.MenuDto;
import com.herryboro.order66.dto.store.MenuGroupDto;
import com.herryboro.order66.dto.store.Option;
import com.herryboro.order66.dto.store.StoreInfoDto;
import com.herryboro.order66.exception.DuplicateRegistrationException;
import com.herryboro.order66.exception.InvalidInputException;
import com.herryboro.order66.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    public final StoreMapper storeMapper;

    /*
        점포 회원 가입
     */
    public void signUp(StoreInfoDto storeInfoDto , PasswordEncoder passwordEncoder) {
        if (!storeInfoDto.getStorePassword().equals(storeInfoDto.getStorePasswordCheck())) {
            throw new InvalidInputException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        storeInfoDto.setStorePassword(passwordEncoder.encode(storeInfoDto.getStorePassword()));

        try {
            storeMapper.insertStoreInfo(storeInfoDto);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRegistrationException(e.getMessage());
        }
    }

    /*
        메뉴 등록
     */
    @Transactional
    public void registerMenuGroup(MenuGroupDto menuGroup) {
        /*
            메뉴 그룹 등록
             - menu group order 순서 지정
        */
        boolean isFirstMenuGroupRegistration = storeMapper.confirmInitialMenuGroupRegistration(menuGroup.getStoreId());

        if (!isFirstMenuGroupRegistration) {
            // 최초 등록이므로 메뉴 그룹 순서를 1롤 지정
            menuGroup.setMenuGroupOrder(1);
        } else {
            // 등록되어 있는 menu group 있는지 여부 확인
            boolean haveMenuGroup = storeMapper.checkSameMenuGroup(menuGroup.getStoreId(), menuGroup.getGroupName());

            if (!haveMenuGroup) {
                // 이미 등록되어 있는 menu group name이 있으면, 등록되어 있는 menu group order 중 가장 큰 값을 가져와 +1하고 다시 setMenuGroupOrder
                int maxMenuGroupOrder = storeMapper.getMaxMenuGroupOrder(menuGroup.getStoreId());
                menuGroup.setMenuGroupOrder(maxMenuGroupOrder + 1);
            } else {
                throw new InvalidInputException("이미 존재하는 메뉴 그룹입니다.");
            }
        }

        // 메뉴 그룹 정보 등록
        storeMapper.registerMenuGroupInfo(menuGroup);
    }

    @Transactional(rollbackFor = JsonProcessingException.class)
    public void registerMenu(MenuDto menuDto) {
        /*
            메뉴 등록
             - menu 순서 지정
        */
        boolean isFirstMenuRegistration = storeMapper.confirmInitialMenuRegistration(menuDto.getMenuGroupId());

        if (!isFirstMenuRegistration) {
            // registeredMenuCount가 0이면 최초 등록이므로 메뉴 순서를 1롤 지정
            menuDto.setMenuOrder(1);
        } else {
            // 이미 등록되어 있는 menu가 있다면, 등록되어 있는 menu order 중 가장 큰 값을 가져와 +1하고 다시 setMenuOrder
            int maxMenuOrder = storeMapper.getMaxMenuOrder(menuDto.getMenuGroupId());
            menuDto.setMenuOrder(maxMenuOrder + 1);
        }

        // 메뉴 정보 등록
        Long menuId = storeMapper.registerMenuInfo(menuDto);

        // 옵션 등록
        // json 배열로 넘어오는 데이터 -> List<Option>으로 변환
        String menuOptions = menuDto.getMenuOptions();
        ObjectMapper objectMapper = new ObjectMapper();

        if (menuOptions != null && !menuOptions.trim().isEmpty()) {
            // uncheck, check exception
            try {
                List<Option> options = objectMapper.readValue(menuOptions, new TypeReference<List<Option>>() {});
                storeMapper.registerMenuOptions(menuId, options);
            } catch (JsonProcessingException e) {
                // check 예외를 -> uncheck 예외로 변환
                // roll back을 적용받을 수 있도록
                throw new RuntimeException("Json 파싱 에러");
            }
        }
    }

    /*
        storeId로 store 정보 가져오기
         - Spring Security의 인증 과정, StoreAuthenticationProvider에서 사용
     */
    public StoreInfoDto getStoreByStoreId(String storeId) {
        return storeMapper.getStoreByStoreId(storeId);
    }

    /*
        메뉴 정보 수정
     */
    @Transactional(rollbackFor = JsonProcessingException.class)
    public void updateMenu(MenuDto menuDto) {
        // 메뉴 이름, 가격, 사진 정보 수정
        storeMapper.updateMenu(menuDto);

        // 옵션 정보 수정
        String menuOptions = menuDto.getMenuOptions();
        ObjectMapper objectMapper = new ObjectMapper();

        if (menuOptions != null && !menuOptions.trim().isEmpty()) {
            List<Option> options = null;

            try {
                 options = objectMapper.readValue(menuOptions, new TypeReference<List<Option>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Json 파싱 에러");
            }

            /* 메뉴 수정 버튼 클릭시 */

            // 이미 존재하는 option을 담는 list
            List<Option> existingOptions = new ArrayList<>();

            // 새로 추가된 data option을 담는 list
            List<Option> newOptions = new ArrayList<>();

            for (Option option : options) {
                if (option.getId() != null) {
                    existingOptions.add(option);
                } else {
                    newOptions.add(option);
                }
            }

            // menu id가 있으면 -> 수정
            if (existingOptions.size() > 0) {
                storeMapper.updateMenuOptions(existingOptions);
            }
            // menu id가 없으면 -> 새로운 메뉴 옵션이므로 추가 insert
            if (newOptions.size() > 0) {
                storeMapper.registerMenuOptions(menuDto.getId(), newOptions);
            }
        }
    }

    /*
        메뉴 그룹 수정
     */
    public void updateMenuGroupInfo(List<MenuGroupDto> menuGroups) {
        storeMapper.updateMenuGroupInfo(menuGroups);
    }

    /*
        메뉴 옵션 삭제
     */
    public String deleteMenuOption(Long id) {
        return storeMapper.deleteMenuOption(id);
    }

    /*
        메뉴 삭제
     */
    public String deleteMenu(Long id) {
        boolean haveMenuOption = storeMapper.checkHaveMenuOption(id);

        // 메뉴에 하위 메뉴 옵션이 존재하는 경우 -> 옵션 전부 삭제 -> 이 후 메뉴 삭제 진행
        if (haveMenuOption) {
            storeMapper.deleteAllMenuOptionUnderMenu(id);
        }

        return storeMapper.deleteMenu(id);
    }

    /*
        메뉴 그룹 삭제
     */
    public String deleteMenuGroup(Long id) {
        boolean haveSubMenu = storeMapper.checkExistSubMenu(id);

        if (haveSubMenu) {
            // 메뉴 그룹 하위에 메뉴가 존재하면 삭제 못하도록 설정
            throw new InvalidInputException("삭제할 수 없습니다. 그룹의 하위 메뉴가 존재합니다.");
        }

        return storeMapper.deleteMenuGroup(id);
    }

    /*
        메뉴 그룹 순서 변경
     */
    @Transactional
    public void updateOrdering(List<MenuGroupDto> orderInfo) {
        // 메뉴 그룹 order update
        storeMapper.updateMenuGroupOrder(orderInfo);

        // 메뉴 order update
        for (MenuGroupDto menuDto : orderInfo) {
            storeMapper.updateMenuOrder(menuDto.getMenus());
        }
    }
}
