package com.niall.younote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.niall.younote.entities.Note;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class NoteViewer extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    public FirebaseAuth fAuth = FirebaseAuth.getInstance();
    public FirebaseUser fUser = fAuth.getCurrentUser();
    final String uId = fUser.getUid();

    private RecyclerView recycle;
    private RecyclerView.LayoutManager rLayoutManager;
    public ArrayList<Note> myList = new ArrayList<>();
    private ExampleAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_viewer);

      buildRecyclerView();


    }


    public void changeIcon(int position, int image){
        myList.get(position).changeImage((image));
        rAdapter.notifyItemChanged(position);
    }

    public void buildRecyclerView() {
        myList = (ArrayList<Note>) getIntent().getSerializableExtra("noteList");

        recycle = findViewById(R.id.recyclerView);
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new ExampleAdapter(myList);
        recycle.setLayoutManager(rLayoutManager);
        recycle.setAdapter(rAdapter);
        rAdapter.notifyItemChanged(myList.size() + 1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    public void updateNotes(){
        rAdapter.setOnItemClickListener(position -> changeIcon(position, R.drawable.ic_tick));

        rAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeIcon(position, R.drawable.ic_tick);
                myList.get(position).setImage(R.drawable.ic_tick);
                rAdapter.notifyDataSetChanged();
                rAdapter.notifyItemChanged(position);


                myRef = FirebaseDatabase.getInstance().getReference("User").child(uId).child("user-notes");

                myRef.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        for (DataSnapshot noteSnapshot: snapshot.getChildren()){
                            Note noteObj = noteSnapshot.getValue(Note.class);
                            assert noteObj != null;
                            if(myList.get(position).getBody().equals(noteObj.getBody().toString())){
                                noteObj.setImage(R.drawable.ic_tick);
                                myList.get(position).setImage(R.drawable.ic_tick);
                                rAdapter.notifyItemChanged(position);
                                rAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });
    }




}

