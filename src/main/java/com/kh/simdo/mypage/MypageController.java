package com.kh.simdo.mypage;

import com.kh.simdo.movie.MovieService;
import com.kh.simdo.mypage.review.Review;
import com.kh.simdo.mypage.review.ReviewService;
import com.kh.simdo.mypage.review.form.ReviewForm;
import com.kh.simdo.user.UserAccount;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;

@RequestMapping("mypage")
@Controller
public class MypageController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    public MypageController(MovieService movieService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
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

    @GetMapping("myreview")
    public String myReview() {
        return "mypage/myreview";
    }

}
