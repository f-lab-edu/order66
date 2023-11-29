package com.herryboro.order66.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter @Getter @ToString
public class MenuGroupDto {
    private Long id;

    private Long storeId;

    @NotBlank(message = "메뉴 그룹명은 필수 입력 사항입니다.")
    private String groupName;

    private int menuGroupOrder;

    private List<MenuDto> menus;

    public MenuGroupDto() {}

    public MenuGroupDto(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public MenuGroupDto(Long id, int menuGroupOrder, List<MenuDto> menus) {
        this.id = id;
        this.menuGroupOrder = menuGroupOrder;
        this.menus = menus;
    }
}
