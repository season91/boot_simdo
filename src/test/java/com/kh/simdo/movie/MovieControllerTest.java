package com.kh.simdo.movie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RequiredArgsConstructor
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void movieAPITest() throws Exception {
        mockMvc.perform(get("/movie/db")
            .param("title","조커")
            ).andDo(print());
    }
}
