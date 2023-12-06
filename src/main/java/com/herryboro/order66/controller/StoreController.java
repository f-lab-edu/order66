package com.herryboro.order66.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.herryboro.order66.dto.MenuDto;
import com.herryboro.order66.dto.MenuGroupDto;
import com.herryboro.order66.dto.Option;
import com.herryboro.order66.dto.StoreInfoDto;
import com.herryboro.order66.exception.DuplicateRegistrationException;
import com.herryboro.order66.exception.ErrorResponse;
import com.herryboro.order66.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/store")
@RequiredArgsConstructor
@Validated
public class StoreController {

    public final StoreService storeService;
    public final PasswordEncoder passwordEncoder;

    /*
     *  main 홈 (security session 로그인 test)
     */
    @GetMapping(value = "/home")
    public ResponseEntity<String> mainHome() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        /*
          - spring security 인증을 받지 않은 유저가 접근할 경우 principal값은 String "anonymousUser"가 할당됨
          - logout 하면 역시 anonymousUser가 됨
         */
        if (principal instanceof String && "anonymousUser".equals(principal)) {
            return ResponseEntity.ok("로그아웃 되었습니다.");
        } else if (principal instanceof Long) {
            return ResponseEntity.ok("여기는 store 메인 홈페이지 입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request.");
        }
    }

    /*
     * store 정보 등록
     */
    @PostMapping(value = "/signUp")
    public ResponseEntity<String> signUpStoreInfo(@Valid @ModelAttribute StoreInfoDto storeInfo) {
        storeService.signUp(storeInfo, passwordEncoder);
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }

    /*
        메뉴 그룹 추가
     */
    @PostMapping(value = "/addMenuGroup")
    public ResponseEntity<String> addMenuGroup(@ModelAttribute MenuGroupDto menuGroup) {
        storeService.registerMenuGroup(menuGroup);
        return ResponseEntity.ok("메뉴 그룹 등록되었습니다.");
    }

    /*
       메뉴 추가
    */
    @PostMapping(value = "/addMenu")
    public ResponseEntity<String> addMenu(@Valid @ModelAttribute MenuDto menu) {
        storeService.registerMenu(menu);
        return ResponseEntity.ok("메뉴 등록 되었습니다.");
    }

    /*
       메뉴 정보 수정 관련 코드
    */

    // 메뉴 그룹 정보 수정
    @PutMapping(value = "/updateMenuGroup")
    public ResponseEntity<String> updateMenuGroup(@RequestBody List<@Valid MenuGroupDto> menuGrouplist) {
        storeService.updateMenuGroupInfo(menuGrouplist);
        return ResponseEntity.ok("메뉴 그룹 정보가 수정되었습니다");
    }

    // 메뉴 정보 수정
    @PutMapping(value = "/updateMenu")
    public ResponseEntity<String> updateMenu(@Valid @ModelAttribute MenuDto menuDto) {
        storeService.updateMenu(menuDto);
        return ResponseEntity.ok("메뉴 정보 수정되었습니다.");
    }

    // 메뉴 순서(menu gropu, menu가 client단에서 보여지는 순서) 편집
    @PutMapping("/updateOrdering")
    public ResponseEntity<String> updateOrdering(@RequestBody List<MenuGroupDto> orderInfo) {
        storeService.updateOrdering(orderInfo);
        return ResponseEntity.ok("순서 수정되었습니다.");
    }

    /*
       메뉴 삭제
    */

    // menu option 삭제
    @DeleteMapping(value = "/deleteMenuOption")
    public ResponseEntity<String> deleteMenuOption(@Valid @RequestBody Option option) {
        String optionName = storeService.deleteMenuOption(option.getId());
        return ResponseEntity.ok("옵션 " + optionName + "이 삭제되었습니다.");
    }

    // menu 삭제
    @DeleteMapping(value = "/deleteMenu")
    public ResponseEntity<String> deleteMenu(@RequestBody MenuDto menuDto) {
        String menuName = storeService.deleteMenu(menuDto.getId());
        return ResponseEntity.ok("메뉴 " + menuName + "가 삭제되었습니다.");
    }

    // 메뉴 그룹 삭제
    @DeleteMapping(value = "/deleteMenuGroup")
    public ResponseEntity<String> deleteMenuGroup(@RequestBody MenuGroupDto menuGroupDto) {
        String menuGroupName = storeService.deleteMenuGroup(menuGroupDto.getId());
        return ResponseEntity.ok("메뉴 그룹 " + menuGroupName + "가 삭제되었습니다.");
    }

    @ExceptionHandler(DuplicateRegistrationException.class)
    public ResponseEntity<ErrorResponse> handlerUserRegistration(DuplicateRegistrationException e) {
        ErrorResponse errorResponse = new ErrorResponse("store 정보 등록 에러(이미 등록되어 있는 data { 아이디,휴대폰 번호, 가게 전화번호, 사업자등록번호 등 })", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
