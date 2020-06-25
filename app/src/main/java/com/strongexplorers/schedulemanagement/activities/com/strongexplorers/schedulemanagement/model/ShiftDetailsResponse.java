package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShiftDetailsResponse implements Serializable {
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
