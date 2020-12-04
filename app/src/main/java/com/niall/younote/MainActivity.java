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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.niall.younote.entities.User;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private static final String REGTAG = "Registered";

    private FirebaseAuth mAuth;
    private EditText loginEmailEdit;
    private EditText loginPwordEdit;
    private EditText loginPhoneNo;
    private EditText loginName;



    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loginEmailEdit = findViewById(R.id.emailEditText);
        loginName = findViewById(R.id.editTextName);
        loginPwordEdit = findViewById(R.id.editTextPword);
        loginPhoneNo = findViewById(R.id.editTextPhone);





    }


    private boolean validate() {
        String emailInput = loginEmailEdit.getText().toString().trim();
        if (loginEmailEdit.toString().equals("") || loginName.toString().equals("")
                || loginPwordEdit.toString().equals("") || loginPhoneNo.toString().equals("")) {
            loginEmailEdit.setError("Field can't be empty");
            Toast.makeText(this, "Fields can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            loginEmailEdit.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = loginEmailEdit.getText().toString().trim();
        if (emailInput.equals("")) {
            loginEmailEdit.setError("Field can't be empty");
            return false;
        } else {
            loginEmailEdit.setError(null);
            return true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    @NotNull
    private String getNameInput() {
        return loginName.getText().toString().trim();
    }

    @NotNull
    private String getPasswordInput() {
        return loginPwordEdit.getText().toString();
    }

    @NotNull
    private String getEmailInput() {
        return loginEmailEdit.getText().toString().trim();
    }

    public void onRegisterClick(View view) {

        System.out.println(getEmailInput());
        validateEmail();

        if (getPasswordInput().length() < 6) {
            Toast.makeText(this, "Your password must be at least 6 characters in length!", Toast.LENGTH_SHORT).show();
        } else {

            Intent home = new Intent(this, Home.class);
            mAuth.createUserWithEmailAndPassword(getEmailInput(), getPasswordInput()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");

                        FirebaseUser user = mAuth.getCurrentUser();
                        String userId = user.getUid();

                        User newUser = new User(getEmailInput(), getPasswordInput(), getNameInput(), loginPhoneNo.getText().toString(), null);

                        myRef.child("User").child(userId).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.w(REGTAG, "Great success!");
                                startActivity(home);
                            }
                        });
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }

    public void onLoginClick(View view){

        Intent loginIntent = new Intent(this, LoginActivity.class);

        startActivity(loginIntent);


    }
}