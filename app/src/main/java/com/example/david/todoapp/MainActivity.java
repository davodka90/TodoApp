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

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected DbHelper db;
    List<Task> list;
    private ListView mTaskListView;
    private TodoAdapter mAdapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DbHelper(this);
        list = db.getAllTasks();
        mTaskListView = (ListView) findViewById(R.id.listView1);
        updateUI();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d("Main", "on date set: " + year + " - " + month + " - " + dayOfMonth);
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
        mAdapter = new TodoAdapter(this, list);
        mTaskListView.setAdapter(mAdapter);
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

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Light, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
