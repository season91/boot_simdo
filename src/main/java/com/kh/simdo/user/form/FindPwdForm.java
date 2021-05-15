package com.kh.simdo.user.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FindPwdForm {

    private String userEmail;
    private String userTel;

}
