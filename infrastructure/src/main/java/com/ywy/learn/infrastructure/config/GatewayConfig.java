package com.ywy.learn.infrastructure.config;

import com.ywy.learn.infrastructure.callback.LogCommandCallback;
import com.ywy.learn.infrastructure.gateway.MetaDataGateway;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author ve
 * @date 2019/3/28 17:09
 */
@Configuration
public class GatewayConfig {

    @Primary
    @Bean
    public MetaDataGateway localCommandGateway(CommandBus commandBus, LogCommandCallback logCommandCallback) {
        CommandGatewayFactory factory = new CommandGatewayFactory(commandBus);
        factory.registerCommandCallback(logCommandCallback);
        return factory.createGateway(MetaDataGateway.class);
    }

    @Bean
    public LogCommandCallback logCommandCallback() {
        return new LogCommandCallback();
    }
}
