package com.kh.simdo.wish;


import com.kh.simdo.user.User;
import com.kh.simdo.user.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class WishControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName(value = "insert wish test")
    public void addWish() throws Exception{
        String mvNo = "F48336";
        mockMvc.perform(get("/wish/add")
                .param("mvNo",mvNo)
                .param("userNo","2")
            ).andDo(print());

    }

}
