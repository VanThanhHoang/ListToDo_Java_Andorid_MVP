package com.example.listtodo_ps25812.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtodo_ps25812.Listener.ChangeStatusTaskItem;
import com.example.listtodo_ps25812.Listener.OpenItemsFragmentListener;
import com.example.listtodo_ps25812.Model.TaskItem;
import com.example.listtodo_ps25812.databinding.LayoutTaskItemBinding;

import java.util.List;

public class TaskItemAdapter extends RecyclerView.Adapter<TaskItemAdapter.viewHolderTaskItem> {
    List<TaskItem> taskList;
    LayoutTaskItemBinding itemBinding;
    OpenItemsFragmentListener openItemsFragmentListener;
    ChangeStatusTaskItem changeStatusTaskItem;
    public TaskItemAdapter(List<TaskItem> taskList, OpenItemsFragmentListener openItemsFragmentListener,ChangeStatusTaskItem changeStatusTaskItem) {
        this.openItemsFragmentListener = openItemsFragmentListener;
        this.taskList = taskList;
        this.changeStatusTaskItem = changeStatusTaskItem;
    }

    @NonNull
    @Override
    public viewHolderTaskItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBinding = LayoutTaskItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false);
        return new viewHolderTaskItem(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderTaskItem holder, int position) {
        TaskItem taskItem = taskList.get(holder.getLayoutPosition());
        holder.tvTaskName.setText(taskItem.getName());
        if (taskItem.getStatus()) {
            holder.chkIsDone.setChecked(true);
        }
        holder.chkIsDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            taskItem.setStatus(isChecked);
            changeStatusTaskItem.onChangeTaskItemStatus(taskItem);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class viewHolderTaskItem extends RecyclerView.ViewHolder {
        TextView tvTaskName;
        CheckBox chkIsDone;
        public viewHolderTaskItem(@NonNull LayoutTaskItemBinding itemTaskBinding) {
            super(itemTaskBinding.getRoot());
            tvTaskName = itemTaskBinding.tvTaskName;
            chkIsDone = itemTaskBinding.chkIsDone;
        }
    }

}
