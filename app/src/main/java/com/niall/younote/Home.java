package com.niall.younote;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

    private TextView mTextView;
    public DatabaseReference dataRef;
    public FirebaseAuth fAuth = FirebaseAuth.getInstance();
    public FirebaseUser fUser = fAuth.getCurrentUser();
    final String userId = fUser.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextView = (TextView) findViewById(R.id.text);

        dataRef = FirebaseDatabase.getInstance().getReference("User");

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.w("USER", "Nay user ");

        }

        String key = dataRef.push().getKey();
        final String uId = fUser.getUid();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {


                    User userObj = snapshot.child(uId).getValue(User.class);
                    String email = userObj.getEmail();

                    mTextView.setText(email);

                    Log.w("USER", "Email " +  email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("User", "USER NOT FOUND");
            }
        });
    }





    }
