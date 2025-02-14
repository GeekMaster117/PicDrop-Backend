package com.geekmaster117.springweb.config;

import com.geekmaster117.springweb.constants.Urls;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{
    /* Registers a messaging endpoint at '/ws' with Stomp messaging protocol
    * Allows requests and subscribers from localhost:5173
    * Enabled SockJS for websocket fail over */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("ws")
                .setAllowedOrigins(Urls.frontendUrl)
                .withSockJS();
    }

    /* Enabled Stomp messaging protocol */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        registry.enableSimpleBroker();
    }
}
