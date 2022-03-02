package com.websocket.dto;

import ch.qos.logback.classic.turbo.TurboFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 **/
@Getter
public enum  DataBase {
       USER1("张三","123456"),
       USER2("李四","123456"),
       USER3("王五","123456");

       private String username;

       private String password;

       DataBase(String username,String password){
           this.username=username;
           this.password=password;
       }

    public static boolean existUsername(String username){
        DataBase[] values = DataBase.values();
        for (DataBase v:values){
            if (v.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public static String getPassword(String username){
           DataBase[] values = DataBase.values();
           for (DataBase v:values){
                if (v.getUsername().equals(username)){
                    return v.getPassword();
                }
           }
           return null;
       }

    public static List<String> getOthers(String username){
        List<String> list=new ArrayList<>();
        DataBase[] values = DataBase.values();
        for (DataBase v:values){
            if (!v.getUsername().equals(username)){
                list.add(v.getUsername());
            }
        }
        return list;
    }

}
