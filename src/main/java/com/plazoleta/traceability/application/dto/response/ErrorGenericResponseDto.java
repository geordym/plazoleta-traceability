package com.plazoleta.traceability.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorGenericResponseDto {
    private String error;
    private String message;
    private String timestamp;

}