package com.kh.simdo.users;

import org.springframework.security.core.userdetails.User;

public class UsersAccount extends User {

    private static final long serialVersionUID = 1L;

    private Users user;

    public UsersAccount(Users user) {
        super(user.getUserEmail(), user.getUserPw(), user.getAuthority());
        this.user = user;
    }

    public Users getUser() {
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
