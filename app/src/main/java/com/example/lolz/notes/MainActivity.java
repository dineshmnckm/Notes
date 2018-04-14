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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email;
    EditText pass;
    Button but;
    TextView signup;

    FirebaseAuth auth;
    ProgressDialog prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        prg = new ProgressDialog(this);

        if(auth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this,Welcome.class));
        }

        email = (EditText) findViewById(R.id.editTextEmail);
        pass = (EditText) findViewById(R.id.editTextPassword);
        but  = (Button) findViewById(R.id.buttonSignin);
        signup = (TextView) findViewById(R.id.signup);

        but.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==but){
            signin();
        }else if(v==signup){
            finish();
            Intent intent = new Intent(this,SignUp.class);
            startActivity(intent);
        }
    }

    public void signin(){

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

        prg.setMessage("Signing in...");
        prg.show();

        auth.signInWithEmailAndPassword(uname,pwd).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            prg.dismiss();
                            finish();
                            Intent innn = new Intent(MainActivity.this,Welcome.class);
                            startActivity(innn);
                        }else{
                            prg.dismiss();
                            Toast.makeText(MainActivity.this,"Incorrect Username/Password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
