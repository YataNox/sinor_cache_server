package com.sinor.stomp.config;

import com.sinor.stomp.GlobalVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * @apiNote <a
 * href="https://stackoverflow.com/questions/64942023/cannot-connect-to-rabbitmq-stomp-from-spring-boot">reference</a>
 */
@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    private final String rabbitRelayHost;
    private final Integer rabbitRelayPort;
    private final String systemUserName;
    private final String systemUserPassword;

    @Autowired
    public StompConfig(GlobalVariables globalVariables) {
        this.rabbitRelayHost = globalVariables.getRabbitmqHost();
        this.rabbitRelayPort = globalVariables.getRabbitmqPort();
        this.systemUserName = globalVariables.getSystemUserName();
        this.systemUserPassword = globalVariables.getSystemUserPassword();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.setPathMatcher(new AntPathMatcher("."))
                .enableStompBrokerRelay(
                        "/topic", "/queue", "/exchange"
                )
                .setRelayHost(rabbitRelayHost)
                .setRelayPort(rabbitRelayPort)
                .setSystemLogin(systemUserName)
                .setSystemPasscode(systemUserPassword)
                .setClientLogin(systemUserName)
                .setClientPasscode(systemUserPassword)
                .setAutoStartup(true)
                .setSystemHeartbeatSendInterval(10 * 1000)
                .setSystemHeartbeatReceiveInterval(10 * 1000);
    }
}