package com.kh.simdo.qna;

import com.kh.simdo.user.UserAccount;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/qna")
public class QnAController {

    private final QnAService qnAService;

    // q&a 목록
    @GetMapping("/list")
    public String qnaList(@AuthenticationPrincipal UserAccount userAccount, @RequestParam(defaultValue = "1") int page, Model model){
        model.addAllAttributes(qnAService.qnaList(userAccount.getUser(), false, PageRequest.of(page-1, 5, Sort.Direction.DESC,"qnaRegDate")));
        return "qna/qnalist";
    }

    // q&a admin 목록
    @GetMapping("/all-list")
    public String qnaAllList(@RequestParam(defaultValue = "1") int page, Model model){
        model.addAllAttributes(qnAService.qnaAllList(PageRequest.of(page-1, 5, Sort.Direction.DESC,"qnaRegDate")));
        return "qna/qnalist";
    }

    // q&a 작성하기 view
    @GetMapping("/write")
    public String qnaWrite(@AuthenticationPrincipal UserAccount userAccount){
        return "qna/write";
    }

    @GetMapping("/add")
    public String qnaAdd(@AuthenticationPrincipal UserAccount userAccount, QnA qna, Model model){

        qna.setUser(userAccount.getUser());

        QnA res = qnAService.insertQnA(qna);
        if(res == null){
            model.addAttribute("alertMsg","문의등록이 실패하였습니다.");
        }
        model.addAttribute("alertMsg","문의등록이 완료되었습니다.");
        model.addAttribute("url","/qna/list");
        return "common/result";
    }

    // q&a 업데이트하기 (코멘드 달기)
    @GetMapping("/coment")
    public String qnaComent(QnA qna, Model model){
        QnA res = qnAService.updateQnA(qna);
        
        if(res == null){
            model.addAttribute("alertMsg","답변등록이 실패하였습니다.");
        }
        
        model.addAttribute("alertMsg","답변등록이 완료되었습니다.");
        model.addAttribute("url","/qna/all-list");

        return "common/result";
    }

    // detail
    @GetMapping("/detail")
    public String qnaDetail(long qnaNo, Model model){
        System.out.println(qnaNo);
        model.addAttribute("qna", qnAService.findQnAByQnaNoAndIsDel(qnaNo, false));
        return "qna/detail";

    }

    

}
