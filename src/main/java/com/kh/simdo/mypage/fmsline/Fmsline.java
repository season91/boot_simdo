package com.kh.simdo.mypage.fmsline;

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
@Table(name = "TB_FMSLINE")
public class Fmsline {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String fmslineNo;

    @ManyToOne //작성자 한명에 명대사는 여러개
    private User user;

    @ManyToOne //영화 하나에 명대사는 여러개
    private Movie movie;

    private String fmlContent;

    @Column(columnDefinition = "date default sysdate")
    private Date fmlRegDate;

    @Column(columnDefinition = "number default 0")
    private int fmlLike;

    @Column(columnDefinition = "number default 0")
    private int fmlHate;

    @Column(columnDefinition = "number default 0")
    private boolean isFmlDel;

    public String getFmslineNo() {
        return fmslineNo;
    }

    public void setFmslineNo(String fmslineNo) {
        this.fmslineNo = fmslineNo;
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

    public String getFmlContent() {
        return fmlContent;
    }

    public void setFmlContent(String fmlContent) {
        this.fmlContent = fmlContent;
    }

    public Date getFmlRegDate() {
        return fmlRegDate;
    }

    public void setFmlRegDate(Date fmlRegDate) {
        this.fmlRegDate = fmlRegDate;
    }

    public int getFmlLike() {
        return fmlLike;
    }

    public void setFmlLike(int fmlLike) {
        this.fmlLike = fmlLike;
    }

    public int getFmlHate() {
        return fmlHate;
    }

    public void setFmlHate(int fmlHate) {
        this.fmlHate = fmlHate;
    }

    public boolean isFmlDel() {
        return isFmlDel;
    }

    public void setFmlDel(boolean fmlDel) {
        isFmlDel = fmlDel;
    }

}
