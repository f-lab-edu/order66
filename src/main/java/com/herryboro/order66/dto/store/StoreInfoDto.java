package com.herryboro.order66.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class StoreInfoDto {
    private Long id;

    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문자와 숫자만 가능합니다.")
    private String storeId;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣\\s]*$", message = "이름에는 문자만 입력 가능합니다.")
    private String ownerName;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$", message = "비밀번호는 8자 이상의 문자 및 특수 문자의 조합이어야 합니다.")
    private String storePassword;

    @NotBlank
    private String storePasswordCheck;

    @NotBlank(message = "사장님 휴대전화 번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[0-9]+$", message = "휴대폰 번호는 숫자만 허용됩니다.")
    private String ownerPhone;

    @NotBlank(message = "매장명은 필수 입력 사항입니다.")
    private String storeName;

    @NotBlank(message = "매장 전화번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[0-9]+$", message = "휴대폰 번호는 숫자만 허용됩니다.")
    private String storePhone;

    @NotBlank(message = "사업자번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[0-9]+$", message = "사업자번호는 숫자만 허용됩니다.")
    private String taxId;

    @NotBlank(message = "매장 주소는 필수 입력 사항입니다.")
    private String storeAddress;
    @NotBlank(message = "매장 주소는 필수 입력 사항입니다.")
    private String storeAddressDetail;

    @NotEmpty(message = "매장 운영 시간은 필수 입력 사항입니다.")
    private String storeOpenTime;

    private String storeProfile;
}
