package com.niall.younote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "EmailPassword";

    public DatabaseReference db;
    private FirebaseAuth mAuth;


    private EditText emailEdtTxt;
    private EditText pWordEdtTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailEdtTxt = findViewById(R.id.editTextTextEmailAddress);
        pWordEdtTxt = findViewById(R.id.editTextTextPassword);
    }
    @NotNull
    private String getEmailInput() {
        return emailEdtTxt.getText().toString().trim();
    }
    @NotNull
    private String getPasswordInput() {
        return pWordEdtTxt.getText().toString();
    }



    public void onLoginBtnClick(View view){

        if(getEmailInput().equals("") || getPasswordInput().equals("")){

            Toast.makeText(this, "Please enter your login details", Toast.LENGTH_SHORT).show();
        }

        Intent home = new Intent(this, Home.class);

        mAuth.signInWithEmailAndPassword(getEmailInput(), getPasswordInput())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = user.getUid();
                            Toast.makeText(LoginActivity.this, "Login success.",
                                    Toast.LENGTH_SHORT).show();

                            db = FirebaseDatabase.getInstance().getReference().child("User");
                            String key = db.push().getKey();
                            Log.d(TAG, "signInWithEmail:success: " + key);


//                            Toast.makeText(LoginActivity.this, userId,
//                                    Toast.LENGTH_LONG).show();


                            startActivity(home);


                            // startActivity(dash);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // ...
                        }

                        // ...
                    }
                });


    }
    public void onBackClick(View view){
                this.finish();
    }
}