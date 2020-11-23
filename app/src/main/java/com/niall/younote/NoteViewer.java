package com.niall.younote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.niall.younote.entities.Note;

import java.util.ArrayList;

public class NoteViewer extends AppCompatActivity {

    private RecyclerView recycle;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;


    Note egNote1 = new Note(R.drawable.ic_tick,"TAG1", "This is the first note");
    Note egNote2 = new Note(R.drawable.ic_tick,"TAG2", "This is the second note");
    Note egNote3 = new Note(R.drawable.ic_tick,"TAG3", "This is the third note");
    Note egNote4 = new Note(R.drawable.ic_tick,"TAG4", "This is the fourth note");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_viewer);

        ArrayList<Note> noteList = new ArrayList<>();
        noteList.add(egNote1);
        noteList.add(egNote2);
        noteList.add(egNote3);
        noteList.add(egNote4);

        recycle = findViewById(R.id.recyclerView);
        //recycle.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new ExampleAdapter(noteList);

        recycle.setLayoutManager(rLayoutManager);
        recycle.setAdapter(rAdapter);



    }
}