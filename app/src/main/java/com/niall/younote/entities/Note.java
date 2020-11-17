package com.niall.younote.entities;

import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;

public class Note {

    private String tag;
    private String body;

    public Note(){


    }

    public Note(String tag, String body){
        this.tag = tag;
        this.body = body;

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
