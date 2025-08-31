package com.ironman.exchange.mapper;

import com.ironman.exchange.client.model.ExchangeRateClientResponse;
import com.ironman.exchange.model.api.ExchangeRateResponse;
import com.ironman.exchange.model.entity.CustomerExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.CDI)
public interface CustomerExchangeRateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerDni", source = "customerDni")
    @Mapping(target = "exchangeDate", source = "exchangeRate.fecha")
    @Mapping(target = "sunatRate", source = "exchangeRate.sunat")
    @Mapping(target = "buyRate", source = "exchangeRate.compra")
    @Mapping(target = "sellRate", source = "exchangeRate.venta")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CustomerExchangeRate toEntity(ExchangeRateClientResponse exchangeRate, String customerDni);

    @Mapping(target = "exchangeDate", source = "fecha")
    @Mapping(target = "sunatRate", source = "sunat")
    @Mapping(target = "buyRate", source = "compra")
    @Mapping(target = "sellRate", source = "venta")
    ExchangeRateResponse toResponse(ExchangeRateClientResponse exchangeRate);

}
