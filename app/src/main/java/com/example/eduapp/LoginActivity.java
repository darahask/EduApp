package com.example.eduapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity<wordtoSpan> extends AppCompatActivity {

    FirebaseAuth fba;
    EditText emailID;
    EditText password;
    String email_id;
    String pass_word;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fba = FirebaseAuth.getInstance();
        emailID = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login);
        FirebaseAuth.AuthStateListener asl = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbu;
                fbu = fba.getCurrentUser();
                if (fbu != null) {
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                email_id = emailID.getText().toString();
                pass_word = password.getText().toString();
                if(email_id.isEmpty()){
                    emailID.setError("Please enter email id");
                    emailID.requestFocus();
                }
                else if(pass_word.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if(email_id.isEmpty()&&pass_word.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields are empty!",Toast.LENGTH_SHORT);
                }
                else{
                    fba.signInWithEmailAndPassword(email_id,pass_word).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Error, plaese try again!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    public void openRegPage(View view){
        Intent intent  = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}