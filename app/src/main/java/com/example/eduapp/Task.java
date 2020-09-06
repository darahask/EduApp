package com.example.eduapp;

public class Task {
    String task_title, task_desc, task_time, task_key;


    public Task() {}

    public Task(String task_title, String task_desc, String task_time,String task_key) {
        this.task_title = task_title;
        this.task_desc = task_desc;
        this.task_time = task_time;
        this.task_key = task_key;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTask_key() {
        return task_key;
    }

    public void setTask_key(String task_key) {
        this.task_key = task_key;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public String getTask_time() {
        return task_time;
    }

    public void setTask_time(String task_time) {
        this.task_time = task_time;
    }

}
