package com.kh.simdo.user;

import com.kh.simdo.user.form.JoinForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("user")
@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login() {
        return "user/login";
    }

    @GetMapping("join")
    public String join(JoinForm joinForm) {
        return "user/join";
    }

    @PostMapping("joinimpl")
    public String joinImpl(JoinForm joinForm) {
        userService.saveUser(joinForm);
        return "/index";
    }

}
