package com.janus.rodeo.Models;

public class User {
    private String FullName;
    private String UserName;
    private String Mail;
    private String password;
    private int UserCode;
    private String UserPermissions;
    private String token;
    private int expirationTimeToken;

    public String getFullName() {
        return FullName;
    }
    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getMail() {
        return Mail;
    }
    public void setMail(String Mail) {
        this.Mail = Mail;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPermissions() {
        return UserPermissions;
    }
    public void setUserPermissions(String UserPermissions) {
        this.UserPermissions = UserPermissions;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public int getUserCode() {
        return UserCode;
    }
    public void setUserCode(int UserCode) {
        this.UserCode = UserCode;
    }

    public int getExpirationTimeToken() {
        return expirationTimeToken;
    }
    public void setExpirationTimeToken(int expirationTimeToken) {
        this.expirationTimeToken = expirationTimeToken;
    }

}