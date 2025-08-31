package com.ironman.exchange.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "customer_exchange_rates")
public class CustomerExchangeRate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_dni", nullable = false, length = 15)
    private String customerDni;

    @Column(name = "exchange_date", nullable = false)
    private LocalDate exchangeDate;

    @Column(name = "sunat_rate", nullable = false, precision = 10, scale = 6)
    private BigDecimal sunatRate;

    @Column(name = "buy_rate", nullable = false, precision = 10, scale = 6)
    private BigDecimal buyRate;

    @Column(name = "sell_rate", nullable = false, precision = 10, scale = 6)
    private BigDecimal sellRate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
