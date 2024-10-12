package com.example.Backend_ApprenticeshipProgram.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
