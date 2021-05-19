package com.kh.simdo.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName(value = "insert new user")
    public void saveNewUser() {
        Date today = new Date();
        User user = new User();
        user.setUserEmail("test99");
        user.setUserPw(passwordEncoder.encode("123"));
        user.setUserTel("01000000000");
        user.setUserRegDate(today);
        user.setRole("ROLE_USER");
        user.setUserNm("USER");

        /*Date today = new Date();
        User user = new User();
        user.setUserEmail("admin");
        user.setUserPw(passwordEncoder.encode("123"));
        user.setUserTel("01000000000");
        user.setUserRegDate(today);
        user.setRole("ROLE_ADMIN");
        user.setUserNm("AY");*/

        userRepository.save(user);
    }

}
