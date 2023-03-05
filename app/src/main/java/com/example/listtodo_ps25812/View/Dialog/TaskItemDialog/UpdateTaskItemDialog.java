package com.example.listtodo_ps25812.View.Dialog.TaskItemDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.listtodo_ps25812.Model.TaskItem;
import com.example.listtodo_ps25812.Presenter.Listener.ValidModelListener;
import com.example.listtodo_ps25812.Presenter.TaskItemFragmentPresenter;
import com.example.listtodo_ps25812.Untilities.CustomWindowDialogHelper;
import com.example.listtodo_ps25812.databinding.TaskdialogBinding;

import java.util.Objects;

public class UpdateTaskItemDialog extends Dialog implements ValidModelListener {
    TaskdialogBinding taskdialogBinding;
    TaskItemFragmentPresenter taskItemFragmentPresenter;
    int positionUpdate;
    TaskItem taskItem;
    public UpdateTaskItemDialog(@NonNull Context context, TaskItemFragmentPresenter taskItemFragmentPresenter, int positionUpdate,TaskItem taskItem) {
        super(context);
        this.taskItem = taskItem;
        this.positionUpdate = positionUpdate;
        this.taskItemFragmentPresenter = taskItemFragmentPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskdialogBinding = TaskdialogBinding.inflate(getLayoutInflater(), null, false);
        setContentView(taskdialogBinding.getRoot());
        taskItemFragmentPresenter.setValidModelListener(this);
        CustomWindowDialogHelper.customWindow(this);
        customLayout();
        setOnClick();
    }

    void customLayout() {
        taskdialogBinding.tvTitle.setText("Cập nhật");
        taskdialogBinding.btnAdd.setText("Cập nhật");
        taskdialogBinding.btnCancel.setText("Trở lại");
        taskdialogBinding.edTaskName.setText(taskItem.getName());
    }

    void setOnClick() {
        taskdialogBinding.btnAdd.setOnClickListener(v -> callPresenterAddTask());
        taskdialogBinding.btnCancel.setOnClickListener(v -> {
            taskItemFragmentPresenter.updateTaskItem(taskItem,positionUpdate);
            dismiss();
        });
    }

    void callPresenterAddTask() {
        String taskItemName = Objects.requireNonNull(taskdialogBinding.edTaskName.getText()).toString();
        taskItem.setName(taskItemName);
        taskItemFragmentPresenter.updateTaskItem(taskItem,positionUpdate);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onValidModel() {
        dismiss();
    }

    @Override
    public void onInValidModel() {
        taskdialogBinding.tilTaskName.setError("Không để trống tên task");
    }
}
