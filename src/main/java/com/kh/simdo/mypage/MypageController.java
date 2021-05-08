package com.kh.simdo.mypage;

import com.kh.simdo.movie.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("mypage")
@Controller
public class MypageController {

    private final MovieService movieService;

    public MypageController(MovieService movieService) {
        this.movieService = movieService;
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

}
