package com.ironman.exchange.resource;

import com.ironman.exchange.exception.ExceptionResponse;
import com.ironman.exchange.model.api.ExchangeRateResponse;
import com.ironman.exchange.service.CustomerExchangeRateService;
import com.ironman.exchange.util.HttpStatusCode;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


@RequiredArgsConstructor
@Path("/api/v1/customer-exchange-rates")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerExchangeRateResource {

    private final CustomerExchangeRateService customerExchangeRateService;

    @APIResponses(value = {
            @APIResponse(
                    responseCode = HttpStatusCode.OK,
                    description = "Successfully retrieved exchange rate for the customer",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ExchangeRateResponse.class)
                    )
            ),
            @APIResponse(
                    responseCode = HttpStatusCode.BAD_REQUEST,
                    description = "Invalid DNI format",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )
            ),
            @APIResponse(
                    responseCode = HttpStatusCode.TOO_MANY_REQUESTS,
                    description = "Daily query limit exceeded for the customer",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )
            ),
            @APIResponse(
                    responseCode = HttpStatusCode.INTERNAL_SERVER_ERROR,
                    description = "Unexpected server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )
            )
    }
    )
    @GET
    @Path("/{customerDni}")
    public Response getExchangeRateByDni(
            @PathParam("customerDni")
            @Pattern(regexp = "^[0-9]{8}$", message = "DNI format is invalid - must be exactly 8 digits")
            String customerDni
    ) {
        ExchangeRateResponse response = customerExchangeRateService.getExchangeRateForCustomer(customerDni);
        return Response.ok(response).build();
    }

}
