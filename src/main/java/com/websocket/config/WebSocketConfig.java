package com.websocket.config;

import org.apache.catalina.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *
 **/
@Configuration
public class WebSocketConfig {


    //注入ServerEndpointExporter 的Bean对象，
    // 该对象会自动向容器注册加了@ServerEndpoint注解的Bean，也即ChatEnpoint
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
