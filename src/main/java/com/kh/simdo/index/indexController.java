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

    @GetMapping("/about")
    public void about(){}

    @GetMapping("/blog")
    public void blog(){}

    @GetMapping("/contact")
    public void contact(){}

    @GetMapping("/blog-single")
    public void single(){};
}
