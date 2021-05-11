package com.kh.simdo.mypage.review;

import com.kh.simdo.movie.Movie;
import com.kh.simdo.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_REVIEW")
public class Review {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String reviewNo;

    @ManyToOne //작성자 한명에 후기는 여러개
    private User user;

    @ManyToOne //영화 하나에 후기는 여러개
    private Movie movie;

    private String reviewScore;

    @Column(columnDefinition = "date default sysdate")
    private Date reviewRegDate;
    private String reviewContent;

    @Column(columnDefinition = "date")
    private Date watchDate;

    @Column(columnDefinition = "number default 0")
    private int reviewLike;

    @Column(columnDefinition = "number default 0")
    private int reviewHate;

    @Column(columnDefinition = "number default 0")
    private boolean isReviewDel;

    public String getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(String reviewNo) {
        this.reviewNo = reviewNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(String reviewScore) {
        this.reviewScore = reviewScore;
    }

    public Date getReviewRegDate() {
        return reviewRegDate;
    }

    public void setReviewRegDate(Date reviewRegDate) {
        this.reviewRegDate = reviewRegDate;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Date getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(Date watchDate) {
        this.watchDate = watchDate;
    }

    public int getReviewLike() {
        return reviewLike;
    }

    public void setReviewLike(int reviewLike) {
        this.reviewLike = reviewLike;
    }

    public int getReviewHate() {
        return reviewHate;
    }

    public void setReviewHate(int reviewHate) {
        this.reviewHate = reviewHate;
    }

    public boolean isReviewDel() {
        return isReviewDel;
    }

    public void setReviewDel(boolean reviewDel) {
        isReviewDel = reviewDel;
    }

}
