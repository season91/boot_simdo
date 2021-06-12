package com.kh.simdo.movie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("db insert 확인")
    public void movieAPITest() throws Exception {
        mockMvc.perform(get("/movie/db")
            .param("title","라라랜드")
            ).andDo(print());
    }

    @Test
    @DisplayName("번역 확인")
    public void papagoAPITest() throws Exception{
        Map<String, String> data = new HashMap<>();
        data.put("text","조커");
        data.put("lan","en");
        mockMvc.perform(post("/movie/translation")
                .param("text", "조커")
                .param("lan","en")
        ).andDo(print());
    }

    @Test
    @DisplayName("영화목록 확인")
    public void movieList() throws Exception {
        mockMvc.perform(get("/movie/movielist")).andDo(print());
    }

    @Test
    @DisplayName("영화 대본 확인")
    public void movieScript() throws Exception {
        mockMvc.perform(
                get("/movie/script")
                .param("mvNo","F48336")
        ).andDo(print());
    }
}
