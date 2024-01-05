package com.sinor.stomp.config;

import com.sinor.stomp.GlobalVariables;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoteClientExchangeConfig {

    private final GlobalVariables globalVariables;

    @Autowired
    public VoteClientExchangeConfig(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }


    @Bean
    public Queue voteClientQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public TopicExchange voteClientExchange() {
        return new TopicExchange(globalVariables.getRabbitExchangeVoteClient());
    }

    @Bean
    public Binding voteClientBinding() {
        return BindingBuilder
                .bind(voteClientQueue())
                .to(voteClientExchange())
                .with("*");
    }
}
