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

public class AddTaskItemDialog extends Dialog implements ValidModelListener {
    TaskdialogBinding taskdialogBinding;
    TaskItemFragmentPresenter taskItemFragmentPresenter;
    String taskKey;

    public AddTaskItemDialog(@NonNull Context context, TaskItemFragmentPresenter taskItemFragmentPresenter, String taskKey) {
        super(context);
        this.taskKey = taskKey;
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
        taskdialogBinding.tvTitle.setText("Thêm mục");
        taskdialogBinding.btnAdd.setText("Thêm");
        taskdialogBinding.btnCancel.setText("Trở lại");
    }

    void setOnClick() {
        taskdialogBinding.btnAdd.setOnClickListener(v -> callPresenterAddTask());
        taskdialogBinding.btnCancel.setOnClickListener(v -> dismiss());
    }

    void callPresenterAddTask() {
        String taskItemName = Objects.requireNonNull(taskdialogBinding.edTaskName.getText()).toString();
        taskItemFragmentPresenter.addTaskItem(new TaskItem(taskItemName, TaskItem.DEFAULT_STATUS, taskKey));
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
