package com.kh.simdo.user;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue
    private long userNo;
    private String userEmail;
    private String userPw;
    private String userTel;

    @Column(columnDefinition = "varchar2(70 char) default 'USER'")
    private String userNm;
    private String userGender;

    @Temporal(TemporalType.DATE)
    private Date userBirth;

    @Column(columnDefinition = "number default 0")
    private boolean isLeave;
    private String userProfile;

    @Column(columnDefinition = "varchar2(50 char) default 'ROLE_USER'")
    protected String role;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default sysdate")
    private Date userRegDate;

    public long getUserNo() {
        return userNo;
    }

    public void setUserNo(long userNo) {
        this.userNo = userNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }

    public boolean isLeave() {
        return isLeave;
    }

    public void setLeave(boolean leave) {
        isLeave = leave;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getUserRegDate() {
        return userRegDate;
    }

    public void setUserRegDate(Date userRegDate) {
        this.userRegDate = userRegDate;
    }

    public List<SimpleGrantedAuthority> getAuthority() {
        List<SimpleGrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(role));
        return auth;
    }

    @Override
    public String toString() {
        return "User{" +
                "userNo='" + userNo + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPw='" + userPw + '\'' +
                ", userTel='" + userTel + '\'' +
                ", userNm='" + userNm + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userBirth=" + userBirth +
                ", isLeave=" + isLeave +
                ", userProfile='" + userProfile + '\'' +
                ", role='" + role + '\'' +
                ", userRegDate=" + userRegDate +
                '}';
    }
}
