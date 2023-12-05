package com.herryboro.order66.mapper;

import com.herryboro.order66.dto.MenuDto;
import com.herryboro.order66.dto.StoreInfoDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreMapper {
    void insertStoreInfo(StoreInfoDto storeInfoDto);

    int checkRegistrationMenuGroup(Long storeId);

    int checkRegistrationMenu(Long menuGroupId);

    int getMaxMenuGroupOrder(Long storeId);

    int getMaxMenuOrder(Long storeId);

    void registerMenuGroupInfo(MenuDto menuDto);

    void registerMenuInfo(MenuDto menuDto);

    Long getMenuGroupId(MenuDto menuDto);

    Long getMenuId(MenuDto menuDto);

    StoreInfoDto getStoreById(Long userId);

    StoreInfoDto getStoreByStoreId(String storeId);
}
