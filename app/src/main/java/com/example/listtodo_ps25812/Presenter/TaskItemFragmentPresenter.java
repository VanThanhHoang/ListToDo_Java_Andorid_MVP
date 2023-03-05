package com.example.listtodo_ps25812.Presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.listtodo_ps25812.Model.TaskItem;
import com.example.listtodo_ps25812.Presenter.Listener.TaskItemChangeListener;
import com.example.listtodo_ps25812.Presenter.Listener.ValidModelListener;
import com.example.listtodo_ps25812.Untilities.NetworkChecker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TaskItemFragmentPresenter {
    private static final String TASK_ITEM_PATH = "TASK_IEM";
    ValidModelListener validModelListener;
    TaskItemChangeListener taskItemChangeListener;
    List<TaskItem> taskItemList;
    Queue<TaskItem> queueRemoveTaskItem;
    DatabaseReference taskItemRef;
    NetworkChecker networkChecker;
    int positionRemoved;
    long taskId;

    public TaskItemFragmentPresenter(TaskItemChangeListener taskItemChangeListener, NetworkChecker networkChecker, long taskId) {
        this.taskItemChangeListener = taskItemChangeListener;
        this.queueRemoveTaskItem = new LinkedList<>();
        this.networkChecker = networkChecker;
        this.taskId = taskId;
        taskItemList = new ArrayList<>();
        taskItemRef = FirebaseDatabase.getInstance().getReference(TASK_ITEM_PATH);
        if (networkChecker.isNetworkConnected()) {
            addDataRefChildrenEvent();
        }

    }

    private void addDataRefChildrenEvent() {
        taskItemRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                TaskItem taskItem = snapshot.getValue(TaskItem.class);
                if (taskItem == null || taskItemList.isEmpty()) {
                    return;
                }
                Iterator<TaskItem> iterator = taskItemList.iterator();
                while (iterator.hasNext()) {
                    TaskItem currentTask = iterator.next();
                    if (currentTask.equals(taskItem)) {
                        int index = taskItemList.indexOf(currentTask);
                        iterator.remove();
                        taskItemChangeListener.onRemoveTask(index);
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addTaskItem(TaskItem taskItem) {
        if (taskItem.validateTaskName(taskItem.getName())) {
            taskItemList.add(0, taskItem);
            taskItemChangeListener.onAddTask(0);
            taskItemRef.push().setValue(taskItem);
            validModelListener.onValidModel();
            return;
        }
        validModelListener.onInValidModel();
    }

    public List<TaskItem> getListTaskItem() {
        return taskItemList;
    }

    public void updateTaskItem(TaskItem taskItem, int position) {
        if (taskItem.validateTaskName(taskItem.getName())) {
            taskItemList.set(position, taskItem);
            taskItemRef.push().updateChildren(taskItem.toMap());
            taskItemChangeListener.onUpdateTask(position);
            validModelListener.onValidModel();
            return;
        }
        validModelListener.onInValidModel();
    }

    public void deleteOnList(int position) {
        queueRemoveTaskItem.add(taskItemList.get(position));
        positionRemoved = position;
        taskItemList.remove(position);
        taskItemChangeListener.onRemoveTask(position);
    }

    public void deleteOnDatabase() {
        TaskItem taskItem = queueRemoveTaskItem.poll();
        if (taskItem != null) {
            String key = taskItem.getKey();
            taskItemRef.child(key).removeValue();
        }
    }

    public void undo() {
        TaskItem taskItem = queueRemoveTaskItem.poll();
        taskItemList.add(positionRemoved, taskItem);
        taskItemChangeListener.onAddTask(positionRemoved);
    }

    public void setValidModelListener(ValidModelListener validModelListener) {
        this.validModelListener = validModelListener;
    }

    public void changeStatusTaskItem(TaskItem taskItem) {
        taskItemRef.push().updateChildren(taskItem.toMap());
    }
}
