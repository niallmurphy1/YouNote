package com.niall.younote.entities;

public class User {

    private String email;
    private String password;
    private String phoneNO;

    public User(){

    }

    public User(String email, String password, String phoneNO) {
        this.email = email;
        this.password = password;
        this.phoneNO = phoneNO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }
}
