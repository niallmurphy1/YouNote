package com.niall.younote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.niall.younote.entities.Note;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class NoteViewer extends AppCompatActivity {

    private RecyclerView recycle;
    private RecyclerView.LayoutManager rLayoutManager;
    public ArrayList<Note> myList;
    private RecyclerView.Adapter rAdapter;
    public Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_viewer);

       myList = (ArrayList<Note>) getIntent().getSerializableExtra("noteList");


        recycle = findViewById(R.id.recyclerView);
        rLayoutManager = new LinearLayoutManager(this);


        rAdapter = new ExampleAdapter(myList);

        recycle.setLayoutManager(rLayoutManager);
        recycle.setAdapter(rAdapter);
        rAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myList.clear();
        rAdapter.notifyDataSetChanged();
    }


}

