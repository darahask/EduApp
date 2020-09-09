package com.example.eduapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    Button btnplus;

    DatabaseReference ref;
    boolean b = true;

    final ArrayList<Task> taskList = new ArrayList<Task>();
    final TaskAdapter taskAdapter = new TaskAdapter(TaskActivity.this, taskList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        btnplus = findViewById(R.id.plusButton);

        final RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(taskAdapter);

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskActivity.this,AddTaskActivity.class);
                startActivity(intent);
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("tasks").child(FirebaseAuth.getInstance().getUid());
        ref.addChildEventListener(new ChildEventListener(){

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Task task = dataSnapshot.getValue(Task.class);
                taskList.add(task);
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Task task = dataSnapshot.getValue(Task.class);
                String task_key = task.task_key;
                System.out.println(task.task_time);
                for(int i = 0;i<taskList.size();i++){
                    if(taskList.get(i).task_key.equalsIgnoreCase(task_key)){
                        taskList.remove(i);
                        taskList.add(i,task);
                        break;
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                String task_key = task.task_key;
                for(int i = 0;i<taskList.size();i++){
                    if(taskList.get(i).task_key.equalsIgnoreCase(task_key)){
                        taskList.remove(i);
                        break;
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s){}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

        });
    }
}