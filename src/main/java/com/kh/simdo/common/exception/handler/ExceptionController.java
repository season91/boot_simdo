package com.kh.simdo.common.exception.handler;


import com.kh.simdo.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Controller
@ControllerAdvice(basePackages = {"com.kh.simdo"})
public class ExceptionController {

    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    public String businessExceptionHandler(CustomException e, Model model){
        model.addAttribute("alertMsg",e.error.errMsg());
        model.addAttribute("url", e.error.url());
        return "common/result";
    }

    public String dataAccessExceptionHandler(DataAccessException e, Model model){
        e.printStackTrace();
        model.addAttribute("alertMsg","데이터 베이스 접근 중 에러가 발생하였습니다.");
        model.addAttribute("url", "/member/login");

        return "common/result";
    }

}
