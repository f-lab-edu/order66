package com.herryboro.order66.service;

import com.herryboro.order66.dto.store.MenuDto;
import com.herryboro.order66.dto.store.MenuGroupDto;
import com.herryboro.order66.dto.store.StoreInfoDto;
import com.herryboro.order66.exception.DuplicateRegistrationException;
import com.herryboro.order66.exception.InvalidInputException;
import com.herryboro.order66.mapper.StoreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    private StoreMapper storeMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StoreService storeService;

    private StoreInfoDto storeInfo;

    private MenuGroupDto menuGroup;

    private MenuDto menu;

    @BeforeEach
    public void setup() {
        storeInfo = new StoreInfoDto();
        storeInfo.setStorePassword("password");
        storeInfo.setStorePasswordCheck("password");

        menuGroup = new MenuGroupDto();
        menu = new MenuDto();
    }

    // insertStoreInfo() StoreInfoDto 데이터 전달 test
    @Test
    public void signUp_validDate_registersStore() {
        ArgumentCaptor<StoreInfoDto> storeInfoDtoArgumentCaptor = ArgumentCaptor.forClass(StoreInfoDto.class);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        storeService.signUp(storeInfo, passwordEncoder);
        verify(storeMapper).insertStoreInfo(storeInfoDtoArgumentCaptor.capture());

        StoreInfoDto captorValue = storeInfoDtoArgumentCaptor.getValue();
        assertThat("encodedPassword").isEqualTo(captorValue.getStorePassword());
    }

    // password와 passwordcheck 불일치 확인 로직 test
    @Test
    public void signUp_passwordMismatch_throwsException() {
        storeInfo.setStorePasswordCheck("WrongPassward");

        assertThrows(InvalidInputException.class, () -> {
            storeService.signUp(storeInfo, passwordEncoder);
        });
    }

    // 이미 등록되어 있는 store 관련 DuplicateRegistrationException 발생 로직 test
    @Test
    public void signUp_duplicateStore_ThrowsException() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        doThrow(new DataIntegrityViolationException("중복 가입")).when(storeMapper).insertStoreInfo(storeInfo);

        assertThrows(DuplicateRegistrationException.class, () -> {
            storeService.signUp(storeInfo, passwordEncoder);
        });
    }

    // 최초 메뉴 그룹 등록일 경우 menuGroupOrder가 1로 set 되는 로직 test
    @Test
    public void registerMenuGroup_firstRegistration() {
        menuGroup.setStoreId(1l);
        when(storeMapper.confirmInitialMenuGroupRegistration(menuGroup.getStoreId())).thenReturn(false);
        storeService.registerMenuGroup(menuGroup);

        assertEquals(1, menuGroup.getMenuGroupOrder());
        verify(storeMapper).registerMenuGroupInfo(menuGroup);
    }

    // 최초 메뉴 그룹 등록이 아닌 경우
    @Test
    public void registerMenuGroup_notFirstRegistration() {
        menuGroup.setStoreId(1l);

        when(storeMapper.confirmInitialMenuGroupRegistration(menuGroup.getStoreId())).thenReturn(true);
        when(storeMapper.checkSameMenuGroup(menuGroup.getStoreId(), menuGroup.getGroupName())).thenReturn(false);
        when(storeMapper.getMaxMenuGroupOrder(menuGroup.getStoreId())).thenReturn(1);

        storeService.registerMenuGroup(menuGroup);
        assertThat(2).isEqualTo(menuGroup.getMenuGroupOrder());
        verify(storeMapper).registerMenuGroupInfo(menuGroup);
    }

    // 중복 메뉴 그룹 이름 있을시 -> InvalidInputException 예회 발생 test
    @Test
    public void registerMenuGroup_duplicateMenuGroupName() {
        menuGroup.setStoreId(1l);

        when(storeMapper.confirmInitialMenuGroupRegistration(menuGroup.getStoreId())).thenReturn(true);
        when(storeMapper.checkSameMenuGroup(menuGroup.getStoreId(), menuGroup.getGroupName())).thenReturn(true);

        assertThrows(InvalidInputException.class, () -> {
            storeService.registerMenuGroup(menuGroup);
        });
    }

    // 최초 메뉴 등록일 경우 menuOrder가 1로 set 되는 로직 test
    @Test
    public void registerMenu_firstRegistration() {
        menu.setMenuGroupId(1l);

        when(storeMapper.confirmInitialMenuRegistration(menu.getMenuGroupId())).thenReturn(false);
        storeService.registerMenu(menu);

        assertEquals(1, menu.getMenuOrder());
        verify(storeMapper).registerMenuInfo(menu);
    }

    // 최초 메뉴 등록이 아닐 경우 -> 새로 등록할 menu의 menuOrder을 maxOrder + 1 하는지 test
    @Test
    public void registerMenu_notFirstRegistration() {
        menu.setMenuGroupId(1l);

        when(storeMapper.confirmInitialMenuRegistration(menu.getMenuGroupId())).thenReturn(true);
        when(storeMapper.getMaxMenuOrder(menu.getMenuGroupId())).thenReturn(2);
        storeService.registerMenu(menu);

        assertEquals(3, menu.getMenuOrder());
        verify(storeMapper).registerMenuInfo(menu);
    }


}