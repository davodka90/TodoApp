package com.example.david.todoapp;

import java.util.Date;

public class Task {
    private String taskName;
    private int id;
    private Date date;

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

    public Date getDate() { return date; }

    public void setDate(int year, int month, int day) { this.date = new Date(year-1900, month, day); }

    public void setDate(Date d) { this.date = d; }
}
