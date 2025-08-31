package com.ironman.exchange.resource.exception;

import com.ironman.exchange.exception.ApplicationException;
import com.ironman.exchange.exception.ExceptionResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

import static com.ironman.exchange.exception.ApplicationException.ExceptionType;
import static com.ironman.exchange.exception.ExceptionConstants.*;
import static com.ironman.exchange.exception.ExceptionResponse.ExceptionDetail;


@ApplicationScoped
public class GlobalExceptionHandler {

    @Provider
    public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

        @Override
        public Response toResponse(ConstraintViolationException exception) {
            var details = exception.getConstraintViolations()
                    .stream()
                    .map(violation -> ExceptionDetail.builder()
                            .component(getFieldName(violation))
                            .message(violation.getMessage())
                            .build())
                    .toList();

            ExceptionResponse response = createExceptionResponse(MESSAGE_INVALID_INPUT_DATA, details);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(response)
                    .build();
        }

        private static String getFieldName(ConstraintViolation<?> violation) {
            String propertyPath = violation.getPropertyPath().toString();
            return propertyPath.isEmpty() ? "unknown" : propertyPath;
        }
    }

    @Provider
    public static class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

        @Override
        public Response toResponse(ApplicationException exception) {
            var detail = ExceptionDetail.builder()
                    .component(exception.getComponent())
                    .message(exception.getMessage())
                    .build();

            String message = messageFromExceptionType(exception.getExceptionType());
            ExceptionResponse response = createExceptionResponse(message, List.of(detail));

            return Response.status(mapToJaxRsExceptionType(exception.getExceptionType()))
                    .entity(response)
                    .build();
        }
    }

    private static ExceptionResponse createExceptionResponse(String message, List<ExceptionDetail> details) {
        return ExceptionResponse.builder()
                .message(message)
                .details(details)
                .build();
    }

    private static String messageFromExceptionType(ExceptionType exceptionType) {
        return switch (exceptionType) {
            case BAD_REQUEST -> MESSAGE_INVALID_INPUT_DATA;
            case INTERNAL_SERVER_ERROR -> MESSAGE_INTERNAL_ERROR;
            default -> MESSAGE_UNEXPECTED_ERROR;
        };
    }

    private static Response.Status mapToJaxRsExceptionType(ExceptionType exceptionType) {
        return switch (exceptionType) {
            case BAD_REQUEST -> Response.Status.BAD_REQUEST;
            case TOO_MANY_REQUESTS -> Response.Status.TOO_MANY_REQUESTS;
            default -> Response.Status.INTERNAL_SERVER_ERROR;
        };
    }

}
