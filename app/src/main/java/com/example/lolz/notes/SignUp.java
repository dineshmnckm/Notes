package com.example.lolz.notes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    EditText email;
    EditText pass;
    Button but;

    ProgressDialog prg;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        prg = new ProgressDialog(this);

        if(auth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this,Welcome.class));
        }

        email = (EditText) findViewById(R.id.editTextEmail);
        pass = (EditText) findViewById(R.id.editTextPassword);
        but  = (Button) findViewById(R.id.buttonSignup);

        but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String uname = email.getText().toString().trim();
        String pwd = pass.getText().toString().trim();

        if(TextUtils.isEmpty(uname)){
            Toast.makeText(this,"Enter Username",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"Enter Password",Toast.LENGTH_LONG).show();
            return;
        }

        prg.setMessage("Registering Please Wait...");
        prg.show();

        auth.createUserWithEmailAndPassword(uname,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),Welcome.class));
                        }else{
                            //display some message here
                            Toast.makeText(SignUp.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        prg.dismiss();
                    }
                });
    }
}
