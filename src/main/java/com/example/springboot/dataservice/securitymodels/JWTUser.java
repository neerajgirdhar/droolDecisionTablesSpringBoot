package com.example.springboot.dataservice.securitymodels;




public class JWTUser {

    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;

    public String getUserName() {
        return userName;
    }


    public String getRole() {
        return role;
    }

    private String role;
}
