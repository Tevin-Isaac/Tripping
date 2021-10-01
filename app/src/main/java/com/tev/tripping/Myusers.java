package com.tev.tripping;


public class Myusers {
    public String UserName;
    public String UserEmail;
    public String UserId;
    public Myusers(){}

    public Myusers(String userName, String userEmail, String userId) {
        UserName = userName;
        UserEmail = userEmail;
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}

