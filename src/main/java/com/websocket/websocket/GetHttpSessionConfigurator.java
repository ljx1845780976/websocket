package com.websocket.websocket;


import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 **/
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        //将httpSession存储到EndpointConfig中,这里key没必要唯一，因为每个客户端下的sec都不一样
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
