package com.example.eduapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTaskActivity extends AppCompatActivity{
    TextView title, add_title, add_desc, add_date;
    EditText task_title, task_desc, task_time;
    String task_key;
    Button Create_btn, Cancel_btn;
    DatabaseReference ref;

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        title = findViewById(R.id.title);

        add_title = findViewById(R.id.add_title);
        add_desc = findViewById(R.id.add_desc);
        add_date = findViewById(R.id.add_date);

        task_title = findViewById(R.id.task_title);
        task_desc = findViewById(R.id.task_desc);
        task_time = findViewById(R.id.task_time);

        Create_btn = findViewById(R.id.Create_btn);
        Cancel_btn = findViewById(R.id.Cancel_btn);

        Create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("tasks");
                task_key = ref.push().getKey();
                Task task = new Task(task_title.getText().toString(),task_desc.getText().toString(),task_time.getText().toString(),task_key);
                ref.child(task_key).setValue(task);
                Intent intent = new Intent(AddTaskActivity.this,TaskActivity.class);
                startActivity(intent);
            }
        });

        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTaskActivity.this,TaskActivity.class);
                startActivity(intent);
            }
        });
    }
}