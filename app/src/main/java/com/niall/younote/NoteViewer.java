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
import java.util.ListIterator;

public class NoteViewer extends AppCompatActivity {

    private RecyclerView recycle;
    private RecyclerView.LayoutManager rLayoutManager;
    public ArrayList<Note> myList = new ArrayList<>();
    private RecyclerView.Adapter rAdapter;
    public Intent intent;

    public RecyclerView.Adapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_viewer);

      buildRecyclerView();


    }

    public void createList(){
        ArrayList<Note> noteArrayList = new ArrayList<>();


    }

    public void buildRecyclerView() {
        myList = (ArrayList<Note>) getIntent().getSerializableExtra("noteList");
        for(int i =0; i<myList.size();i++){
            System.out.println(myList.get(i).toString());
        }

        //TODO: FIX THIS STUPID FUCKING ARRAYLIST
        ListIterator<Note> listItr = myList.listIterator();

        recycle = findViewById(R.id.recyclerView);
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new ExampleAdapter(myList);
        recycle.setLayoutManager(rLayoutManager);
        recycle.setAdapter(rAdapter);
        rAdapter.notifyItemChanged(myList.size() + 1);
    }


}

