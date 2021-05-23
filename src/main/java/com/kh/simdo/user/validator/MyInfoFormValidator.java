package com.kh.simdo.user.validator;

import com.kh.simdo.user.UserRepository;
import com.kh.simdo.user.form.MyInfoForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class MyInfoFormValidator implements Validator {

    private final UserRepository userRepository;

    public MyInfoFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MyInfoForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Pattern telPattern = Pattern.compile("^\\d{11}$");
        MyInfoForm user = (MyInfoForm) target;

        if (!telPattern.matcher(user.getUserTel()).find()) {
            errors.rejectValue("userTel", "error.userTel", "전화번호는 '-'를 제외한 11자리 숫자를 입력해주세요.");
        }
    }

}
