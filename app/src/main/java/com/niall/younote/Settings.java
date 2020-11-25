package com.niall.younote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.niall.younote.entities.User;

public class Settings extends AppCompatActivity {

    private EditText nameEdit;
    private EditText phoneNumEdit;
    private TextView nameView;
    private TextView phoneView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    public FirebaseAuth fAuth = FirebaseAuth.getInstance();
    public FirebaseUser fUser = fAuth.getCurrentUser();


    final String uId = fUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameEdit = findViewById(R.id.settingsEditTextName);
        phoneNumEdit = findViewById(R.id.settingsEditTextPhone);
        nameView = findViewById(R.id.settingsNameTextView);
        phoneView = findViewById(R.id.settingsNumTextView);

        myRef = FirebaseDatabase.getInstance().getReference("User");


        if(fUser == null){
            Log.w("USER", "No user found");

        }
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userObj = snapshot.child(uId).getValue(User.class);

                        assert userObj != null;
                        nameView.setText(userObj.getName());
                        phoneView.setText(userObj.getPhoneNO());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("User", "USER NOT FOUND");
                }
            });
    }

    public void onChangeDetailsClick(View v){

        if(nameEdit.getText().toString().equals("") & phoneNumEdit.getText().toString().equals("")){

            Toast.makeText(this, "Please enter values in fields", Toast.LENGTH_SHORT).show();

        }
        else if (!nameEdit.getText().toString().equals("") & !phoneNumEdit.getText().toString().equals("")){

            changeNameAndPhone();

        }
        else if(!nameEdit.getText().toString().equals("") & phoneNumEdit.getText().toString().equals("")){
            changeName();
        }
        else if(nameEdit.getText().toString().equals("") & !phoneNumEdit.getText().toString().equals("")){

           changePhone();

        }

    }

    public void changeNameAndPhone(){

        String newName = nameEdit.getText().toString();

        String newPhone = phoneNumEdit.getText().toString();

        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("User").child(uId);
        fireDB.child("name").setValue(newName);
        fireDB.child("phoneNO").setValue(newPhone);

        Toast.makeText(this, "Updated user name: " + newName
                + "\n Updated user Phone No.: " + newPhone, Toast.LENGTH_SHORT).show();
    }

    public void changeName(){
        String newName = nameEdit.getText().toString();
        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("User").child(uId);
        fireDB.child("name").setValue(newName);

        Toast.makeText(this, "Updated user name: " + newName, Toast.LENGTH_SHORT).show();
    }

    public void changePhone(){

        String newPhone = phoneNumEdit.getText().toString();
        DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference("User").child(uId);
        fireDB.child("phoneNO").setValue(newPhone);

        Toast.makeText(this, "Updated user phone No.: " + newPhone, Toast.LENGTH_SHORT).show();
    }

}