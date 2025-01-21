package com.epam.springsecurityrevise.util;

import com.epam.springsecurityrevise.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpUtil {
    public static ResponseEntity<ErrorResponse> respondWithError(
            HttpStatus status,
            String message) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }

    public static <T> ResponseEntity<T> buildResponse(
            T body,
            HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }
}
