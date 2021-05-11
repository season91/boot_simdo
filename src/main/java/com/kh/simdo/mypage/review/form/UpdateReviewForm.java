package com.kh.simdo.mypage.review.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateReviewForm {

    private String reviewNo;
    private String watchDate;
    private String reviewScore;
    private String reviewContent;

}
