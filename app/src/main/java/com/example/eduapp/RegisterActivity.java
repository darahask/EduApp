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

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth fba;
    EditText emailID;
    EditText password;
    String email_id;
    String pass_word;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fba = FirebaseAuth.getInstance();
        emailID = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        signup = (Button) findViewById(R.id.register);

        signup.setOnClickListener(new View.OnClickListener(){
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
                    Toast.makeText(RegisterActivity.this,"Fields are empty!",Toast.LENGTH_SHORT);
                }
                else{
                    fba.createUserWithEmailAndPassword(email_id,pass_word).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"sign up unsuccessful, try again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    public void openLogPage(View view){
        Intent intent  = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}