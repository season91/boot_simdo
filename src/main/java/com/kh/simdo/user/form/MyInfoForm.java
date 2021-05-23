package com.kh.simdo.user.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MyInfoForm {

    private String userProfile;
    private String userEmail;
    private String userNm;
    private String userGender;
    private String userBirth;
    private String userTel;
    private String certNum;

    @Override
    public String toString() {
        return "MyInfoForm{" +
                "userProfile='" + userProfile + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userNm='" + userNm + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userBirth='" + userBirth + '\'' +
                ", userTel='" + userTel + '\'' +
                ", certNum='" + certNum + '\'' +
                '}';
    }

}
