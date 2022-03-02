package com.websocket.entity;/**
 *
 **/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @introduction
 * @author ljz
 * @date 2022年02月28日 16:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage {

    /**
     * 是否是系统消息
     */
    private boolean systemMsgFlag;
    /**
     * 发送方Name
     */
    private String fromName;
    /**
     * 发送的数据
     */
    private Object message;

}
