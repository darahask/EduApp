package com.example.eduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditDelActivity extends AppCompatActivity {

    EditText task_title,task_desc,task_time;
    Button btn_update,btn_delete;
    String task_key;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_del);
        task_title = findViewById(R.id.task_title);
        task_desc = findViewById(R.id.task_desc);
        task_time = findViewById(R.id.task_time);

        btn_update = findViewById(R.id.Update_btn);
        btn_delete = findViewById(R.id.Delete_btn);

        task_title.setText(getIntent().getStringExtra("task_title"));
        task_desc.setText(getIntent().getStringExtra("task_desc"));
        task_time.setText(getIntent().getStringExtra("task_time"));
        task_key = getIntent().getStringExtra("task_key");
        ref = FirebaseDatabase.getInstance().getReference().child("tasks").child(task_key);
    }

    public void delete(View view){
        ref.removeValue();
        Intent intent  = new Intent(EditDelActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void update(View view){
        Task task = new Task(task_title.getText().toString(),task_desc.getText().toString(),task_time.getText().toString(),task_key);
        ref.setValue(task);
        Intent intent  = new Intent(EditDelActivity.this,MainActivity.class);
        startActivity(intent);
    }

}