package com.websocket.entity;

import lombok.Data;

/**
 *
 **/
@Data
public class Message {

    /*给谁发送*/
    private String toName;
    private String message;


}
