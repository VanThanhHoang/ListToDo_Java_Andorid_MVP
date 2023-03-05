package com.example.listtodo_ps25812.Model;

import java.util.HashMap;
import java.util.Map;

public class TaskItem {
    String key;
    String name;
    boolean status;
    String taskKey;
    public static final boolean DEFAULT_STATUS = false;

    public TaskItem(String taskItemName, boolean status, String taskKey) {
        this.name = taskItemName;
        this.status = status;
        this.taskKey = taskKey;
    }

    public TaskItem(String key, String name, boolean status, String taskKey) {
        this.key = key;
        this.name = name;
        this.status = status;
        this.taskKey = taskKey;
    }

    public TaskItem() {
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
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

    public String getTaskKey() {
        return taskKey;
    }

    public boolean validateTaskName(String taskItemName) {
        return !taskItemName.isEmpty();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("taskKey", this.getKey());
        result.put("status", this.getStatus());
        result.put("name", this.getName());
        result.put("taskId", this.getTaskKey());
        return result;
    }
}
