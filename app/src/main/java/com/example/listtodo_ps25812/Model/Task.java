package com.example.listtodo_ps25812.Model;

import java.util.HashMap;
import java.util.Map;

public class Task {
    private String key;
    private String name;

    public Task() {
    }

    public Task(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Task(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean equals(Task task) {
        return this.key.equals(task.getKey());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean validateTaskName(String taskName) {
        return !taskName.isEmpty();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", this.getName());
        return result;
    }
}
