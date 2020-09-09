package com.example.eduapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eduapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseAuth fba;
    FirebaseFirestore fbfs;
    EditText name;
    EditText emailID;
    EditText password;
    String email_id;
    String pass_word,name_user;
    Button signup;
    Spinner spinner;
    String class_name;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fba = FirebaseAuth.getInstance();
        fbfs = FirebaseFirestore.getInstance();
        emailID = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        signup = (Button) findViewById(R.id.register);
        name = findViewById(R.id.register_name);
        spinner = findViewById(R.id.class_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.classes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        progressBar = findViewById(R.id.register_progress);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                email_id = emailID.getText().toString();
                pass_word = password.getText().toString();
                name_user = name.getText().toString();
                if(name_user.isEmpty()){
                    name.setError("Please enter your name");
                    name.requestFocus();
                }
                else if(email_id.isEmpty()){
                    emailID.setError("Please enter email id");
                    emailID.requestFocus();
                }
                else if(pass_word.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    fba.createUserWithEmailAndPassword(email_id,pass_word).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                if(!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
                                    Toast.makeText(RegisterActivity.this, "Please enter a valid Email id", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "sign up unsuccessful, try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));
                                Map<String, Object> city = new HashMap<>();
                                city.put("name", name_user);
                                city.put("email", email_id);
                                city.put("class",class_name);
                                fbfs.collection("Users").document(fba.getUid()).set(city);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        class_name = (String)adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this,"Select class",Toast.LENGTH_SHORT).show();
    }
}