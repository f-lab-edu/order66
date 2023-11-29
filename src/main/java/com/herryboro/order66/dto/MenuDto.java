package com.herryboro.order66.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class MenuDto {
    private Long id;

    private Long menuGroupId;

    @NotBlank(message = "메뉴 이름은 필수 입력 사항입니다.")
    private String menuName;

    @NotNull(message = "가격은 필수 입력 사항입니다.")
    private int menuPrice;

    @NotBlank(message = "메뉴 사진을 등록해주세요.")
    private String menuPhoto;

    private String menuOptions;

    private int menuOrder;

    public MenuDto() {}

    public MenuDto(Long id, int menuOrder) {
        this.id = id;
        this.menuOrder = menuOrder;
    }
}
