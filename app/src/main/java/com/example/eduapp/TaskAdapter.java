package com.example.eduapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder>{

    Context context;
    ArrayList<Task> TaskList;

    public TaskAdapter(Context context, ArrayList<Task> TaskList) {
        this.context = context;
        this.TaskList = TaskList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.task,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i){
        myViewHolder.task_title.setText(TaskList.get(i).getTask_title());
        myViewHolder.task_desc.setText(TaskList.get(i).getTask_desc());
        myViewHolder.task_time.setText(TaskList.get(i).getTask_time());

        final String getTaskTitle = TaskList.get(i).getTask_title();
        final String getTaskDesc = TaskList.get(i).getTask_desc();
        final String getTaskTime = TaskList.get(i).getTask_time();
        final String getTaskKey = TaskList.get(i).getTask_key();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditDelActivity.class);
                intent.putExtra("task_title", getTaskTitle);
                intent.putExtra("task_desc",getTaskDesc);
                intent.putExtra("task_time",getTaskTime);
                intent.putExtra("task_key",getTaskKey);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return TaskList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public BreakIterator task_key;
        TextView task_title, task_desc, task_time;

        public MyViewHolder(@NonNull View taskView) {
            super(taskView);
            task_title = (TextView) itemView.findViewById(R.id.task_title);
            task_desc = (TextView) itemView.findViewById(R.id.task_desc);
            task_time = (TextView) itemView.findViewById(R.id.task_time);
        }
    }

}
