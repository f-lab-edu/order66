//package com.herryboro.order66.controller;
//
//import com.herryboro.order66.dto.ClientInfoDTO;
//import com.herryboro.order66.dto.JwtTokenDto;
//import com.herryboro.order66.security.JwtFilter;
//import com.herryboro.order66.security.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
///** Jwt 로그인 controller */
//@RestController
//@RequestMapping("/jwtApi")
//@RequiredArgsConstructor
//public class JwtAuthenticationController {
//
//    private final AuthenticationManagerBuilder managerBuilder;
//    private final TokenProvider tokenProvider;
//
//    @GetMapping(value = "/home")
//    public ResponseEntity<String> mainHome() {
//        return ResponseEntity.ok("메인홈입니다.");
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtTokenDto> authenticteUser(@RequestBody ClientInfoDTO loginRequest) {
//
//        Authentication authentication = managerBuilder.getObject().authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getClientId(), loginRequest.getClientPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = tokenProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        // response header에 jwt token에 넣어줌
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//        return new ResponseEntity<>(new JwtTokenDto(jwt), httpHeaders, HttpStatus.OK);
//    }
//}
