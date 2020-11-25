package com.niall.younote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.niall.younote.entities.Note;
import com.niall.younote.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity implements NewNoteDialog.NewNoteDialogListener {

    public DatabaseReference dataRef;
    public FirebaseAuth fAuth = FirebaseAuth.getInstance();
    public FirebaseUser fUser = fAuth.getCurrentUser();
    public Intent noteViewer;

    final String uId = fUser.getUid();

    public Intent settingsIntent;

    public Intent logoutIntent;

    public ArrayList<Note> userNotes;

    public static final String TAG = "tag";
    public static final String BODY = "body";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        noteViewer = new Intent(this, NoteViewer.class);
        settingsIntent  = new Intent(this, Settings.class);
        logoutIntent = new Intent(this, MainActivity.class);

        dataRef = FirebaseDatabase.getInstance().getReference("User");

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.w("USER", "Nay user ");

        }
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {

                    User userObj = snapshot.child(uId).getValue(User.class);

                    String email = userObj.getEmail();
                    Log.w("USER", "Email: " +  email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("User", "USER NOT FOUND");
            }
        });

        addUserNotes();

    }


    //Update user note list (populate recyclerView)
    public void addUserNotes(){
        DatabaseReference fireDb = FirebaseDatabase.getInstance().getReference("User").child(uId).child("user-notes");

        userNotes = new ArrayList<>();

        fireDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userNotes.clear();

                for (DataSnapshot noteSnapshot: snapshot.getChildren()){
                    Note noteObj = noteSnapshot.getValue(Note.class);
                    if(noteObj == null){break;}
                    else {
                        //System.out.println(noteObj.getTag() + " " + noteObj.getBody());
                        Note aNote = new Note(noteObj.getImage(), noteObj.getTag(), noteObj.getBody());
                        //System.out.println(aNote.toString());
                        userNotes.add(aNote);
                    }


                }
                noteViewer.putExtra("noteList", userNotes);
                for(int i =0; i<userNotes.size();i++){
                    System.out.println(userNotes.get(i).toString());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
    return true;
    }


    public void onNewNoteClick(View view){
                openDialog();

    }

    public void openDialog(){
            NewNoteDialog newNoteDialog = new NewNoteDialog();
            newNoteDialog.show(getSupportFragmentManager(), "New note dialog");
    }


    public void onViewNotesClick(View view){

        startActivity(noteViewer);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId()){
           case R.id.item1:
               this.finish();
               return  true;

           case R.id.item2:
                startActivity(settingsIntent);
               return  true;

           case R.id.item3:
                startActivity(logoutIntent);
               return  true;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyTexts(String tag, String body) {
            //TODO: add Note object to Firebase
        Log.w(tag, body);
        System.out.println(tag + body);

        Note note = new Note(R.drawable.ic_right_arrow,tag, body);

        dataRef = FirebaseDatabase.getInstance().getReference();
        String key = dataRef.child("Note").push().getKey();

        dataRef.child("Note").child(key).setValue(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.w("NOTE", "Key: " + key + ", " + note.toString()+ " Successfully added");
            }
        });

        //adding note object to the note arraylist of user

        FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();


        Map<String, Object> noteValues = note.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        dataRef = FirebaseDatabase.getInstance().getReference("User");

        childUpdates.put(userId + "/user-notes/" + key, noteValues);

        dataRef.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println(childUpdates.toString());
                Log.w("CHILD UPDATES: ", childUpdates.toString());


            }
        });

        noteViewer.putExtra(TAG, tag);
        noteViewer.putExtra(BODY, body);


    }
}
