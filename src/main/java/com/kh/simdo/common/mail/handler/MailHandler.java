package com.kh.simdo.common.mail.handler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MailHandler {

    @PostMapping("mail")
    public String writeMail(@RequestParam Map<String, Object> data, Model model) {

        // jsp에 뿌려줄 데이터를 모델이 담아준다.
        model.addAllAttributes(data);
        // view
        return "mail-template/" + data.get("mail-template");


    }
}
