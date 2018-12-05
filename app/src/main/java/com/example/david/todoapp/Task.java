package com.example.david.todoapp;

public class Task {
    private String taskName;
    private int id;

    public Task(){
        this.taskName = null;
    }

    public Task(String taskName){
        super();
        this.taskName = taskName;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTaskName(){
        return taskName;
    }

    public void setTaskName(String taskName){
        this.taskName = taskName;
    }
}
