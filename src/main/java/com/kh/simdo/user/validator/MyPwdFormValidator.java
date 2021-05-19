package com.kh.simdo.user.validator;

import com.kh.simdo.user.UserAccount;
import com.kh.simdo.user.UserRepository;
import com.kh.simdo.user.form.MyPwdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class MyPwdFormValidator implements Validator {

    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public MyPwdFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MyPwdForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pattern pwPattern = Pattern.compile("^(?!.*[ㄱ-힣])(?=.*\\W)(?=.*\\d)(?=.*[a-zA-Z])(?=.{8,})");
        MyPwdForm pwd = (MyPwdForm) target;

        if (!passwordEncoder.matches(pwd.getUserPw(), userRepository.findByUserEmailAndIsLeave(pwd.getUserEmail(), false).getUserPw())) {
            errors.rejectValue("userPw", "error.userPw", "현재 비밀번호를 잘못 입력하셨습니다.");
        }

        if (!pwPattern.matcher(pwd.getNewPw()).find()) {
            errors.rejectValue("newPw", "error.newPw", "비밀번호는 숫자,영문자,특수문자 조합의 8글자 이상이어야 합니다.");
        }

        if (!pwd.getNewPw().equals(pwd.getConfirmPw())) {
            errors.rejectValue("confirmPw", "error.confirmPw", "새 비밀번호가 일치하지 않습니다.");
        }
    }

}
