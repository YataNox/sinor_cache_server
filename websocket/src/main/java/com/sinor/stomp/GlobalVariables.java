package com.sinor.stomp;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GlobalVariables {

    @Value("${spring.rabbitmq.host}")
    String rabbitmqHost;
    @Value("${spring.rabbitmq.port}")
    Integer rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    String systemUserName;
    @Value("${spring.rabbitmq.password}")
    String systemUserPassword;

    @Value("${rabbitmq.queue.connection}")
    String rabbitQueueConnection;


    @Value("${rabbitmq.queue.default}")
    String rabbitQueueDefault;
    @Value("${rabbitmq.exchange.default}")
    String rabbitExchangeDefault;
    @Value("${rabbitmq.routing-key.default}")
    String rabbitRoutingKeyDefault;


    @Value("${rabbitmq.queue.example}")
    String rabbitQueueExample;
    @Value("${rabbitmq.exchange.example}")
    String rabbitExchangeVoteClient;
    @Value("${rabbitmq.routing-key.example}")
    String rabbitRoutingKeyExample;


}