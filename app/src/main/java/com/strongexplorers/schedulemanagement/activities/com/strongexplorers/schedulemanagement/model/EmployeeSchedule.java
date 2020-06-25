package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmployeeSchedule implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("emp_id")
    String empId;
    @SerializedName("shift_date")
    long shiftDate;
    @SerializedName("start_time")
    long startTime;
    @SerializedName("end_time")
    long endTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setShiftDate(long shiftDate) {
        this.shiftDate = shiftDate;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public String getEmpId() {
        return empId;
    }

    public long getShiftDate() {
        return shiftDate;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
