package com.kh.simdo.mypage;

import com.kh.simdo.movie.MovieService;
import com.kh.simdo.mypage.fmsline.Fmsline;
import com.kh.simdo.mypage.fmsline.FmslineService;
import com.kh.simdo.mypage.fmsline.form.FmslineForm;
import com.kh.simdo.mypage.fmsline.form.UpdateFmslineForm;
import com.kh.simdo.mypage.review.Review;
import com.kh.simdo.mypage.review.ReviewService;
import com.kh.simdo.mypage.review.form.ReviewForm;
import com.kh.simdo.mypage.review.form.UpdateReviewForm;
import com.kh.simdo.user.UserAccount;
import com.kh.simdo.user.UserRepository;
import com.kh.simdo.user.UserService;
import com.kh.simdo.user.form.MyPwdForm;
import com.kh.simdo.user.validator.MyPwdFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.text.SimpleDateFormat;

@RequestMapping("mypage")
@Controller
public class MypageController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final FmslineService fmslineService;
    private final UserService userService;
    private final MyPwdFormValidator myPwdFormValidator;

    public MypageController(MovieService movieService, ReviewService reviewService, FmslineService fmslineService, UserService userService, MyPwdFormValidator myPwdFormValidator) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.fmslineService = fmslineService;
        this.userService = userService;
        this.myPwdFormValidator = myPwdFormValidator;
    }

    @InitBinder(value = "myPwdForm")
    public void setMyPwdFormValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(myPwdFormValidator);
    }

    //mypage 메인
    @GetMapping("mycalendar")
    public String myCalendar() {
        return "mypage/mycalendar";
    }

    //영화 후기 작성 페이지로 이동
    @GetMapping("writereview")
    public String writeReview(String mvNo, Model model) {
        model.addAttribute("movie", movieService.movieDetail(mvNo));

        return "mypage/writereview";
    }

    //영화 후기 insert
    @PostMapping("reviewupload")
    public String reviewUpload(ReviewForm reviewForm, Model model, @AuthenticationPrincipal UserAccount userAccount) {
        Review review = new Review();
        review.setMovie(movieService.movieDetail(reviewForm.getMvNo()));
        review.setUser(userAccount.getUser());
        review.setWatchDate(Date.valueOf(reviewForm.getWatchDate()));
        review.setReviewScore(reviewForm.getReviewScore());
        review.setReviewContent(reviewForm.getReviewContent());

        reviewService.saveReview(review);

        model.addAttribute("alertMsg", "영화 후기가 등록되었습니다.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

    //영화 명대사 작성 페이지로 이동
    @GetMapping("writefmsline")
    public String writeFmsline(String mvNo, Model model) {
        model.addAttribute("movie", movieService.movieDetail(mvNo));

        return "mypage/writefmsline";
    }

    //영화 명대사 insert
    @PostMapping("fmslineupload")
    public String fmslineUpload(FmslineForm fmslineForm, Model model, @AuthenticationPrincipal UserAccount userAccount) {
        Fmsline fmsline = new Fmsline();
        fmsline.setMovie(movieService.movieDetail(fmslineForm.getMvNo()));
        fmsline.setUser(userAccount.getUser());
        fmsline.setFmlContent(fmslineForm.getFmlContent());

        fmslineService.saveFmsline(fmsline);

        model.addAttribute("alertMsg", "영화 명대사가 등록되었습니다.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

    //내가 쓴 영화 후기, 명대사 목록 조회
    @GetMapping("myreview")
    public String myReview(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        model.addAttribute("review", reviewService.findByUserAndIsReviewDelOrderByReviewRegDateDesc(userAccount.getUser()));
        model.addAttribute("fmsline", fmslineService.findByUserAndIsFmlDelOrderByFmlRegDateDesc(userAccount.getUser()));

        return "mypage/myreview";
    }

    //영화 후기 삭제(update reviewDel 1)
    @GetMapping("delreview")
    @ResponseBody
    public String delReview(String reviewNo) {
        reviewService.deleteReview(reviewNo);

        return "success";
    }

    //영화 명대사 삭제(update fmlDel 1)
    @GetMapping("delfmsline")
    @ResponseBody
    public String delFmsline(String fmslineNo) {
        fmslineService.deleteFmsline(fmslineNo);

        return "success";
    }

    //영화 후기 수정 페이지로 이동
    @GetMapping("modifyreview")
    public String modifyReview(String reviewNo, Model model) {
        Review review = reviewService.findByReviewNo(reviewNo);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String watchDate = simpleDateFormat.format(review.getWatchDate());
        model.addAttribute("review", review);
        model.addAttribute("watchDate", watchDate);

        return "mypage/modifyreview";
    }

    //영화 후기 수정
    @PostMapping("updatereview")
    public String updateReview(UpdateReviewForm updateReviewForm, Model model) {
        Review review = reviewService.findByReviewNo(updateReviewForm.getReviewNo());
        review.setReviewScore(updateReviewForm.getReviewScore());
        review.setReviewContent(updateReviewForm.getReviewContent());
        review.setWatchDate(Date.valueOf(updateReviewForm.getWatchDate()));
        reviewService.saveReview(review);

        model.addAttribute("alertMsg", "영화 후기가 수정되었습니다.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

    //영화 명대사 수정 페이지로 이동
    @GetMapping("modifyfml")
    public String modifyFmsline(String fmslineNo, Model model) {
        Fmsline fmsline = fmslineService.findByFmslineNo(fmslineNo);
        model.addAttribute("fmsline", fmsline);

        return "mypage/modifyfmsline";
    }

    //영화 명대사 수정
    @PostMapping("updatefmsline")
    public String updateFmsline(UpdateFmslineForm updateFmslineForm, Model model) {
        Fmsline fmsline = fmslineService.findByFmslineNo(updateFmslineForm.getFmslineNo());
        fmsline.setFmlContent(updateFmslineForm.getFmlContent());
        fmslineService.saveFmsline(fmsline);

        model.addAttribute("alertMsg", "영화 명대사가 수정되었습니다.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

    //myinfo 메인 페이지로 이동(비밀번호/회원정보 변경 선택 창)
    @GetMapping("myinfomain")
    public String myInfoMain() {
        return "mypage/myinfomain";
    }

    //비밀번호 변경 페이지로 이동
    @GetMapping("mypwd")
    public String myPwd(MyPwdForm myPwdForm) {
        return "mypage/mypwd";
    }

    //비밀번호 변경
    @PostMapping("mypwdimpl")
    public String myPwdImpl(@Valid MyPwdForm myPwdForm, Errors errors, Model model) {
        System.out.println("넘어온 값 : " + myPwdForm);
        if (errors.hasErrors()) {
            return "mypage/mypwd";
        }

        userService.setNewPwd(myPwdForm);

        model.addAttribute("alertMsg", "비밀번호가 변경되었습니다.");
        model.addAttribute("url", "/mypage/myinfomain");

        return "common/result";
    }

}
