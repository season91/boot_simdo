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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

@RequestMapping("mypage")
@Controller
public class MypageController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final FmslineService fmslineService;

    public MypageController(MovieService movieService, ReviewService reviewService, FmslineService fmslineService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.fmslineService = fmslineService;
    }

    @GetMapping("mycalendar")
    public String myCalendar() {
        return "mypage/mycalendar";
    }

    @GetMapping("writereview")
    public String writeReview(String mvNo, Model model) {
        model.addAttribute("movie", movieService.movieDetail(mvNo));

        return "mypage/writereview";
    }

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

    @GetMapping("writefmsline")
    public String writeFmsline(String mvNo, Model model) {
        model.addAttribute("movie", movieService.movieDetail(mvNo));

        return "mypage/writefmsline";
    }

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

    @GetMapping("myreview")
    public String myReview(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        model.addAttribute("review", reviewService.findByUserAndIsReviewDelOrderByReviewRegDateDesc(userAccount.getUser()));
        model.addAttribute("fmsline", fmslineService.findByUserAndIsFmlDelOrderByFmlRegDateDesc(userAccount.getUser()));

        return "mypage/myreview";
    }

    @GetMapping("delreview")
    @ResponseBody
    public String delReview(String reviewNo) {
        reviewService.deleteReview(reviewNo);

        return "success";
    }

    @GetMapping("delfmsline")
    @ResponseBody
    public String delFmsline(String fmslineNo) {
        fmslineService.deleteFmsline(fmslineNo);

        return "success";
    }

    @GetMapping("modifyreview")
    public String modifyReview(String reviewNo, Model model) {
        Review review = reviewService.findByReviewNo(reviewNo);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String watchDate = simpleDateFormat.format(review.getWatchDate());
        model.addAttribute("review", review);
        model.addAttribute("watchDate", watchDate);

        return "mypage/modifyreview";
    }

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

    @GetMapping("modifyfml")
    public String modifyFmsline(String fmslineNo, Model model) {
        Fmsline fmsline = fmslineService.findByFmslineNo(fmslineNo);
        model.addAttribute("fmsline", fmsline);

        return "mypage/modifyfmsline";
    }

    @PostMapping("updatefmsline")
    public String updateFmsline(UpdateFmslineForm updateFmslineForm, Model model) {
        Fmsline fmsline = fmslineService.findByFmslineNo(updateFmslineForm.getFmslineNo());
        fmsline.setFmlContent(updateFmslineForm.getFmlContent());
        fmslineService.saveFmsline(fmsline);

        model.addAttribute("alertMsg", "영화 명대사가 수정되었습니다.");
        model.addAttribute("url", "/mypage/myreview");

        return "common/result";
    }

}
