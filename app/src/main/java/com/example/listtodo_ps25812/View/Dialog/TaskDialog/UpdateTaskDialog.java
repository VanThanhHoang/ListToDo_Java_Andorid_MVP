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

import java.util.Objects;

public class UpdateTaskDialog extends Dialog implements ValidModelListener {
    TaskdialogBinding taskdialogBinding;
    int positionUpdate;
    Task taskUpdate;
    TaskFragmentPresenter taskFragmentPresenter;

    public UpdateTaskDialog(@NonNull Context context, TaskFragmentPresenter taskFragmentPresenter, Task task, int position) {
        super(context);
        this.taskFragmentPresenter = taskFragmentPresenter;
        this.taskUpdate = task;
        this.positionUpdate = position;
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
        taskdialogBinding.tvTitle.setText("Cập nhật");
        taskdialogBinding.btnAdd.setText("Cập nhật");
        taskdialogBinding.btnCancel.setText("Trở lại");
        taskdialogBinding.edTaskName.setText(taskUpdate.getName());
    }

    void setOnClick() {
        taskdialogBinding.btnAdd.setOnClickListener(v -> callPresenterUpdateTask());
        taskdialogBinding.btnCancel.setOnClickListener(v -> callPresenterCancelUpdate());
    }

    void callPresenterUpdateTask() {
        String taskName = Objects.requireNonNull(taskdialogBinding.edTaskName.getText()).toString();
        taskUpdate.setName(taskName);
        taskFragmentPresenter.updateTask(taskUpdate, positionUpdate);
    }

    void callPresenterCancelUpdate() {
        taskFragmentPresenter.cancelUpdateTask(positionUpdate);
        dismiss();
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
