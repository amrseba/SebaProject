package com.example.sebaproject.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sebaproject.AddNewTask;
import com.example.sebaproject.Model.ToDomodel;
import com.example.sebaproject.Profile;
import com.example.sebaproject.R;
import com.example.sebaproject.dbConnect;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDomodel> todolist;
    private Profile profile;
    private dbConnect db;

    public ToDoAdapter(dbConnect db, Profile profile) {
        this.profile = profile;
        this.db = db;
        this.todolist = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDomodel item = todolist.get(position);

        holder.task.setText(item.getTask());
        holder.taskDescription.setText(item.getDescription());
        holder.taskTime.setText(item.getTime());
        holder.task.setChecked(toBoolean(item.getStatus()));

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.updateTaskStatus(item.getId(), isChecked ? 1 : 0);
            }
        });
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return todolist.size();
    }

    public void setTasks(List<ToDomodel> todolist) {
        this.todolist = todolist;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return profile;
    }
    public void deleteItem(int position){
        ToDomodel item = todolist.get(position);
        db.deleteTask(item.getId());
        todolist.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ToDomodel item = todolist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());

        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(profile.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        TextView taskDescription, taskTime;


        public ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckbox);
            taskDescription = view.findViewById(R.id.taskDescription);
            taskTime = view.findViewById(R.id.taskTime);

        }
    }
}