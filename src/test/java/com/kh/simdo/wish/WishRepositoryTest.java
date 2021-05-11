package com.kh.simdo.wish;

import com.kh.simdo.user.User;
import com.kh.simdo.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootTest
public class WishRepositoryTest {

    @Autowired
    WishRepository wishRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName(value = "insert wish")
    public void insertWish() {

    }

}
