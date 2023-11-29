package com.herryboro.order66.mapper;

import com.herryboro.order66.dto.MenuDto;
import com.herryboro.order66.dto.MenuGroupDto;
import com.herryboro.order66.dto.Option;
import com.herryboro.order66.dto.StoreInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreMapper {
    void insertStoreInfo(StoreInfoDto storeInfoDto);

    boolean checkSameMenuGroup(Long storeId, String groupName);

    boolean confirmInitialMenuGroupRegistration(Long storeId);

    boolean confirmInitialMenuRegistration(Long menuGroupId);

    int getMaxMenuGroupOrder(Long storeId);

    int getMaxMenuOrder(Long storeId);

    Long registerMenuGroupInfo(MenuGroupDto menuDto);

    Long registerMenuInfo(MenuDto menuDto);

    StoreInfoDto getStoreById(Long userId);

    StoreInfoDto getStoreByStoreId(String storeId);

    void registerMenuOptions(@Param("menuId") Long menuId, @Param("options") List<Option> options);

    void updateMenu(MenuDto menuDto);

    void updateMenuOptions(List<Option> updateOptionData);

    void updateMenuGroupInfo(List<MenuGroupDto> menuGroups);

    String deleteMenuOption(Long id);

    boolean checkHaveMenuOption(Long id);

    void deleteAllMenuOptionUnderMenu(Long id);

    String deleteMenu(Long id);

    boolean checkExistSubMenu(Long id);

    String deleteMenuGroup(Long id);

    void updateMenuGroupOrder(List<MenuGroupDto> orderInfo);

    void updateMenuOrder(List<MenuDto> menus);
}
