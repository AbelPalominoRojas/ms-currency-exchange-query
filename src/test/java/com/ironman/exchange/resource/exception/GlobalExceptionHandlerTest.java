package com.ironman.exchange.resource.exception;

import com.ironman.exchange.exception.ApplicationException;
import com.ironman.exchange.exception.ExceptionResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.ironman.exchange.exception.ApplicationException.ExceptionType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private ConstraintViolation<Object> constraintViolation;

    @Mock
    private Path propertyPath;

    @InjectMocks
    private GlobalExceptionHandler.ConstraintViolationExceptionMapper constraintMapper;

    @InjectMocks
    private GlobalExceptionHandler.ApplicationExceptionMapper applicationMapper;

    @Test
    void shouldHandleSingleConstraintViolation() {

        String fieldName = "email";
        String errorMessage = "Email format is invalid";

        given(constraintViolation.getPropertyPath()).willReturn(propertyPath);
        given(propertyPath.toString()).willReturn(fieldName);
        given(constraintViolation.getMessage()).willReturn(errorMessage);

        Set<ConstraintViolation<?>> violations = Set.of(constraintViolation);
        ConstraintViolationException exception = new ConstraintViolationException(violations);


        Response response = constraintMapper.toResponse(exception);
        ExceptionResponse exceptionResponse = (ExceptionResponse) response.getEntity();
        ExceptionResponse.ExceptionDetail detail = exceptionResponse.getDetails().get(0);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(fieldName, detail.getComponent());
        assertEquals(errorMessage, detail.getMessage());
    }


    @ParameterizedTest
    @EnumSource(ExceptionType.class)
    void shouldHandleAllExceptionTypes(ExceptionType exceptionType) {
        String component = "TestComponent";
        String message = "Test error message";
        ApplicationException exception = ApplicationException.builder()
                .exceptionType(exceptionType)
                .component(component)
                .message(message)
                .build();

        Response response = applicationMapper.toResponse(exception);
        ExceptionResponse exceptionResponse = (ExceptionResponse) response.getEntity();
        ExceptionResponse.ExceptionDetail detail = exceptionResponse.getDetails().get(0);

        assertNotNull(exceptionResponse);
        assertEquals(1, exceptionResponse.getDetails().size());

        assertEquals(component, detail.getComponent());
        assertEquals(message, detail.getMessage());
    }

}