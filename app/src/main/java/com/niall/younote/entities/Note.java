package com.niall.younote.entities;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import org.w3c.dom.ls.LSOutput;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class Note implements Serializable {

    private int image;
    private String tag;
    private String body;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tag", tag);
        result.put("body", body);

        return result;
    }

    public Note() {
    }

    public Note(int image, String tag, String body) {
        this.image = image;
        this.tag = tag;
        this.body = body;

    }

    public void changeImage(int newImage){
        image = newImage;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    @NonNull
    @Override
    public String toString() {
        return "Tag: " + getTag() + "\nNote: " + getBody();
    }

    @Override
    public boolean equals(Object n) {
        return this == n;
    }

}
