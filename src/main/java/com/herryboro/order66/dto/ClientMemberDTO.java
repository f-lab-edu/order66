package com.herryboro.order66.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ClientMemberDTO {
    private Long clientSeq;
    private String clientId;
    private String clientName;
    private String clientNickname;
    private String clientPassword;
    private String clientEmail;
    private String clientPhone;
    private String clientProfile;
}
