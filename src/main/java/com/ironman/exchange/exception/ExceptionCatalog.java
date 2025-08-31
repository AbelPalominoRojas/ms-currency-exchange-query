package com.ironman.exchange.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.ironman.exchange.exception.ApplicationException.ExceptionType;

@Getter
@RequiredArgsConstructor
public enum ExceptionCatalog {
    DAILY_QUERY_LIMIT_EXCEEDED(
            ExceptionType.TOO_MANY_REQUESTS,
            "exchange-service",
            "Daily query limit of %d exceeded for customer: %s"
    ),
    UNEXPECTED_ERROR(
            ExceptionType.INTERNAL_SERVER_ERROR,
            "exchange-service",
            "An unexpected error occurred during the process."
    );

    private final ExceptionType exceptionType;
    private final String component;
    private final String message;


    public ApplicationException buildException(Object... args) {
        String formattedMessage = String.format(message, args);
        return ApplicationException.builder()
                .exceptionType(exceptionType)
                .component(component)
                .message(formattedMessage)
                .build();
    }
}
