package com.kh.simdo.wish;

import com.kh.simdo.user.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/wish")
public class WishController {

    private final WishService wishService;

    //찜 하기, 비동기 통신
    @GetMapping("/add")
    @ResponseBody
    public String addWish(String mvNo, @AuthenticationPrincipal UserAccount userAccount){
        Wish wish = new Wish();
        wish.setMvNo(mvNo);
        wish.setUserNo(userAccount.getUser().getUserNo());

        wishService.saveWish(wish);

        if(wish == null){
            return "fail";
        }
        return "success";
    }

    //찜 해제하기. isDel 1 처리.
    @GetMapping("/del")
    @ResponseBody
    public String delWish(String mvNo, @AuthenticationPrincipal UserAccount userAccount){
        Wish wish = new Wish();
        wish.setMvNo(mvNo);
        wish.setUserNo(userAccount.getUser().getUserNo());

        wishService.saveWish(wish);

        if(wish == null){
            return "fail";
        }
        return "success";
    }
}
