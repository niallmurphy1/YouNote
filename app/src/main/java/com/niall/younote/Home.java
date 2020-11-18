package com.niall.younote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.niall.younote.entities.User;

public class Home extends AppCompatActivity {

    public DatabaseReference dataRef;
    public FirebaseAuth fAuth = FirebaseAuth.getInstance();
    public FirebaseUser fUser = fAuth.getCurrentUser();
    final String userId = fUser.getUid();

    public TextView welcomeText= findViewById(R.id.welcTextView);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dataRef = FirebaseDatabase.getInstance().getReference("User");

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.w("USER", "Nay user ");

        }
        final String uId = fUser.getUid();
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {


                    User userObj = snapshot.child(uId).getValue(User.class);
                    String email = userObj.getEmail();
                    String welcomeString = "Account: " + email;

                    Toast.makeText(Home.this, welcomeString, Toast.LENGTH_LONG).show();

                    welcomeText.setText(welcomeString);
                    Log.w("USER", "Email: " +  email);

                    //TODO: add new note and view all notes button


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("User", "USER NOT FOUND");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId()){
           case R.id.item1:
               this.finish();
               return  true;

           case R.id.item2:
               //TODO: Edit Note
               //do something
               return  true;

               case R.id.item3:
                   //TODO: search all notes
               return  true;
       }
        return super.onOptionsItemSelected(item);
    }
}
