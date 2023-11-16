package com.herryboro.order66.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateClientInfoDto {
    private Long id;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣\\s]*$", message = "이름에는 문자만 입력 가능합니다.")
    private String clientName;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    private String clientNickname;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$", message = "비밀번호는 8자 이상의 문자 및 특수 문자의 조합이어야 합니다.")
    private String clientPassword;

    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String clientEmail;

    @NotBlank(message = "휴대전화 번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[0-9]+$", message = "휴대폰 번호는 숫자만 허용됩니다.")
    private String clientPhone;

    private String clientProfile;
}
