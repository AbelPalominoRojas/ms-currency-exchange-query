package com.ironman.exchange.model.api;


import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExchangeRateResponse implements Serializable {
    private LocalDate exchangeDate;
    private BigDecimal sunatRate;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
}
