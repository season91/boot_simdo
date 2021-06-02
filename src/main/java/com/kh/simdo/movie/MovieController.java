package com.kh.simdo.movie;

import com.google.gson.Gson;
import com.kh.simdo.mypage.fmsline.Fmsline;
import com.kh.simdo.mypage.fmsline.FmslineService;
import com.kh.simdo.mypage.review.Review;
import com.kh.simdo.mypage.review.ReviewService;
import com.kh.simdo.user.UserAccount;
import com.kh.simdo.wish.WishService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/movie")
public class MovieController {


    private final MovieService movieService;
    private final ReviewService reviewService;
    private final WishService wishService;
    private final FmslineService fmslineService;

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
    public String movieDetail(String mvNo, @AuthenticationPrincipal UserAccount userAccount, Model model){
        // 1. 영화정보 전송
        model.addAttribute("movie",movieService.movieDetail(mvNo));

        // 2-1. 후기여부 전송
        Movie movie = new Movie();
        movie.setMvNo(mvNo);
        model.addAttribute("isReview", reviewService.isReview(userAccount.getUser(), movie));
        
        // 2-2. 후기내역 전송
        model.addAttribute("reviewList", reviewService.findReviewList(movie, false));

        // 3. 찜여부 전송
        model.addAttribute("wish", wishService.findWish(userAccount.getUser().getUserNo(), mvNo, false));

        // 4. 명대사내역 전송
        model.addAttribute("fmsList",fmslineService.findFmsList(movie, false));

        return "movie/detail";
    }

    // 명대사 번역, 비동기통신
    @PostMapping("translation")
    @ResponseBody
    public String fmsTranslation(@RequestParam String data){

        Gson gson = new Gson();
        Map<String, String> parsedData = gson.fromJson(data, Map.class);

        String text = (String) parsedData.get("text");
        String lan = (String) parsedData.get("lan");

        String res = movieService.papagoAPI(text, lan);
        if(res == null){
            return "fail";
        }
        return res;
    }

    // 대본보러가기
    @GetMapping("script")
    public String movieScript(String mvNo, Model model){
        model.addAttribute("movie", movieService.movieDetail(mvNo));
        return "movie/script";
    }

    //대본 다운로드, 비동기통신
    @GetMapping("script-down")
    @ResponseBody
    public ResponseEntity<FileSystemResource> movieScriptDown(String mvNo, Model model){
        Movie movie = movieService.movieDetail(mvNo);

        File file = movieService.scirptDownload(movie.getMvTitleorg().trim()+".txt", movie.getScript());
        //내보내기
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName(), Charset.forName("UTF-8"))
                .build());

        FileSystemResource resource = new FileSystemResource(file);
        return ResponseEntity.ok().headers(headers).body(resource);

    }

    //영화 검색
    @GetMapping("search")
    public String movieSearch(String keyword, Model model){
        System.out.println(keyword);
        model.addAttribute("movieList", movieService.searchMovie(keyword));
        return "movie/movielist";
    }

    //영화상세내 댓글 좋아요. 비동기통신
    @GetMapping("review-like")
    @ResponseBody
    public int reviewLike(String reviewNo){
        Review review = reviewService.reviewLike(reviewNo);
        return review.getReviewLike();
    }

    //영화상세내 댓글 싫어요. 비동기통신
    @GetMapping("review-hate")
    @ResponseBody
    public int reviewHate(String reviewNo){
        Review review = reviewService.reviewHate(reviewNo);
        return review.getReviewHate();
    }

    @GetMapping("fmsline-like")
    @ResponseBody
    public int fmslineLike(String fmslineNo){
        Fmsline fmsline = fmslineService.fmslineLike(fmslineNo);
        return fmsline.getFmlLike();
    }
}
