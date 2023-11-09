package com.herryboro.order66.controller;

import com.google.gson.Gson;
import com.herryboro.order66.dto.MenuDto;
import com.herryboro.order66.dto.StoreInfoDto;
import com.herryboro.order66.exception.ErrorResponse;
import com.herryboro.order66.exception.InputDataException;
import com.herryboro.order66.exception.PasswordMismatchException;
import com.herryboro.order66.exception.RegistrationException;
import com.herryboro.order66.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/store")
@RequiredArgsConstructor
public class StoreController {

    public final StoreService storeService;
    public final PasswordEncoder passwordEncoder;

    /**
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

    /**
     * store 정보 등록
     */
    @PostMapping(value = "/signUp")
    public ResponseEntity<String> signUpStoreInfo(@Valid @ModelAttribute StoreInfoDto user, BindingResult result) {

        if (result.hasErrors()) {
            // 회원가입 시 발생한 예외 메시지 반환
            String jsonErrorMessages = new Gson().toJson(result.getFieldErrors());
            throw new InputDataException(jsonErrorMessages);
        }
        storeService.signUp(user,passwordEncoder);

        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }

    @PostMapping(value = "/addMenu")
    public ResponseEntity<String> addMenuGroup(@Valid @ModelAttribute MenuDto menuDto) {
        storeService.addMenuGroup(menuDto);
        return ResponseEntity.ok("메뉴등록 되었습니다.");

    }

    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<String> handleInputDataCheck(InputDataException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({PasswordMismatchException.class})
    public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchException e) {
        ErrorResponse errorResponse = new ErrorResponse("비밀 번호 불일치", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponse> handlerUserRegistration(RegistrationException e) {
        ErrorResponse errorResponse = new ErrorResponse("store 정보 등록 에러(이미 등록되어 있는 data { 아이디,휴대폰 번호, 가게 전화번호, 사업자등록번호 등 })", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
