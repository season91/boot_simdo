package com.kh.simdo.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping("/")
    public String contextPath() {
        return "/index";
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

}
