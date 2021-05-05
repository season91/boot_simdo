package com.kh.simdo.users;

import com.kh.simdo.users.form.JoinForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void saveUser(JoinForm joinForm) {
        Users newUser = new Users();
        newUser.setUserEmail(joinForm.getUserEmail());
        newUser.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));
        newUser.setUserTel(joinForm.getUserTel());
        usersRepository.save(newUser);
    }
}
