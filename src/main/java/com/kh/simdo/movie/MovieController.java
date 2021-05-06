package com.kh.simdo.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/movie")
public class MovieController {


    private final MovieService movieService;

    @GetMapping(value = "/db")
    public String getMovie(String title){
        // 영화 정보를 받은 후에 movie에 저장해준다.
        Map<String, Object> movieMap = movieService.kmdbAPI(title);
        String thumbnail = movieService.naverMovieAPI(title);
        movieService.saveMovie(movieMap, thumbnail);
        return "index";
    }
}
