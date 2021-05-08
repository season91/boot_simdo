package com.kh.simdo.user.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JoinForm {

    private String userEmail;
    private String userPw;
    private String confirmPw;
    private String userTel;
    private String certNum;

    @Override
    public String toString() {
        return "JoinForm{" +
                "userEmail='" + userEmail + '\'' +
                ", userPw='" + userPw + '\'' +
                ", confirmPw='" + confirmPw + '\'' +
                ", userTel='" + userTel + '\'' +
                ", certNum='" + certNum + '\'' +
                '}';
    }
}
