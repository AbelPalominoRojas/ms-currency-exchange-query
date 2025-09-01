package com.ironman.exchange.service;

import com.ironman.exchange.client.model.ExchangeRateClientResponse;
import com.ironman.exchange.client.service.ExchangeRateClient;
import com.ironman.exchange.config.AppConfig;
import com.ironman.exchange.exception.ApplicationException;
import com.ironman.exchange.exception.ExceptionCatalog;
import com.ironman.exchange.mapper.CustomerExchangeRateMapperImpl;
import com.ironman.exchange.model.entity.CustomerExchangeRate;
import com.ironman.exchange.repository.CustomerExchangeRateRepository;
import com.ironman.exchange.service.impl.CustomerExchangeRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.ironman.exchange.constant.TestConstant.*;
import static com.ironman.exchange.mother.ExchangeRateClientResponseMother.validExchangeRateClientResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class CustomerExchangeRateServiceTest {

    @Mock
    private CustomerExchangeRateRepository customerExchangeRateRepository;

    @Mock
    private AppConfig appConfig;

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Spy
    private CustomerExchangeRateMapperImpl customerExchangeRateMapper;

    @InjectMocks
    private CustomerExchangeRateServiceImpl customerExchangeRateService;

    @BeforeEach
    void setUp() {
        given(appConfig.timezone()).willReturn(TIMEZONE);
        given(appConfig.queryCountLimit()).willReturn(DAILY_LIMIT);
    }


    @Test
    void shouldReturnExchangeRateWhenWithinDailyLimit() {
        ExchangeRateClientResponse exchangeRate = validExchangeRateClientResponse();

        given(customerExchangeRateRepository.countByCustomerDniAndExchangeDate(anyString(), isA(LocalDate.class)))
                .willReturn(0L);
        given(exchangeRateClient.getTodayExchangeRate()).willReturn(exchangeRate);
        willDoNothing().given(customerExchangeRateRepository).persist(isA(CustomerExchangeRate.class));


        var response = customerExchangeRateService.getExchangeRateForCustomer(CUSTOMER_DNI);

        assertEquals(exchangeRate.getFecha(), response.getExchangeDate());
        assertEquals(exchangeRate.getSunat(), response.getSunatRate());
        assertEquals(exchangeRate.getCompra(), response.getBuyRate());
        assertEquals(exchangeRate.getVenta(), response.getSellRate());
    }

    @Test
    void shouldThrowExceptionWhenDailyLimitExceeded() {
        var expectedException = ExceptionCatalog
                .DAILY_QUERY_LIMIT_EXCEEDED.buildException(DAILY_LIMIT, CUSTOMER_DNI);

        given(customerExchangeRateRepository.countByCustomerDniAndExchangeDate(anyString(), isA(LocalDate.class)))
                .willReturn(DAILY_LIMIT + 1L);
        given(appConfig.timezone()).willReturn(TIMEZONE);

        var exception = assertThrows(ApplicationException.class, () -> customerExchangeRateService
                .getExchangeRateForCustomer(CUSTOMER_DNI));

        assertEquals(expectedException.getExceptionType(), exception.getExceptionType());
        assertEquals(expectedException.getComponent(), exception.getComponent());
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

}