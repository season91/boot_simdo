package com.kh.simdo.user.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
public class JoinForm {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    //@Pattern(regexp = "^(?!.*[ㄱ-힣])(?=.*\\W)(?=.*\\d)(?=.*[a-zA-Z])(?=.{8,})", message = "비밀번호는 숫자,영문자,특수문자 조합의 8글자 이상이어야 합니다.")
    private String userPw;

    @NotBlank
    private String confirmPw;

    @NotBlank
    //@Pattern(regexp = "^\\d{11}$", message = "전화번호는 '-'를 제외한 11자리 숫자를 입력해주세요.")
    private String userTel;

    @NotBlank
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
