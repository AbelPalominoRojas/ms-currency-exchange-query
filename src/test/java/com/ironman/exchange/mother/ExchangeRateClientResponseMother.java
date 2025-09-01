package com.ironman.exchange.mother;

import com.ironman.exchange.client.model.ExchangeRateClientResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExchangeRateClientResponseMother {
    private ExchangeRateClientResponseMother() {
    }

    public static String EXCHANGE_RATE_SUNAT = "3.52";
    public static String EXCHANGE_RATE_COMPRA = "3.53";
    public static String EXCHANGE_RATE_VENTA = "3.54";

    public static ExchangeRateClientResponse validExchangeRateClientResponse() {
        return ExchangeRateClientResponse.builder()
                .fecha(LocalDate.now())
                .sunat(new BigDecimal(EXCHANGE_RATE_SUNAT))
                .compra(new BigDecimal(EXCHANGE_RATE_COMPRA))
                .venta(new BigDecimal(EXCHANGE_RATE_VENTA))
                .build();
    }
}
