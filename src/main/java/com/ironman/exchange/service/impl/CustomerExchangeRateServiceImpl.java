package com.ironman.exchange.service.impl;

import com.ironman.exchange.config.AppConfig;
import com.ironman.exchange.exception.ExceptionCatalog;
import com.ironman.exchange.client.model.ExchangeRateClientResponse;
import com.ironman.exchange.client.service.ExchangeRateClient;
import com.ironman.exchange.mapper.CustomerExchangeRateMapper;
import com.ironman.exchange.model.api.ExchangeRateResponse;
import com.ironman.exchange.repository.CustomerExchangeRateRepository;
import com.ironman.exchange.service.CustomerExchangeRateService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDate;
import java.time.ZoneId;


@RequiredArgsConstructor
@ApplicationScoped
public class CustomerExchangeRateServiceImpl implements CustomerExchangeRateService {
    private final CustomerExchangeRateRepository customerExchangeRateRepository;
    private final AppConfig appConfig;
    @RestClient
    private final ExchangeRateClient exchangeRateClient;
    private final CustomerExchangeRateMapper customerExchangeRateMapper;

    @Transactional
    @Override
    public ExchangeRateResponse getExchangeRateForCustomer(String customerDni) {
        verifyDailyQueryLimit(customerDni);

        var currentExchangeRate = exchangeRateClient.getTodayExchangeRate();
        saveExchangeRateQuery(customerDni, currentExchangeRate);

        return customerExchangeRateMapper.toResponse(currentExchangeRate);
    }

    private void saveExchangeRateQuery(String customerDni, ExchangeRateClientResponse todayExchangeRate) {
        var customerExchangeRate = customerExchangeRateMapper
                .toEntity(todayExchangeRate, customerDni);

        customerExchangeRateRepository.persist(customerExchangeRate);
    }

    private void verifyDailyQueryLimit(String customerDni) {
        LocalDate currentDate = getDateInConfigZone();
        long todayQueryCount = customerExchangeRateRepository
                .countByCustomerDniAndExchangeDate(customerDni, currentDate);

        long dailyLimit = appConfig.queryCountLimit();
        if (todayQueryCount >= dailyLimit) {
            throw ExceptionCatalog.DAILY_QUERY_LIMIT_EXCEEDED.buildException(dailyLimit, customerDni);
        }
    }

    private LocalDate getDateInConfigZone() {
        ZoneId configuredZone = ZoneId.of(appConfig.timezone());
        return LocalDate.now(configuredZone);
    }
}
