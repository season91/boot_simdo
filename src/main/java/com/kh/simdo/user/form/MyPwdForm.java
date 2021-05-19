package com.kh.simdo.user.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MyPwdForm {

    private String userEmail;
    private String userPw;
    private String newPw;
    private String confirmPw;

    @Override
    public String toString() {
        return "MyPwdForm{" +
                "userEmail='" + userEmail + '\'' +
                ", userPw='" + userPw + '\'' +
                ", newPw='" + newPw + '\'' +
                ", confirmPw='" + confirmPw + '\'' +
                '}';
    }

}
