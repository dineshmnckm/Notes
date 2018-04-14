package com.example.lolz.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private EditText Name,Address;
    private Button buttonLogout,buttonUpdate,pic;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        Name = (EditText) findViewById(R.id.editTextName);
        Address = (EditText) findViewById(R.id.editTextAddress);
        pic = (Button) findViewById(R.id.pic);

        textViewUserEmail.setText("Welcome "+user.getEmail());

        buttonLogout.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        pic.setOnClickListener(this);
    }

    public void addDetails(){

        String name = Name.getText().toString().trim();
        String addr = Address.getText().toString().trim();

        UserDetails userDetails = new UserDetails(name,addr);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child(firebaseUser.getUid()).setValue(userDetails);

        Toast.makeText(this,"Updated!",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }else if(view==buttonUpdate){
            addDetails();
        }else if(view==pic){
            finish();
            startActivity(new Intent(this,Upload.class));
        }
    }
}