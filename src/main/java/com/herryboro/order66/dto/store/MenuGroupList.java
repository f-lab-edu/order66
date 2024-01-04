package com.herryboro.order66.dto.store;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
    menugroup 업데이트시 controller로 넘어오는 json 배열 data를 정상적으로 valid 하기 위해 MenuGroupDto를 MenuGroupList로 한번 wrap함
    -> https://stackoverflow.com/questions/28150405/validation-of-a-list-of-objects-in-spring#:~:text=The%20basic%20problem%20is%20that,CompanyTag%3E%20categories.%20Change%20to
       이 내용 참고
 */
@Getter @Setter
public class MenuGroupList {
    @Valid
    public List<MenuGroupDto> menuGroups;

    public MenuGroupList() {}

    public MenuGroupList(List<MenuGroupDto> menuGroups) {
        this.menuGroups = menuGroups;
    }
}
