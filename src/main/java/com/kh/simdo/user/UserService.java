package com.kh.simdo.user;

import com.kh.simdo.common.code.ConfigCode;
import com.kh.simdo.common.mail.EmailSender;
import com.kh.simdo.user.form.FindPwdForm;
import com.kh.simdo.user.form.JoinForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@Service
public class
UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private EmailSender mail;
    @Autowired
    private RestTemplate http;
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

    public void authenticateEmail(@Valid JoinForm joinForm, String authPath) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("mail-template", "temp_join");
        body.add("userEmail", joinForm.getUserEmail());
        body.add("authPath", authPath);

        RequestEntity<MultiValueMap<String, String>> request =
                RequestEntity.post(ConfigCode.DOMAIN+"/mail")
                .header("content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(body);

        ResponseEntity<String> response = http.exchange(request, String.class);
        mail.send(joinForm.getUserEmail(), "[SIMDO:wm] 회원 가입을 완료해주세요.", response.getBody());
    }

    public boolean findUserByUserEmail(String userEmail) {
        return userRepository.existsByUserEmail(userEmail);
    }

    public String setTmpPwd(FindPwdForm findPwdForm) {
        User user = userRepository.findByUserEmailAndIsLeave(findPwdForm.getUserEmail(), false);

        String tmpPwd = UUID.randomUUID().toString().substring(0, 13);
        user.setUserPw(passwordEncoder.encode(tmpPwd));

        userRepository.save(user);

        return tmpPwd;
    }

    public void findPwdEmail(@Valid FindPwdForm findPwdForm, String tmpPwd) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("mail-template", "temp_findpwd");
        body.add("userEmail", findPwdForm.getUserEmail());
        body.add("tmpPwd", tmpPwd);

        RequestEntity<MultiValueMap<String, String>> request =
                RequestEntity.post(ConfigCode.DOMAIN+"/mail")
                        .header("content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .body(body);

        ResponseEntity<String> response = http.exchange(request, String.class);
        mail.send(findPwdForm.getUserEmail(), "[SIMDO:wm] 임시 비밀번호가 발급되었습니다.", response.getBody());
    }

    public User checkToFindEmail(String userEmail, String userTel) {
        return userRepository.findByUserEmailAndUserTel(userEmail, userTel);
    }
}
