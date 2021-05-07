package com.kh.simdo.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.simdo.common.util.http.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor //생성자 생성, 의존성 주입 편의성을 위해 사용. 대신 setter가 없는 메서드 강제로 생성되어, 필드값이 변경될 수 있다.
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;
    private final RestTemplate http;

    // 영화 기본 정보 가지고 올 영화 API
    public Map<String, Object> kmdbAPI(String title) {
        HttpUtils util = new HttpUtils();
        ObjectMapper om = new ObjectMapper();
        String SERVICE_KEY = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?ServiceKey=GE40RB7377JK9WJ72713&query=";

        // 쿼리문 변경해가면서 DB적재
        String url = "";
        switch (title){
            //1. 아이엠히어
            case "조커" :  url = SERVICE_KEY + "ToddPhillips&actor=호아킨피닉스&detail=Y&collection=kmdb_new2&listCount=1"; break;
            //2. 라라랜드
            case "라라랜드" : url = SERVICE_KEY+ "엠마&actor=라이언고슬링&detail=Y&collection=kmdb_new2&listCount=1"; break;
            default: break;
        }

        String json = util.get(url);

        Map resultMap = null;
        try {
            // Data 값 가져오기.
            Map resMap = om.readValue(json, Map.class);
            List<String> dataList = (List<String>) resMap.get("Data");
            String dataStr = om.writeValueAsString(dataList.get(0));
            Map dataMap = om.readValue(dataStr, Map.class);

            // Result값 가져오기.
            List<String> resultList = (List<String>) dataMap.get("Result");
            String resultStr = om.writeValueAsString(resultList.get(0));
            resultMap = om.readValue(resultStr, Map.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    // 영화 썸네일 가지고 올 네이버영화 API
    public String naverMovieAPI(String title) {
        HttpUtils util = new HttpUtils();
        String clientId = "1TOE19GYAcgawcD0ESm1";
        String clientSecret = "tmgwvMjtQF";
        ObjectMapper om = new ObjectMapper();
        String titleEncoder = null;

        try {

            titleEncoder = URLEncoder.encode(title, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + titleEncoder + "&yearfrom=2009&yearto=2019"; // json 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String jsonRes = util.get(apiURL, requestHeaders);

        String thumbNail = "";
        try {
            Map resMap = om.readValue(jsonRes, Map.class);
            List<String> itemsList = (List<String>) resMap.get("items");
            String itemsStr = om.writeValueAsString(itemsList.get(0));
            Map itemsMap = om.readValue(itemsStr, Map.class);
            thumbNail = (String) itemsMap.get("image");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return thumbNail;
    }

    // 파파고테스트
    public String papagoAPI(String paramText){
        ObjectMapper om = new ObjectMapper();
        HttpUtils util = new HttpUtils();
        String clientId = "1TOE19GYAcgawcD0ESm1";
        String clientSecret = "tmgwvMjtQF";

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        String body;
        String lan = "en";
        try {
            text = URLEncoder.encode(paramText, "UTF-8");
            body = "source=ko&target="+lan+"&text=" + text;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("번역 인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String json = util.post(apiURL, body, requestHeaders);
        String res = "";
        try {
            Map<String, Object> mapRes = om.readValue(json, Map.class);
            Map<String, Object> mapRes1 = (Map<String, Object>) mapRes.get("message");
            Map<String, Object> mapRes2 = (Map<String, Object>) mapRes1.get("result");
            res = (String) mapRes2.get("translatedText");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return res;
    }

    // json 한번더 파싱해야 하는 경우 사용할 메서드
    public Map<String, Object> listSeparation(Map<String, Object> map, String beforecategory, String aftercategory) {
        ObjectMapper om = new ObjectMapper();
        Map<String, Object> resMap = null;
        Map<String, Object> beforeMap = (Map<String, Object>) map.get(beforecategory);
        List<String> beforeList = (List<String>) beforeMap.get(aftercategory);
        try {
            String resStr = om.writeValueAsString(beforeList.get(0));
            resMap = om.readValue(resStr, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resMap;
    }

    // poster 배열로 잘라주고 사진 1개 반환
    public String posterSplit(Map<String, Object> map, String category) {
        String posters = (String) map.get(category);
        String[] posterArr = posters.split("[|]");
        return posterArr[0];
    }

    // 영화 대본정보 크롤링으로 가져온다.
    public Map<String, String> crwalingMovieScript(String movieName) {

        //movieName = Joker
        if(movieName.contains(" ")){
            movieName = movieName.replaceAll(" ","-");
        }
        Map<String, String> movieScript = new LinkedHashMap<String, String>();

        try {
            Document doc = Jsoup.parse(new URL("https://imsdb.com/scripts/"+movieName+".html"), 5000);
            Elements scriptElement = doc.select("#mainbody > table:nth-child(3) > tbody > tr > td:nth-child(3) > table > tbody > tr");

            for (Element element : scriptElement) {

                movieScript.put("name", movieName);
                movieScript.put("script", element.children()+"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieScript;
    }


    // 영화정보들 movie에 넣어준다.
    public void saveMovie(Map movieMap, String thumbnail) {
        // 감독이랑, 줄거리를 한번더 분리해줘야 한다.
        Map<String, Object> directMap = listSeparation(movieMap, "directors", "director");
        Map<String, Object> plotMap = listSeparation(movieMap, "plots", "plot");
        // poster 는 배열로 분리해서 첫번째 것만 가져온다.
        String posterLink = posterSplit(movieMap, "posters");


        Movie movie = new Movie();
        movie.setMvNo((String) movieMap.get("DOCID"));
        movie.setMvTitle((String) movieMap.get("title"));
        movie.setMvTitleorg((String) movieMap.get("titleOrg"));
        movie.setDirector((String) directMap.get("directorNm"));
        movie.setGenre((String) movieMap.get("genre"));
        movie.setReleaseDate(transformDate((String) movieMap.get("repRlsDate")));
        movie.setPlot((String) plotMap.get("plotText"));
        movie.setNation((String) movieMap.get("nation"));
        movie.setRuntime((String) movieMap.get("runtime"));
        movie.setRating((String) movieMap.get("rating"));
        movie.setPoster(posterLink);
        movie.setThumbnail(thumbnail);

        // 영화 제목 기준으로 대본 가져온다
        Map<String, String> scriptMap = crwalingMovieScript((String) movieMap.get("titleOrg"));

        if(scriptMap.get("script") != null){
            movie.setScript(scriptMap.get("script"));
        }

        movieRepository.save(movie);
    }

    public Date transformDate(String strDate) {
        // 개봉일자는 String -> util.date -> sql.date 로 변환을 해주어야 한다.
        // util.date로 변환해주기.
        SimpleDateFormat beforFormat = new SimpleDateFormat("yyyymmdd");
        SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date tempDate = null;
        try {
            tempDate = beforFormat.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tempDate;
    }

    public List<Movie> movieTotalList(){
        return movieRepository.findAll();
    }
}
