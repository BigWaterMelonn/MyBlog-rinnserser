package blog.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.example.service.UserService;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }
    
    
//在注册成功后直接重定向到登录页面
    @PostMapping("/register/process")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String tel) {
        if (userService.createAccount(username, email, password, tel)) {
            return "redirect:/user/login";
        } else {
            return "register.html";
        }
    }
}