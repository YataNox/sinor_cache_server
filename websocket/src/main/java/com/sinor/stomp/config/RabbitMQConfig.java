package com.sinor.stomp.config;

import com.sinor.stomp.GlobalVariables;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final GlobalVariables globalVariables;
    private final Jackson2JsonMessageConverter jsonMessageConverter;

    @Autowired
    public RabbitMQConfig(Jackson2JsonMessageConverter jsonMessageConverter,
                          GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
        this.jsonMessageConverter = jsonMessageConverter;

    }


    /* messageConverter를 커스터마이징 하기 위해 Bean 새로 등록 */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        rabbitTemplate.setDefaultReceiveQueue(globalVariables.getRabbitQueueDefault());
        return rabbitTemplate;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(globalVariables.getRabbitmqHost());
        factory.setPort(5672); // amqp
        factory.setUsername(globalVariables.getSystemUserName());
        factory.setPassword(globalVariables.getSystemUserPassword());
        return factory;
    }


}