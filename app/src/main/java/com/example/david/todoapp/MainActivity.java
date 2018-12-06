package com.example.david.todoapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected DbHelper db;
    List<Task> list;
    private NoScrollListView mTaskTodayListView;
    private NoScrollListView mTaskTomorrowListView;
    private NoScrollListView mTaskOtherListView;
    private TodoAdapter mAdapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int y = 0;
    private int m = 0;
    private int d = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DbHelper(this);
        list = db.getAllTasks();
        mTaskTodayListView = (NoScrollListView) findViewById(R.id.listView1);
        mTaskTomorrowListView = (NoScrollListView) findViewById(R.id.listView2);
        mTaskOtherListView =  (NoScrollListView) findViewById(R.id.listView3);
        updateUI();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
              //  month = month + 1;
                y = year;
                m = month;
                d = dayOfMonth;
            }
        };
    }
    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editText1);
        String s = t.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "enter the task description first!!",
                    Toast.LENGTH_LONG);
        } else {
            Task task = new Task(s);
            if(y != 0 && m != 0 && d != 0)
                task.setDate(y, m, d);
            else{
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                task.setDate(c.getTime());
            }
            db.addTask(task);
            Log.d("tasker", "data added");
            t.setText("");
        }
        updateUI();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void updateUI() {
        list = db.getAllTasks();
        mAdapter = new TodoAdapter(this, getTodayTasks(list));
        mTaskTodayListView.setAdapter(mAdapter);

        mAdapter = new TodoAdapter(this, getTomorrowTasks(list));
        mTaskTomorrowListView.setAdapter(mAdapter);

        mAdapter = new TodoAdapter(this, getOtherTasks(list));
        mTaskOtherListView.setAdapter(mAdapter);
    }

    private List<Task> getTodayTasks(List<Task> list){
        List<Task> t = new ArrayList<Task>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();

        for(Task x : list){
            if(x.getDate().equals(today)){
                t.add(x);
            }
        }

        return t;
    }

    private List<Task> getTomorrowTasks(List<Task> list){
        List<Task> t = new ArrayList<Task>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date tomorrow = c.getTime();

        for(Task x : list){
            if(x.getDate().equals(tomorrow)){
                t.add(x);
            }
        }

        return t;
    }

    private List<Task> getOtherTasks(List<Task> list){
        List<Task> t = new ArrayList<Task>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date tomorrow = c.getTime();

        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();

        for(Task x : list){
            Date d = x.getDate();
            if(!d.equals(today) && !d.equals(tomorrow)){
                t.add(x);
            }
        }

        return t;
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskIDTV = (TextView) parent.findViewById((R.id.task_id));
        String id = String.valueOf(taskIDTV.getText());
        db.deleteTask(Integer.parseInt(id));
        updateUI();
    }

    public void getDate(View v){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
