package com.kh.simdo.users;

import com.kh.simdo.users.form.JoinForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("user")
@Controller
public class UsersController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("login")
    public String login() {
        return "user/login";
    }

    @GetMapping("join")
    public String join() {
        return "user/join";
    }

    @PostMapping("joinimpl")
    public String joinImpl(@Valid JoinForm joinForm) {
        usersService.saveUser(joinForm);
        return "/index";
    }

}
