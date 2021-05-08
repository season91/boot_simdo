package com.kh.simdo.user;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.ToAlertException;
import com.kh.simdo.common.sms.SmsSender;
import com.kh.simdo.user.form.JoinForm;
import com.kh.simdo.user.validator.JoinFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("user")
@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SmsSender smsSender;

    private final UserService userService;
    private final JoinFormValidator joinFormValidator;

    public UserController(UserService userService, JoinFormValidator joinFormValidator) {
        this.userService = userService;
        this.joinFormValidator = joinFormValidator;
    }

    @InitBinder(value = "joinForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormValidator);
    }

    @GetMapping("login")
    public String login() {
        return "user/login";
    }

    @GetMapping("join")
    public void join(JoinForm joinForm) {}

    @GetMapping("emailcheck")
    @ResponseBody
    public String emailCheck(String userEmail) {
        if (userService.findUserByUserEmail(userEmail)) {
            return "fail";
        }

        return "success";
    }

    @GetMapping("telauth")
    @ResponseBody
    public String authenticateTel(String userTel, HttpSession session) {
        int res = smsSender.authenticateTel(userTel, session);

        if (res == 202) {
            return "success";
        }

        return "fail";
    }

    @GetMapping("telconfirm")
    @ResponseBody
    public String certConfirm(String certNum, HttpSession session) {
        String rightNumber = (String) session.getAttribute("certNum");

        if(certNum.equals(rightNumber)) {
            return "success";
        }

        return "fail";
    }

    @PostMapping("mailauth")
    public String authenticateEmail(@Valid JoinForm joinForm, Errors errors, HttpSession session, Model model) {
        if (errors.hasErrors()) {
            return "user/join";
        }

        String authPath = UUID.randomUUID().toString();
        session.setAttribute("authPath", authPath);
        session.setAttribute("joinForm", joinForm);

        userService.authenticateEmail(joinForm, authPath);

        model.addAttribute("alertMsg", "이메일이 전송되었습니다. 이메일 링크를 통해 회원가입을 완료해주세요.");
        model.addAttribute("url", "/user/login");

        return "common/result";
    }

    @GetMapping("joinimpl/{authPath}")
    public String joinImpl(@PathVariable("authPath") String urlPath,
                           @SessionAttribute("authPath") String sessionPath,
                           @SessionAttribute("joinForm") JoinForm joinForm,
                           Model model,
                           HttpSession session) {
        if (!urlPath.equals(sessionPath)) {
            throw new ToAlertException(ErrorCode.EXPIRED_LINK);
        }

        userService.saveUser(joinForm);
        session.removeAttribute("joinForm");
        session.removeAttribute("authPath");

        model.addAttribute("alertMsg", "회원가입이 완료되었습니다.");
        model.addAttribute("url", "/user/login");

        return "user/login";
    }

}
