package com.kh.simdo.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/wish")
public class WishController {

    private final WishService wishService;

    //찜 하기, 비동기 통신!!
    @GetMapping("/add")
    @ResponseBody
    public String addWish(String mvNo){

        return "success";
    }
}
