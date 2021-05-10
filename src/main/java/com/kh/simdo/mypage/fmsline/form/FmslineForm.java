package com.kh.simdo.mypage.fmsline.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FmslineForm {

    private String mvNo;
    private String fmlContent;

    @Override
    public String toString() {
        return "FmslineForm{" +
                "mvNo='" + mvNo + '\'' +
                ", fmlContent='" + fmlContent + '\'' +
                '}';
    }

}
