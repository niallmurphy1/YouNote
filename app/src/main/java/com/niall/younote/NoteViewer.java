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
      //updateNotes();
       // deleteItem();


    }


//    public void changeIcon(int position, int image){
//        myList.get(position).changeImage((image));
//        rAdapter.notifyItemChanged(position);
//    }

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

    public void deleteItem(){



        rAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                myList.remove(myList.get(position));

                myRef = FirebaseDatabase.getInstance().getReference("User").child(uId).child("user-notes")
                        .child(myList.get(position).getNoteId());

                System.out.println(myList.get(position).getNoteId().toString());

                myRef.removeValue();

                rAdapter.notifyDataSetChanged();


            }
        });
    }



//    public void updateNotes(){
//
//
//        rAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                rAdapter.notifyDataSetChanged();
//                rAdapter.notifyItemChanged(position);
//                boolean bool = true;
//
//                myList.get(position).setChecked(bool);
//               String body =  myList.get(position).getTag();
//                String tag = myList.get(position).getBody();
//
//
//
//                myRef = FirebaseDatabase.getInstance().getReference("User").child(uId).child("user-notes")
//                        .child(myList.get(position).getNoteId());
//
//                myRef.child("checked").setValue(bool);
//                myRef.child("body").setValue(body);
//                myRef.child("tag").setValue(tag);
//
//                rAdapter.notifyDataSetChanged();
//
//                myList.clear();
//
//
//
//
//
//            }
//        });
//    }




}

