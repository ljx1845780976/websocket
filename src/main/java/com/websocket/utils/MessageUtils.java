package com.websocket.utils;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.websocket.entity.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 *
 **/
@Data
@AllArgsConstructor
public class MessageUtils {

    /**
     * 封装响应的消息，注意系统消息的发送fromName为null;封装好的响应为json如下，例如
     *  1.系统消息： {“systemMsgFlag”: true, "fromName": null, "message": ["Name1", "Name2"]}.
     *  2.非系统消息 {“systemMsgFlag”: false, "fromName": "YYJ", "message": “你在哪里呀？”}.
     * @param isSystemMsg 是否是系统消息
     * @param fromName 该消息来自谁
     * @param msg 具体消息
     * @return Json格式的发送内容
     */
    public static String getMessage(boolean isSystemMsg,String fromName,Object msg){
        ResultMessage rm=new ResultMessage();
        rm.setSystemMsgFlag(isSystemMsg);
        rm.setMessage(msg);
        /*如果不是系统消息就传入fromName*/
        if(!isSystemMsg){
            rm.setFromName(fromName);
        }
        String jsonString = JSON.toJSONString(rm);
        return jsonString;

    }


}
