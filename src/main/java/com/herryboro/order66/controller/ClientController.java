package com.herryboro.order66.controller;

import com.herryboro.order66.dto.ClientInfoDTO;
import com.herryboro.order66.dto.UpdateClientInfoDto;
import com.herryboro.order66.exception.ErrorResponse;
import com.herryboro.order66.exception.DuplicateRegistrationException;
import com.herryboro.order66.exception.exceptionutil.ErrorUtils;
import com.herryboro.order66.exception.InvalidInputException;
import com.herryboro.order66.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

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
            Long id = (Long) principal;
            ClientInfoDTO userById = clientService.getUserById(id);
            return ResponseEntity.ok("여기는 메인 홈페이지 입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request.");
        }
    }
    /**
     *  client 회원 가입
     */
    @PostMapping(value = "/signUp")
    public ResponseEntity<String> signUpClientUser(@Valid @ModelAttribute ClientInfoDTO clientInfo, BindingResult result) {
        if (result.hasErrors()) {
            ErrorUtils.checkBindingResult(result);
        }

        clientService.signUp(clientInfo, passwordEncoder);
        return ResponseEntity.ok("회원 가입이 완료되었습니다.");
    }

    /**
     *  client 유저 정보 수정
     */
    @PutMapping(value = "/updateClientInfo")
    public ResponseEntity<String> updateClientInfo(@Valid @ModelAttribute UpdateClientInfoDto user, BindingResult result) {
        if (result.hasErrors()) {
            ErrorUtils.checkBindingResult(result);
        }
        clientService.updateClientInfo(user, passwordEncoder);
        return ResponseEntity.ok("수정되었습니다.");
    }

    @ExceptionHandler(DuplicateRegistrationException.class)
    public ResponseEntity<ErrorResponse> handleUserRegistration(DuplicateRegistrationException e) {
        ErrorResponse errorResponse = new ErrorResponse("유저 등록 에러(이미 등록되어 있는 data { 아이디, 닉네임, 휴대폰 번호 등 })", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
