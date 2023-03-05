package com.example.listtodo_ps25812.Presenter.Listener;
import com.example.listtodo_ps25812.Model.TaskItem;
import java.util.List;

public interface TaskItemChangeListener {
    void onAddTask(int position);
    void onRemoveTask(int position);
    void onUpdateTask(int position);
    void onGetListTask(List<TaskItem> taskItemList);
}
