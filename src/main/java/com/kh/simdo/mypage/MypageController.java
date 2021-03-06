package com.kh.simdo.mypage;

import com.kh.simdo.movie.Movie;
import com.kh.simdo.movie.MovieService;
import com.kh.simdo.mypage.fmsline.Fmsline;
import com.kh.simdo.mypage.fmsline.FmslineService;
import com.kh.simdo.mypage.fmsline.form.FmslineForm;
import com.kh.simdo.mypage.fmsline.form.UpdateFmslineForm;
import com.kh.simdo.mypage.review.Review;
import com.kh.simdo.mypage.review.ReviewService;
import com.kh.simdo.mypage.review.form.ReviewForm;
import com.kh.simdo.mypage.review.form.UpdateReviewForm;
import com.kh.simdo.user.User;
import com.kh.simdo.user.UserAccount;
import com.kh.simdo.user.UserService;
import com.kh.simdo.user.form.MyInfoForm;
import com.kh.simdo.user.form.MyPwdForm;
import com.kh.simdo.user.validator.MyInfoFormValidator;
import com.kh.simdo.user.validator.MyPwdFormValidator;
import com.kh.simdo.wish.Wish;
import com.kh.simdo.wish.WishService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("mypage")
@Controller
public class MypageController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final FmslineService fmslineService;
    private final UserService userService;
    private final MyPwdFormValidator myPwdFormValidator;
    private final MyInfoFormValidator myInfoFormValidator;
    private final WishService wishService;

    public MypageController(MovieService movieService, ReviewService reviewService, FmslineService fmslineService
            , UserService userService, MyPwdFormValidator myPwdFormValidator, MyInfoFormValidator myInfoFormValidator, WishService wishService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.fmslineService = fmslineService;
        this.userService = userService;
        this.myPwdFormValidator = myPwdFormValidator;
        this.myInfoFormValidator = myInfoFormValidator;
        this.wishService = wishService;
    }

    @InitBinder(value = "myPwdForm")
    public void setMyPwdFormValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(myPwdFormValidator);
    }

    @InitBinder(value = "myInfoForm")
    public void setMyInfoFormValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(myInfoFormValidator);
    }

    //mypage ??????
    @GetMapping("mycalendar")
    public String myCalendar() {
        return "mypage/mycalendar";
    }

    //?????? ?????? ?????? ???????????? ??????
    @GetMapping("writereview")
    public String writeReview(String mvNo, Model model) {
        model.addAttribute("movie", movieService.movieDetail(mvNo));

        return "mypage/writereview";
    }

    //?????? ?????? insert
    @PostMapping("reviewupload")
    public String reviewUpload(ReviewForm reviewForm, Model model, @AuthenticationPrincipal UserAccount userAccount) {
        Review review = new Review();
        review.setMovie(movieService.movieDetail(reviewForm.getMvNo()));
        review.setUser(userAccount.getUser());
        review.setWatchDate(Date.valueOf(reviewForm.getWatchDate()));
        review.setReviewScore(reviewForm.getReviewScore());
        review.setReviewContent(reviewForm.getReviewContent());

        reviewService.saveReview(review);

        model.addAttribute("alertMsg", "?????? ????????? ?????????????????????.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

    //?????? ????????? ?????? ???????????? ??????
    @GetMapping("writefmsline")
    public String writeFmsline(String mvNo, Model model) {
        model.addAttribute("movie", movieService.movieDetail(mvNo));

        return "mypage/writefmsline";
    }

    //?????? ????????? insert
    @PostMapping("fmslineupload")
    public String fmslineUpload(FmslineForm fmslineForm, Model model, @AuthenticationPrincipal UserAccount userAccount) {
        Fmsline fmsline = new Fmsline();
        fmsline.setMovie(movieService.movieDetail(fmslineForm.getMvNo()));
        fmsline.setUser(userAccount.getUser());
        fmsline.setFmlContent(fmslineForm.getFmlContent());

        fmslineService.saveFmsline(fmsline);

        model.addAttribute("alertMsg", "?????? ???????????? ?????????????????????.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

    //?????? ??? ?????? ??????, ????????? ?????? ??????
    @GetMapping("myreview")
    public String myReview(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        model.addAttribute("review", reviewService.findByUserAndIsReviewDelOrderByReviewRegDateDesc(userAccount.getUser()));
        model.addAttribute("fmsline", fmslineService.findByUserAndIsFmlDelOrderByFmlRegDateDesc(userAccount.getUser()));

        return "mypage/myreview";
    }

    //?????? ?????? ??????(update reviewDel 1)
    @GetMapping("delreview")
    @ResponseBody
    public String delReview(String reviewNo) {
        reviewService.deleteReview(reviewNo);

        return "success";
    }

    //?????? ????????? ??????(update fmlDel 1)
    @GetMapping("delfmsline")
    @ResponseBody
    public String delFmsline(String fmslineNo) {
        fmslineService.deleteFmsline(fmslineNo);

        return "success";
    }

    //?????? ?????? ?????? ???????????? ??????
    @GetMapping("modifyreview")
    public String modifyReview(String reviewNo, Model model) {
        Review review = reviewService.findByReviewNo(reviewNo);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String watchDate = simpleDateFormat.format(review.getWatchDate());
        model.addAttribute("review", review);
        model.addAttribute("watchDate", watchDate);

        return "mypage/modifyreview";
    }

    //?????? ?????? ??????
    @PostMapping("updatereview")
    public String updateReview(UpdateReviewForm updateReviewForm, Model model) {
        Review review = reviewService.findByReviewNo(updateReviewForm.getReviewNo());
        review.setReviewScore(updateReviewForm.getReviewScore());
        review.setReviewContent(updateReviewForm.getReviewContent());
        review.setWatchDate(Date.valueOf(updateReviewForm.getWatchDate()));
        reviewService.saveReview(review);

        model.addAttribute("alertMsg", "?????? ????????? ?????????????????????.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

    //?????? ????????? ?????? ???????????? ??????
    @GetMapping("modifyfml")
    public String modifyFmsline(String fmslineNo, Model model) {
        Fmsline fmsline = fmslineService.findByFmslineNo(fmslineNo);
        model.addAttribute("fmsline", fmsline);

        return "mypage/modifyfmsline";
    }

    //?????? ????????? ??????
    @PostMapping("updatefmsline")
    public String updateFmsline(UpdateFmslineForm updateFmslineForm, Model model) {
        Fmsline fmsline = fmslineService.findByFmslineNo(updateFmslineForm.getFmslineNo());
        fmsline.setFmlContent(updateFmslineForm.getFmlContent());
        fmslineService.saveFmsline(fmsline);

        model.addAttribute("alertMsg", "?????? ???????????? ?????????????????????.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

    //myinfo ?????? ???????????? ??????(????????????/???????????? ?????? ?????? ???)
    @GetMapping("myinfomain")
    public String myInfoMain() {
        return "mypage/myinfomain";
    }

    //???????????? ?????? ???????????? ??????
    @GetMapping("mypwd")
    public String myPwd(MyPwdForm myPwdForm) {
        return "mypage/mypwd";
    }

    //???????????? ??????
    @PostMapping("mypwdimpl")
    public String myPwdImpl(@Valid MyPwdForm myPwdForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "mypage/mypwd";
        }

        userService.setNewPwd(myPwdForm);

        model.addAttribute("alertMsg", "??????????????? ?????????????????????.");
        model.addAttribute("url", "/mypage/myinfomain");

        return "common/result";
    }

    //???????????? ?????? ???????????? ??????
    @GetMapping("myinfo")
    public String myInfo(@AuthenticationPrincipal UserAccount userAccount, Model model, MyInfoForm myInfoForm) {
        long userNo = userAccount.getUser().getUserNo();
        User user = userService.findByUserNo(userNo);
        model.addAttribute("user", user);

        String userBirth = "";

        if (user.getUserBirth() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            userBirth = simpleDateFormat.format(user.getUserBirth());
        }

        model.addAttribute("userBirth", userBirth);

        return "mypage/myinfo";
    }

    //???????????? ??????
    @PostMapping("myinfoimpl")
    public String myInfoImpl(@Valid MyInfoForm myInfoForm, Errors errors, Model model) {
        System.out.println("????????? ??? : " + myInfoForm);
        if (errors.hasErrors()) {
            return "mypage/myinfo";
        }

        userService.updateInfo(myInfoForm);

        model.addAttribute("alertMsg", "??????????????? ?????????????????????.");
        model.addAttribute("url", "/mypage/myinfomain");

        return "common/result";
    }

    //????????? ???????????? ??????
    @GetMapping("mymovie")
    public String myMovie(long userNo, Model model) {
        List<Wish> wishList = wishService.findByUserNoAndIsWishDel(userNo, false);
        List<Movie> movieList = new ArrayList<>();

        for (Wish wish : wishList) {
            Movie movie = movieService.movieDetail(wish.getMvNo());
            movieList.add(movie);
        }

        model.addAttribute("wishList", wishList);
        model.addAttribute("movieList", movieList);

        return "mypage/mymovie";
    }

}
