package com.ironman.exchange.service;

import com.ironman.exchange.model.api.ExchangeRateResponse;

public interface CustomerExchangeRateService {
    ExchangeRateResponse getExchangeRateForCustomer(String customerDni);
}
