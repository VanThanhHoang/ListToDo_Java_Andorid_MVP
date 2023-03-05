package com.example.listtodo_ps25812.Presenter;

import static com.example.listtodo_ps25812.Untilities.Constant.PATH_TASK_ON_FIRE_BASE;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.listtodo_ps25812.Model.Task;
import com.example.listtodo_ps25812.Presenter.Listener.TaskChangeListener;
import com.example.listtodo_ps25812.Presenter.Listener.ValidModelListener;
import com.example.listtodo_ps25812.Untilities.NetworkChecker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TaskFragmentPresenter {
    List<Task> taskList;
    Queue<Task> taskRemovedQueue = new LinkedList<>();
    int positionTaskRemove;
    TaskChangeListener taskChangeListener;
    ValidModelListener validModelListener;
    NetworkChecker networkChecker;
    DatabaseReference tasksRef;

    public TaskFragmentPresenter(TaskChangeListener taskChangeListener, NetworkChecker networkChecker) {
        this.taskChangeListener = taskChangeListener;
        this.networkChecker = networkChecker;
        taskList = new ArrayList<>();
        tasksRef = FirebaseDatabase.getInstance().getReference(PATH_TASK_ON_FIRE_BASE);
        if (networkChecker.isNetworkConnected()) {
            getListFromFireBase();
            addDataRefChildrenEvent();
        }
    }
    public void setValidModelListener(ValidModelListener validModelListener) {
        this.validModelListener = validModelListener;
    }

    public List<Task> getListTask() {
        return taskList;
    }

    void getListFromFireBase() {
        tasksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);
                    taskList.add(0,task);
                }
                taskChangeListener.onGetListTask(taskList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void addDataRefChildrenEvent() {
        tasksRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                /*Task task = snapshot.getValue(Task.class);
                taskList.add(0, task);
                taskChangeListener.onAddTask(0);*/
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Task task = snapshot.getValue(Task.class);
                if (task == null || taskList.isEmpty()) {
                    return;
                }
                for (Task currentTask : taskList) {
                    if (currentTask.equals(task)) {
                        int index = taskList.indexOf(currentTask);
                        taskList.set(index, task);
                        taskChangeListener.onUpdateTask(index);
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Task task = snapshot.getValue(Task.class);
                if (task == null || taskList.isEmpty()) {
                    return;
                }
                Iterator<Task> iterator = taskList.iterator();
                while (iterator.hasNext()) {
                    Task currentTask = iterator.next();
                    if (currentTask.equals(task)) {
                        int index = taskList.indexOf(currentTask);
                        iterator.remove();
                        taskChangeListener.onRemoveTask(index);
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

    public void addTask(Task task) {
        if (task.validateTaskName(task.getName())) {
            taskList.add(0, task);
            taskChangeListener.onAddTask(0);
            tasksRef.push().setValue(task);
            validModelListener.onValidModel();
            return;
        }
        validModelListener.onInValidModel();
    }

    public void updateTask(Task task, int position) {
        if (task.validateTaskName(task.getName())) {
            validModelListener.onValidModel();
            tasksRef.push().updateChildren(task.toMap());
        } else {
            validModelListener.onInValidModel();
            taskChangeListener.onUpdateTask(position);
        }
    }

    public void cancelUpdateTask(int position) {
        taskChangeListener.onUpdateTask(position);
    }

    public void deleteTaskOnList(int position) {
        positionTaskRemove = position;
        taskRemovedQueue.add(taskList.get(positionTaskRemove));
        taskList.remove(positionTaskRemove);
        taskChangeListener.onRemoveTask(position);
    }

    public void deleteTaskOnDatabase() {
        Task task = taskRemovedQueue.poll();
        if (task != null) {
            String key = task.getKey();
            tasksRef.child(key).removeValue();
        }
    }

    public void undoTaskRemoved() {
        Task task = taskRemovedQueue.poll();
        taskList.add(positionTaskRemove, task);
        taskChangeListener.onAddTask(positionTaskRemove);
    }

}
