package com.epam.springsecurityrevise.dto.response;

import lombok.*;

@Data
@Builder
public class ErrorResponse {
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    private int statusCode;
    private String message;
}
