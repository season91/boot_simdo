package com.kh.simdo.user.validator;

import com.kh.simdo.user.UserRepository;
import com.kh.simdo.user.form.JoinForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class JoinFormValidator implements Validator {

    private final UserRepository userRepository;

    public JoinFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pattern pwPattern = Pattern.compile("^(?!.*[ㄱ-힣])(?=.*\\W)(?=.*\\d)(?=.*[a-zA-Z])(?=.{8,})");
        Pattern telPattern = Pattern.compile("^\\d{11}$");
        JoinForm newUser = (JoinForm) target;

        if (userRepository.existsByUserEmail(newUser.getUserEmail())) {
            errors.rejectValue("userEmail", "error.userEmail", "이미 가입된 이메일입니다.");
        }

        if (!pwPattern.matcher(newUser.getUserPw()).find()) {
            errors.rejectValue("userPw", "error.userPw", "비밀번호는 숫자,영문자,특수문자 조합의 8글자 이상이어야 합니다.");
        }

        if (!telPattern.matcher(newUser.getUserTel()).find()) {
            errors.rejectValue("userTel", "error.userTel", "전화번호는 '-'를 제외한 11자리 숫자를 입력해주세요.");
        }
    }

}
