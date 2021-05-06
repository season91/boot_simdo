package com.kh.simdo.user;

public class UserAccount extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private User user;

    public UserAccount(User user) {
        super(user.getUserEmail(), user.getUserPw(), user.getAuthority());
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return user.getUserEmail();
    }

    public String getRole() {
        return user.getRole();
    }

    public String getTel() {
        return user.getUserTel();
    }

}
