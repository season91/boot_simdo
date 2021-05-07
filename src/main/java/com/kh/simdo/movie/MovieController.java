package com.kh.simdo.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/movie")
public class MovieController {


    private final MovieService movieService;

    // DB확정전까지 영화 2개 불러오는 용도
    @GetMapping(value = "/tempmovie")
    public String tempMovie(){
        return "movie/tempmovie";
    }


    @GetMapping(value = "/db")
    public String getMovie(String title){
        // 영화 정보를 받은 후에 movie에 저장해준다.
        Map<String, Object> movieMap = movieService.kmdbAPI(title);
        movieService.saveMovie(movieMap);
        return "index";
    }

    // 영화 목록
    @GetMapping(value = "/movielist")
    public String movieList(Model model){
        model.addAttribute("movieList", movieService.movieTotalList());
        return "movie/movielist";
    }

    // 영화 상세
    @GetMapping(value = "/detail")
    public String movieDetail(String mvNo, Model model){
        model.addAttribute("movie",movieService.movieDetail(mvNo));
        return "movie/detail";
    }
}
