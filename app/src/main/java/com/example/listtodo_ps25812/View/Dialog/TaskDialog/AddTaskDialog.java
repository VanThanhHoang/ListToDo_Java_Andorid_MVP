package com.example.listtodo_ps25812.View.Dialog.TaskDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.listtodo_ps25812.Model.Task;
import com.example.listtodo_ps25812.Presenter.Listener.ValidModelListener;
import com.example.listtodo_ps25812.Presenter.TaskFragmentPresenter;
import com.example.listtodo_ps25812.Untilities.CustomWindowDialogHelper;
import com.example.listtodo_ps25812.databinding.TaskdialogBinding;

public class AddTaskDialog extends Dialog implements ValidModelListener {
    TaskdialogBinding taskdialogBinding;
    TaskFragmentPresenter taskFragmentPresenter;
    public AddTaskDialog(@NonNull Context context, TaskFragmentPresenter taskFragmentPresenter) {
        super(context);
        this.taskFragmentPresenter = taskFragmentPresenter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskdialogBinding = TaskdialogBinding.inflate(getLayoutInflater(), null, false);
        setContentView(taskdialogBinding.getRoot());
        CustomWindowDialogHelper.customWindow(this);
        customLayout();
        setOnClick();
        taskFragmentPresenter.setValidModelListener(this);
    }

    void customLayout() {
            taskdialogBinding.tvTitle.setText("Thêm task");
            taskdialogBinding.btnAdd.setText("Thêm");
            taskdialogBinding.btnCancel.setText("Trở lại");
    }

    void setOnClick() {
        taskdialogBinding.btnAdd.setOnClickListener(v ->callPresenterAddTask());
        taskdialogBinding.btnCancel.setOnClickListener(v -> dismiss());
    }
    void callPresenterAddTask(){
        String taskName = taskdialogBinding.edTaskName.getText().toString();
        taskFragmentPresenter.addTask(new Task(taskName));
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
