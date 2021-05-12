package com.kh.simdo.movie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    public void movieAPITest() throws Exception {
        mockMvc.perform(get("/movie/db")
            .param("title","라라랜드")
            ).andDo(print());
    }

    @Test
    public void papagoAPITest() throws Exception{
        Map<String, String> data = new HashMap<>();
        data.put("text","조커");
        data.put("lan","en");
        mockMvc.perform(post("/movie/translation")
                .param("text", "조커")
                .param("lan","en")
        ).andDo(print());
    }
}
