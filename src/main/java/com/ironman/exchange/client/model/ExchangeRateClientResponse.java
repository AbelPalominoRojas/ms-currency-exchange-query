package com.ironman.exchange.client.model;


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
public class ExchangeRateClientResponse implements Serializable {
    private LocalDate fecha;
    private BigDecimal sunat;
    private BigDecimal compra;
    private BigDecimal venta;
}
