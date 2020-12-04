package com.niall.younote.entities;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Note implements Serializable {



    private String tag;
    private String body;
    private boolean isChecked = false;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    private String noteId;



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("tag", tag);
        result.put("body", body);

        return result;
    }

    public Note() {
    }

    public Note( String tag, String body, boolean isChecked) {

        this.tag = tag;
        this.body = body;
        this.isChecked = isChecked;

    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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
