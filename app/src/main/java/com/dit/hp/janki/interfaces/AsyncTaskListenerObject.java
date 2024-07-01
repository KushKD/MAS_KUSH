package com.dit.hp.janki.interfaces;

import com.dit.hp.janki.Modal.ResponsePojoGet;
import com.dit.hp.janki.enums.TaskType;

import org.json.JSONException;

public interface AsyncTaskListenerObject {
    void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws JSONException;
}