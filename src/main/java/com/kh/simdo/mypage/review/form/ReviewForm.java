package com.kh.simdo.mypage.review.form;

import com.kh.simdo.movie.Movie;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReviewForm {

    private String mvNo;
    private String watchDate;
    private String reviewScore;
    private String reviewContent;

    @Override
    public String toString() {
        return "ReviewForm{" +
                "mvNo='" + mvNo + '\'' +
                ", watchDate='" + watchDate + '\'' +
                ", reviewScore='" + reviewScore + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                '}';
    }

}
