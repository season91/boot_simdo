package com.kh.simdo.user.validator;

import com.kh.simdo.user.UserRepository;
import com.kh.simdo.user.form.FindPwdForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class FindPwdFormValidator implements Validator {

    private final UserRepository userRepository;

    public FindPwdFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FindPwdForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pattern telPattern = Pattern.compile("^\\d{11}$");
        FindPwdForm user = (FindPwdForm) target;

        if (!userRepository.existsByUserEmail(user.getUserEmail())) {
            errors.rejectValue("userEmail", "error.userEmail", "가입되지 않은 이메일입니다.");
        }

        if (!telPattern.matcher(user.getUserTel()).find()) {
            errors.rejectValue("userTel", "error.userTel", "전화번호는 '-'를 제외한 11자리 숫자를 입력해주세요.");
        }
    }

}
