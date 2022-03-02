package com.websocket.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.websocket.entity.Message;
import com.websocket.entity.ResultMessage;
import com.websocket.utils.MessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 **/
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component //加了该注解还不能完全注入容器，还得配置一个配置类
public class ChatEndpoint {
    private final static Logger LOGGER = LogManager.getLogger(ChatEndpoint.class);


    /*每一个客户端都会生成一个新ChatEndpoint类，ChatEndpoint不能是单例的*/
    private static Map<String,ChatEndpoint> onlineUsers=new ConcurrentHashMap<>();

    /*session对象，可以发送信息给指定的用户*/
    private Session session;

    /*httpSession对象，可以获取到之前登录的用户名*/
    private HttpSession httpSession;



    /*连接websocket时调用*/
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        LOGGER.info("已连接");

        /*1、将局部session传给本对象的session*/
        this.session=session;

        /*2、要想拿到调用websocket服务的客户端的httpSession
          得利用参数EndpointConfig,先创建一个GetHttpSessionConfigurator类
        */

        this.httpSession=
                (HttpSession)config.getUserProperties().get(HttpSession.class.getName());

        /*3、将当前对象存储到容器中,先从httpSession中获取用户名*/
        String  username = (String)this.httpSession.getAttribute("username");
        onlineUsers.put(username,this);

        /*4、将当前上线的用户名，通过系统消息推给其他客户端*/
        String msg = MessageUtils.getMessage(true, null, getUsernames());
        broadcastAllUsers(msg);
    }

    private Set<String> getUsernames(){
        return onlineUsers.keySet();
    }

    /*广播给全部用户*/
    private void broadcastAllUsers(String msg){
        //要将该msg广播给所有用户，前端的onMessage会收到
        Set<String> usernames = onlineUsers.keySet();
        for (String username : usernames) {
            ChatEndpoint chatEndpoint = onlineUsers.get(username);
            //核心组件：（同步，异步是AsyncRemote）用来推送消息到前端的onMessage
            RemoteEndpoint.Basic basicRemote = chatEndpoint.session.getBasicRemote();
            try {
                basicRemote.sendText(msg);
            } catch (IOException e) {
                LOGGER.error("广播发送系统消息失败！{}", e);
                e.printStackTrace();
            }
        }
    }

    /*接收到客户端发送的数据时调用*/
    @OnMessage
    public void onMessage(String message,Session session){
        Message msg = JSON.parseObject(message, Message.class);
        String  username = (String )this.httpSession.getAttribute("username");
        String  toName = msg.getToName();
        String res = MessageUtils.getMessage(false, username, msg);
        try {
            onlineUsers.get(toName).session.getBasicRemote().sendText(res);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*连接关闭时调用*/
    @OnClose
    public void onClose(Session session){
        String  username = (String )this.httpSession.getAttribute("username");
        ChatEndpoint remove = onlineUsers.remove(username);
        String massage = MessageUtils.getMessage(true, null, getUsernames());
        remove.broadcastAllUsers(massage);
        LOGGER.info("已断开连接");
    }


}
