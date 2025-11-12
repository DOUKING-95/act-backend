package com.health_donate.health.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // active le support WebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // "/topic" = les messages que le serveur envoie vers les clients
        config.enableSimpleBroker("/topic");

        // "/app" = les messages que les clients envoient vers le serveur
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Le point d’entrée WebSocket de ton backend
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // autorise Flutter à s’y connecter
                .withSockJS(); // pour compatibilité navigateur
    }
}

