package com.websocket.controller;

import com.websocket.dto.DataBase;
import com.websocket.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *
 **/
@Controller
@RequestMapping
public class LoginController {

    @PostMapping("/login")
    public String login(User user, HttpSession httpSession, ModelMap modelMap){
        String username = user.getUsername();
        String password = user.getPassword();
        if (DataBase.existUsername(username)) {
            if (password!=null&&DataBase.getPassword(username).equals(password)){
                //这个要为登录成功的用户创建一个该用户自己的session，
                // 复习：每个客户端都有自己与服务器的唯一sessionId，这个sessionId也就是cookie
                //    seesionId是cookie的key，cookie的值也就是与服务器建立session连接的唯一值
                //    建立连接后，可通过这个自己的唯一session写入各种值。
                httpSession.setAttribute("username",username);
                return "chat";
            }
        }
        modelMap.addAttribute("error","密码或用户名错误");
        return "index";
    }


    @GetMapping("/getUsername")
    @ResponseBody
    public String getUsername(HttpSession httpSession){
        return (String) httpSession.getAttribute("username");
    }
}
