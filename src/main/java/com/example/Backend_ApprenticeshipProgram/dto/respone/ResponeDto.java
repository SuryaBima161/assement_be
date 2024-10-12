package com.example.Backend_ApprenticeshipProgram.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponeDto {
    private String message;
    private Object data;
}
