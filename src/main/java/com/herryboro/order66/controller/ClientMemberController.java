package com.herryboro.order66.controller;

import com.google.gson.Gson;
import com.herryboro.order66.dto.ClientMemberDTO;
import com.herryboro.order66.exception.ClientInputDataException;
import com.herryboro.order66.exception.ErrorResponse;
import com.herryboro.order66.exception.PasswordMismatchException;
import com.herryboro.order66.exception.ClientRegistrationException;
import com.herryboro.order66.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/client")
@RequiredArgsConstructor
public class ClientMemberController {

    private final ClientService clientService;

    @GetMapping(value = "/home")
    public ResponseEntity<String> mainHome() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.toString());
        /**
          - spring security 인증을 받지 않은 유저가 접근할 경우 principal값은 String "anonymousUser"가 할당됨
          - logout 하면 역시 anonymousUser가 됨
         */

        if (principal instanceof String && "anonymousUser".equals(principal)) {
            return ResponseEntity.ok("로그아웃 되었습니다.");
        } else if (principal instanceof Long) {
            Long id = (Long) principal;
            ClientMemberDTO userById = clientService.getUserById(id);
            System.out.println(userById.toString());
            return ResponseEntity.ok("여기는 메인 홈페이지 입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request.");
        }
    }

    @PostMapping(value = "/signUp")
    public ResponseEntity<String> signUpClientUser(@Valid @ModelAttribute ClientMemberDTO user, BindingResult result) {

        if (result.hasErrors()) {
            // 회원가입 시 발생한 예외 메시지 반환
            String jsonErrorMessages = new Gson().toJson(result.getFieldErrors());
            throw new ClientInputDataException(jsonErrorMessages);
        }

        clientService.signUp(user);
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }

    @ExceptionHandler({ClientInputDataException.class})
    public ResponseEntity<String> handlePasswordMismatch(ClientInputDataException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({PasswordMismatchException.class})
    public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchException e) {
        ErrorResponse errorResponse = new ErrorResponse("비밀 번호 불일치", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ClientRegistrationException.class)
    public ResponseEntity<ErrorResponse> handlerUserRegistration(ClientRegistrationException e) {
        ErrorResponse errorResponse = new ErrorResponse("유저 등록 에러(이미 등록되어 있는 data { 아이디, 닉네임, 휴대폰 번호 등 })", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
