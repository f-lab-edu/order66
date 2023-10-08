package com.herryboro.order66.controller;

import com.herryboro.order66.dto.ClientMemberDTO;
import com.herryboro.order66.exception.PasswordMismatchException;
import com.herryboro.order66.exception.UserRegistrationException;
import com.herryboro.order66.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/clientMember")
@RequiredArgsConstructor
public class ClientMemberController {

    private final ClientService clientService;

    @PostMapping(value = "/signUp")
    public ResponseEntity<String> signUpClientUser(@Valid @ModelAttribute ClientMemberDTO user, BindingResult result) {
        // @NotBlank 필드 예외 처리 -> UserRegistrationException
        if (result.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            fieldErrors.put("clientId", "아이디는 필수 입력 사항입니다.");
            fieldErrors.put("clientName", "성함은 필수 입력 사항입니다.");
            fieldErrors.put("clientNickname", "닉네임은 필수 입력 사항입니다.");
            fieldErrors.put("clientPassword", "비밀번호는 필수 입력 사항입니다.");
            fieldErrors.put("passwordCheck", "비밀번호 확인은 필수 입력 사항입니다.");  // Fixed the typo from 'passwardCheck'
            fieldErrors.put("clientPhone", "휴대폰 번호는 필수 입력 사항입니다.");

            String fieldError = result.getFieldError().getField();

            if (fieldErrors.containsKey(fieldError)) {
                throw new UserRegistrationException(fieldErrors.get(fieldError));
            }
        }
        clientService.signUp(user);
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handlePasswordMismatch(PasswordMismatchException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handlerDuplicateKey(DuplicateKeyException e) {
        return ResponseEntity.badRequest().body("이미 가입되어 있는 계정입니다.");
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handlerUserRegistration(UserRegistrationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
