package com.kh.simdo.user;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.ToAlertException;
import com.kh.simdo.common.sms.SmsSender;
import com.kh.simdo.user.form.FindPwdForm;
import com.kh.simdo.user.form.JoinForm;
import com.kh.simdo.user.validator.FindPwdFormValidator;
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
    private final FindPwdFormValidator findPwdFormValidator;

    public UserController(UserService userService, JoinFormValidator joinFormValidator, FindPwdFormValidator findPwdFormValidator) {
        this.userService = userService;
        this.joinFormValidator = joinFormValidator;
        this.findPwdFormValidator = findPwdFormValidator;
    }

    @InitBinder(value = "joinForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormValidator);
    }

    @InitBinder(value = "findPwdForm")
    public void setFindPwdFormValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(findPwdFormValidator);
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

        model.addAttribute("alertMsg", "???????????? ?????????????????????. ????????? ????????? ?????? ??????????????? ??????????????????.");
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

        model.addAttribute("alertMsg", "??????????????? ?????????????????????.");
        model.addAttribute("url", "/user/login");

        return "user/login";
    }

    @GetMapping("findpwd")
    public void findPwd(FindPwdForm findPwdForm) {}

    @PostMapping("findpwdimpl")
    public String findPwdImpl(@Valid FindPwdForm findPwdForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "user/findpwd";
        }

        User user = userService.checkToFindEmail(findPwdForm.getUserEmail(), findPwdForm.getUserTel());

        if (user != null) {
            //?????? ???????????? ??????
            String tmpPwd = userService.setTmpPwd(findPwdForm);
            //????????? ??????
            userService.findPwdEmail(findPwdForm, tmpPwd);

            model.addAttribute("alertMsg", "???????????? ?????????????????????. ?????? ??????????????? ??????????????????.");
            model.addAttribute("url", "/user/login");
        }else {
            model.addAttribute("alertMsg", "???????????? ??????????????? ?????? ??????????????????.");
            model.addAttribute("url", "/user/findpwd");
        }

        return "common/result";
    }

}
