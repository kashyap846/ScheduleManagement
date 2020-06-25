package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShiftDetails implements Serializable {
    @SerializedName("day")
    String week;
    @SerializedName("shift_start")
    String shiftStart;
    @SerializedName("shift_end")
    String shiftEnd;
    @SerializedName("emp_id")
    String emp_id;
    @SerializedName("id")
    String id;

    public String getEmp_id() {
        return emp_id;
    }

    public String getId() {
        return id;
    }

    public String getWeek() {
        return week;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }
}
