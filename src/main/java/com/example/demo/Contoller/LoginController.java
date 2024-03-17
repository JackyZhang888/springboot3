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

/**
 * 登录控制类，处理用户登录相关的请求
 */
@Controller
@Slf4j
public class LoginController {
    @Autowired
    UserService userService; // 注入UserService，用于用户信息的查询

    /**
     * 显示登录页面
     *
     * @return 返回登录页面的视图名称
     */
    @GetMapping(value = {"/"})
    public String loginPage() {
        return "login";
    }

    /**
     * 处理用户登录请求
     *
     * @param user 用户输入的登录信息
     * @param session 用户的会话
     * @param model 用于在视图和控制器之间传递数据
     * @return 根据登录结果返回的视图名称或错误信息
     */
    @PostMapping("login")
    public String login(User user, HttpSession session, Model model) {
        try {
            // 查询数据库中是否存在该用户名
            User userCache = userService.getUserByName(user.getUsername());
            if (userCache != null) {
                // 验证账号密码是否正确
                if (userCache.getPassword().equals(user.getPassword())) {
                    // 登录成功，将用户信息保存至session
                    session.setAttribute("loginUser", user);
                    log.info("login success! userName:{}, role:{}", user.getUsername(), userCache.getRole());
                    // 重定向到成功页面
                    return "redirect:/success";
                } else {
                    // 账号或密码错误，返回登录页面并显示错误信息
                    model.addAttribute("msg", "账号或者密码有误!");
                    log.error("error userName:{}, password:{}", user.getUsername(), user.getPassword());
                    return "login";
                }
            } else {
                // 用户名不存在，返回登录页面并显示错误信息
                model.addAttribute("msg", "账号或者密码有误");
                log.error("get user failed!");
                return "login";
            }
        } catch (Exception e) {
            // 捕获登录过程中的异常
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * 登录成功后的页面
     *
     * @param session 用户的会话
     * @param model 用于在视图和控制器之间传递数据
     * @return 根据登录状态返回用户页面或登录页面
     */
    @GetMapping("success")
    public String UserPage(HttpSession session, Model model) {
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser != null) {
            // 已登录，显示用户页面
            model.addAttribute("user", loginUser.getUsername());
            return "upload";
        } else {
            // 未登录，返回登录页面
            model.addAttribute("msg", "请登录");
            return "login";
        }
    }
}
