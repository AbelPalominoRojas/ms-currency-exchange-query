package com.ironman.exchange.resource;

import com.ironman.exchange.client.service.ExchangeRateClient;
import com.ironman.exchange.model.entity.CustomerExchangeRate;
import com.ironman.exchange.repository.CustomerExchangeRateRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.ironman.exchange.constant.TestConstant.CUSTOMER_DNI;
import static com.ironman.exchange.mother.ExchangeRateClientResponseMother.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@QuarkusTest
class CustomerExchangeRateResourceTest {

    @InjectMock
    private CustomerExchangeRateRepository customerExchangeRateRepository;

    @RestClient
    @InjectMock
    private ExchangeRateClient exchangeRateClient;

    @Test
    void testHelloEndpoint() {
        given(customerExchangeRateRepository
                        .countByCustomerDniAndExchangeDate(anyString(), isA(LocalDate.class)))
                .willReturn(0L);
        given(exchangeRateClient.getTodayExchangeRate())
                .willReturn(validExchangeRateClientResponse());
        willDoNothing().given(customerExchangeRateRepository).persist(isA(CustomerExchangeRate.class));

        RestAssured.given()
                .pathParams("customerDni", CUSTOMER_DNI)
                .when().get("/api/v1/customer-exchange-rates/{customerDni}")
                .then()
                .statusCode(200)
        .body("exchangeDate", Matchers.notNullValue())
        .body("sunatRate", Matchers.equalTo(Float.valueOf(EXCHANGE_RATE_SUNAT)))
        .body("sellRate", Matchers.equalTo(Float.valueOf(EXCHANGE_RATE_VENTA)))
        .body("buyRate", Matchers.equalTo(Float.valueOf(EXCHANGE_RATE_COMPRA)))        ;
    }

}