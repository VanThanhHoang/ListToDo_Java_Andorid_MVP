package com.example.listtodo_ps25812.Presenter.Listener;

import com.example.listtodo_ps25812.Model.Task;

import java.util.List;

public interface TaskChangeListener {
    void onAddTask(int position);
    void onRemoveTask(int position);
    void onUpdateTask(int position);
    void onGetListTask(List<Task> taskList);
}
