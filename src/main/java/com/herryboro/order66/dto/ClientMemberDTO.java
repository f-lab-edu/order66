package com.herryboro.order66.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ClientMemberDTO {

    @NotBlank
    private String clientId;

    @NotBlank
    private String clientName;

    @NotBlank
    private String clientNickname;

    @NotBlank
    private String clientPassword;

    @NotBlank
    private String passwardCheck;

    private String clientEmail;

    @NotBlank
    private String clientPhone;

    private String clientProfile;
}
