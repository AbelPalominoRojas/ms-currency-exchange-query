package com.ironman.exchange.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix =  "app")
public interface AppConfig {

    @WithName("timezone")
    @WithDefault("America/Lima")
    String timezone();

    @WithName("query-count-limit")
    @WithDefault("10")
    long queryCountLimit();
}
