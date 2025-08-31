package com.ironman.exchange.client.service;

import com.ironman.exchange.client.model.ExchangeRateClientResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "exchange-rate-api")
@Produces(MediaType.APPLICATION_JSON)
public interface ExchangeRateClient {

    @GET
    @Path("/tipo-cambio/today.json")
    ExchangeRateClientResponse getTodayExchangeRate();
}
