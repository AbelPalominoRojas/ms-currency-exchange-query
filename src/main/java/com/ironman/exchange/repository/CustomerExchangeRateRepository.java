package com.ironman.exchange.repository;

import com.ironman.exchange.model.entity.CustomerExchangeRate;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;

@ApplicationScoped
public class CustomerExchangeRateRepository implements PanacheRepositoryBase<CustomerExchangeRate, Long> {

    public long countByCustomerDniAndExchangeDate(
            String customerDni,
            LocalDate exchangeDate
    ) {
        return count("customerDni = ?1 and exchangeDate = ?2", customerDni, exchangeDate);
    }
}
