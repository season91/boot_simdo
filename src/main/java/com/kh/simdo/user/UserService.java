package com.kh.simdo.user;

import com.kh.simdo.user.form.JoinForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(JoinForm joinForm) {
        User newUser = new User();
        newUser.setUserEmail(joinForm.getUserEmail());
        newUser.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));
        newUser.setUserTel(joinForm.getUserTel());
        userRepository.save(newUser);
    }
}
