package com.yyy.blog.web.admin;


import com.yyy.blog.po.User;
import com.yyy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    //输入用户名密码之后点提交要调用的方法
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attribute){
        //到数据库中查询用户名和密码是否存在，返回一个User对象
        User user = userService.checkUser(username, password);
        //判断User是否为空，非空代表正确，跳index页面，session把user带过去
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }
        //空表示不正确，这里要把错误信息带过去，不能用Model，因为使用的是重定向
        else {
            attribute.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
