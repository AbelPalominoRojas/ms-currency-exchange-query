package com.ironman.exchange.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class ApplicationException extends RuntimeException {
    private ExceptionType exceptionType;
    private String component;
    private String message;

    @RequiredArgsConstructor
    @Getter
    public enum ExceptionType {
        BAD_REQUEST(400),
        TOO_MANY_REQUESTS(429),
        INTERNAL_SERVER_ERROR(500);

        private final int statusCode;
    }
}
