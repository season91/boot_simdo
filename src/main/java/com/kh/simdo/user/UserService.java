package com.kh.simdo.user;

import com.kh.simdo.user.form.JoinForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmailAndIsLeave(username, false);

        return new UserAccount(user);
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
