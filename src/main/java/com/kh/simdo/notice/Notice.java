package com.kh.simdo.notice;

import com.kh.simdo.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_NOTICE")
public class Notice {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String nNo;
    
    private String nTitle;
    private String nContent;

    @Column(columnDefinition = "date default sysdate")
    private Date nRegDate;

    @Column(columnDefinition = "number default 0")
    private boolean nIsDel;

    @ManyToOne //작성자 한명이 공지사항 여러개 쓸 수 있음
    private User user;

    public String getnNo() {
        return nNo;
    }

    public void setnNo(String nNo) {
        this.nNo = nNo;
    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnContent() {
        return nContent;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    public Date getnRegDate() {
        return nRegDate;
    }

    public void setnRegDate(Date nRegDate) {
        this.nRegDate = nRegDate;
    }

    public boolean isnIsDel() {
        return nIsDel;
    }

    public void setnIsDel(boolean nIsDel) {
        this.nIsDel = nIsDel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
