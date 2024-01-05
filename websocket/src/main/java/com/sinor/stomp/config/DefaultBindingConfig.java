package com.sinor.stomp.config;

import com.sinor.stomp.GlobalVariables;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultBindingConfig {

    private final GlobalVariables globalVariables;

    @Autowired
    public DefaultBindingConfig(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }


    @Bean
    public Queue defaulltQueue() {
        return new Queue(globalVariables.getRabbitQueueDefault(), true);
    }

    @Bean
    public Queue connectionQueue() {
        return new Queue(globalVariables.getRabbitQueueConnection(), true);
    }

    @Bean
    public TopicExchange defaultExchange() {
        return new TopicExchange(globalVariables.getRabbitExchangeDefault());
    }


    @Bean
    public Binding defaultBinding() {
        return BindingBuilder
                .bind(defaulltQueue())
                .to(defaultExchange())
                .with("#");
    }
}
