package com.herryboro.order66.controller;

import com.herryboro.order66.dto.ClientMemberDTO;
import com.herryboro.order66.exception.PasswordMismatchException;
import com.herryboro.order66.exception.UserRegistrationException;
import com.herryboro.order66.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/clientMember")
@RequiredArgsConstructor
public class ClientMemberController {

    private final ClientService clientService;

    @PostMapping(value = "/signUp")
    public ResponseEntity<String> signUpClientUser(@Valid @ModelAttribute ClientMemberDTO user, BindingResult result) {
        if (result.hasErrors()) {
            List<String> fieldOrder = Arrays.asList("clientId", "clientName", "clientNickname", "clientPassword", "passwardCheck", "clientEmail", "clientPhone", "clientProfile");

            // 회원가입 시 아이디,이름,별명 ... 등의 필드 순서대로 예외 메시지 발생 시키기
            for (String fieldName : fieldOrder) {
                FieldError error = result.getFieldError(fieldName);
                if (error != null) {
                    throw new UserRegistrationException(error.getDefaultMessage());
                }
            }
        }

        if (!user.getClientPassword().equals(user.getPasswardCheck())) {
            throw new PasswordMismatchException("비밀번호 확인이 일치하지 않습니다.");
        }
        clientService.signUp(user);
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handlePasswordMismatch(PasswordMismatchException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // unique 필드에서 예외 발생시 처리하는 handlerDuplicateKey
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handlerDuplicateKey(DuplicateKeyException e) {
        String errorMessage = e.getMessage().toLowerCase();

        System.out.println("errorMessage: " + errorMessage);

        if (errorMessage.contains("client_id")) {
            return ResponseEntity.badRequest().body("이미 존재하는 계정입니다.");
        } else if (errorMessage.contains("client_nickname")) {
            return ResponseEntity.badRequest().body("이미 사용되고 있는 닉네임입니다.");
        } else if (errorMessage.contains("client_phone")) {
            return ResponseEntity.badRequest().body("이미 등록되어 있는 휴대전화 번호입니다.");
        } else {
            return ResponseEntity.badRequest().body("An account with the provided details is already registered.");
        }
    }

    // form에서 data 넘어올때 dto내부에서 발생하는 예외들(@NotBlank 등)이 발생할때 catch되는 exception핸들러
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handlerUserRegistration(UserRegistrationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
