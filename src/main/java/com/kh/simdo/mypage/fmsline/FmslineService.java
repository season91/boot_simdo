package com.kh.simdo.mypage.fmsline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.simdo.common.util.http.HttpUtils;
import com.kh.simdo.movie.Movie;
import com.kh.simdo.user.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FmslineService {

    private final FmslineRepository fmslineRepository;

    public FmslineService(FmslineRepository fmslineRepository) {
        this.fmslineRepository = fmslineRepository;
    }

    public void saveFmsline(Fmsline fmsline) {
        fmslineRepository.save(fmsline);
    }

    public List<Fmsline> findByUserAndIsFmlDelOrderByFmlRegDateDesc(User user) {
        return fmslineRepository.findByUserAndIsFmlDelOrderByFmlRegDateDesc(user, false);
    }

    public Fmsline findByFmslineNo(String fmslineNo) {
        return fmslineRepository.findByFmslineNo(fmslineNo);
    }

    public void deleteFmsline(String fmslineNo) {
        Fmsline fmsline = fmslineRepository.findByFmslineNo(fmslineNo);
        fmsline.setFmlDel(true);

        fmslineRepository.save(fmsline);
    }

    //아영: 영화상세에 들어갈 명대사 내역
    public List<Fmsline> findFmsList(Movie movie, boolean isFmlDel){
        return fmslineRepository.findByMovieAndIsFmlDel(movie, isFmlDel);
    }

}
