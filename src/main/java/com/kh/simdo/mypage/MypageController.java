package com.kh.simdo.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("mypage")
@Controller
public class MypageController {

    @GetMapping("mycalendar")
    public String myCalendar() {
        return "mypage/mycalendar";
    }

    @GetMapping("writereview")
    public String writeReview() {
        return "mypage/writereview";
    }

}
