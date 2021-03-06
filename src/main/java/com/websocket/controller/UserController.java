package com.websocket.controller;

import com.websocket.dto.DataBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    /*
    *  获取好友列表
    * */
    @GetMapping("/getFriends")
    public List<String> getFriends(HttpSession httpSession){
        String currUsername =(String) httpSession.getAttribute("username");
        return DataBase.getOthers(currUsername);
    }
}
