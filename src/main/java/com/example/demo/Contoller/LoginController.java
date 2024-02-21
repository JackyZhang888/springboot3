package com.example.demo.Contoller;

import com.example.demo.Bean.User;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping(value = {"/"})
    public String loginPage() {
        // 返回login.html
        return "login";
    }

    @PostMapping("login")
    public String login(User user, HttpSession session, Model model) {
        try {
            //先查找一下有没有该账号
            User userCache = userService.getUserByName(user.getUsername());
            if (userCache != null) {
                //如果有账号则判断账号密码是否正确
                if (userCache.getPassword().equals(user.getPassword())) {
                    //添加到session保存起来
                    session.setAttribute("loginUser", user);
                    log.info("login success! userName:{}, role:{}", user.getUsername(), userCache.getRole());
                    //重定向到@GetMapping("success")
                    return "redirect:/success";
                } else {
                    //如果密码错误，则提示输入有误
                    model.addAttribute("msg", "账号或者密码有误!");
                    log.error("error userName:{}, password:{}", user.getUsername(), user.getPassword());
                    return "login";
                }
            } else {
                model.addAttribute("msg", "账号或者密码有误");
                log.error("get user failed!");
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("success")
    public String UserPage(HttpSession session, Model model) {
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("user", loginUser.getUsername());
            // upload.html
            return "upload";
        } else {
            model.addAttribute("msg", "请登录");
            return "login";
        }
    }
}
