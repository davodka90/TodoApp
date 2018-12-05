package com.example.david.todoapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends ArrayAdapter<Task> {
    private Context mContext;
    private List<Task> todoList = new ArrayList<>();

    public TodoAdapter(Context context, List<Task> list) {
        super(context, 0, list);
        mContext = context;
        todoList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.todo_list, parent,false);

        Task currentTask = todoList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.task_title);
        name.setText(currentTask.getTaskName());

        TextView id = (TextView) listItem.findViewById(R.id.task_id);
        id.setText(String.valueOf(currentTask.getId()));

        return listItem;
    }
}
