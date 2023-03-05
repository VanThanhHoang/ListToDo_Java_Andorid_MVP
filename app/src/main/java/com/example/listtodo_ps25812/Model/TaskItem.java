package com.example.listtodo_ps25812.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

public class TaskItem {
    String key;
    String name;
    boolean status;
    long taskId;
    @Ignore
    public static final boolean DEFAULT_STATUS = false;
    public TaskItem( String key, String name, boolean status,int taskId) {
        this.key = key;
        this.name = name;
        this.status = status;
        this.taskId = taskId;
    }
    @Ignore
    public TaskItem(String taskItemName,boolean status, long taskId) {
        this.name = taskItemName;
        this.status = status;
        this.taskId = taskId;
    }
    @Ignore
    public TaskItem(){}
    public String getKey() {
        return key;
    }

    public void setKey( String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public boolean validateTaskName(String taskItemName){
        return !taskItemName.isEmpty();
    }
    public Map<String,Object> toMap(){
        Map<String,Object> result = new HashMap<>();
        result.put("status",this.getStatus());
        result.put("name",this.getName());
        result.put("taskId",this.getTaskId());
        return  result;
    }
}
