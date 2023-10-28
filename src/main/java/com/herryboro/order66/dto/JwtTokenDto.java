package com.herryboro.order66.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDto {
    private String token;
}
