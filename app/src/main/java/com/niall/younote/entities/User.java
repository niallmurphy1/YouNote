package com.niall.younote.entities;

import java.util.ArrayList;

public class User {

    private String email;
    private String password;
    private String phoneNO;
    private ArrayList<Note> myNotes;


    public User(){

    }

    public User(String email, String password, String phoneNO, ArrayList<Note> myNotes) {
        this.email = email;
        this.password = password;
        this.phoneNO = phoneNO;
        this.myNotes = myNotes;
    }

    public ArrayList<Note> getMyNotes() {
        return myNotes;
    }

    public void setMyNotes(ArrayList<Note> myNotes) {
        this.myNotes = myNotes;
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
