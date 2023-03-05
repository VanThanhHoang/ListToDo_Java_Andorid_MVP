package com.example.listtodo_ps25812.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtodo_ps25812.Listener.OpenItemsFragmentListener;
import com.example.listtodo_ps25812.Model.Task;
import com.example.listtodo_ps25812.databinding.LayoutTaskBinding;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.viewHolderTaskItem> {
    List<Task> taskList;
    LayoutTaskBinding itemTaskBinding;
    OpenItemsFragmentListener openItemsFragmentListener;
    public TaskAdapter(List<Task> taskList, OpenItemsFragmentListener openItemsFragmentListener) {
        this.openItemsFragmentListener = openItemsFragmentListener;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public viewHolderTaskItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemTaskBinding =LayoutTaskBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false);
        return new viewHolderTaskItem(itemTaskBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolderTaskItem holder, int position) {
        holder.tvTaskName.setText(taskList.get(holder.getLayoutPosition()).getName());
        holder.cardView.setOnClickListener(v->openItemsFragmentListener
                .openItemsFragment(taskList.get(holder.getLayoutPosition())));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public class viewHolderTaskItem extends RecyclerView.ViewHolder {
        TextView tvTaskName,tvLeftLine;
        ImageView imgArrow;
        CardView cardView;
        public viewHolderTaskItem(@NonNull LayoutTaskBinding itemTaskBinding) {
            super(itemTaskBinding.getRoot());
            cardView = itemTaskBinding.cardView;
            tvTaskName = itemTaskBinding.tvTaskName;
            tvLeftLine = itemTaskBinding.tvLeftLine;
            imgArrow = itemTaskBinding.imgArrow;
        }
    }

}
