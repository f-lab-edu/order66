package com.herryboro.order66.controller;

import com.herryboro.order66.dto.ClientMemberDTO;
import com.herryboro.order66.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientMember")
@RequiredArgsConstructor
public class ClientMemberController {

    private final ClientService clientService;

    @PostMapping(value = "/signUp")
    public ResponseEntity<String> signUpClientUser(@RequestBody ClientMemberDTO user) {
        clientService.signUp(user);
        return ResponseEntity.ok("Registration successful!");
    }
}
