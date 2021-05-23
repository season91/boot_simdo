package com.kh.simdo.user;

import com.kh.simdo.common.code.ConfigCode;
import com.kh.simdo.common.mail.EmailSender;
import com.kh.simdo.user.form.FindPwdForm;
import com.kh.simdo.user.form.JoinForm;
import com.kh.simdo.user.form.MyInfoForm;
import com.kh.simdo.user.form.MyPwdForm;
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
import java.sql.Date;
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
    
    //회원가입 : DB에 새 회원 저장
    @Transactional
    public void saveUser(JoinForm joinForm) {
        User newUser = new User();
        newUser.setUserEmail(joinForm.getUserEmail());
        newUser.setUserPw(passwordEncoder.encode(joinForm.getUserPw()));
        newUser.setUserTel(joinForm.getUserTel());
        userRepository.save(newUser);
    }
    
    //회원가입 : 이메일 인증
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
    
    //비밀번호 찾기 : 임시 비밀번호로 회원 비밀번호 변경하여 DB에 저장
    public String setTmpPwd(FindPwdForm findPwdForm) {
        User user = userRepository.findByUserEmailAndIsLeave(findPwdForm.getUserEmail(), false);

        String tmpPwd = UUID.randomUUID().toString().substring(0, 13);
        user.setUserPw(passwordEncoder.encode(tmpPwd));

        userRepository.save(user);

        return tmpPwd;
    }
    
    //비밀번호 찾기 : 임시 비밀번호 회원 이메일로 전송
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
    
    //비밀번호 찾기 : 가입된 회원인지 확인
    public User checkToFindEmail(String userEmail, String userTel) {
        return userRepository.findByUserEmailAndUserTelAndIsLeave(userEmail, userTel, true);
    }
    
    //비밀번호 변경
    public void setNewPwd(MyPwdForm myPwdForm) {
        User user = userRepository.findByUserEmailAndIsLeave(myPwdForm.getUserEmail(), false);
        user.setUserPw(passwordEncoder.encode(myPwdForm.getNewPw()));

        userRepository.save(user);
    }

    //회원정보 변경
    public void updateInfo(MyInfoForm myInfoForm) {
        User user = userRepository.findByUserEmailAndIsLeave(myInfoForm.getUserEmail(), false);

        if (myInfoForm.getUserProfile() != "") {
            user.setUserProfile(myInfoForm.getUserProfile());
        }
        if (myInfoForm.getUserNm() != "") {
            user.setUserNm(myInfoForm.getUserNm());
        }
        if (myInfoForm.getUserGender() != null) {
            user.setUserTel(myInfoForm.getUserTel());
        }
        if (myInfoForm.getUserBirth() != "") {
            user.setUserBirth(Date.valueOf(myInfoForm.getUserBirth()));
        }
        if (myInfoForm.getUserTel() != "") {
            user.setUserTel(myInfoForm.getUserTel());
        }

        userRepository.save(user);
    }

}
