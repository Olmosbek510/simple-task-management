package com.epam.springsecurityrevise.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NotBlank
@Builder
public class AuthRegisterResponseDTO {
    private String email;
    private String password;
}
