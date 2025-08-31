package com.ironman.exchange.exception;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class ExceptionResponse implements Serializable {
    private String message;
    private List<ExceptionDetail> details;

    @Getter
    @Builder
    public static class ExceptionDetail implements Serializable {
        private String component;
        private String message;
    }
}
