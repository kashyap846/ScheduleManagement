package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ManagerAvailabilityResponse implements Serializable {

    @SerializedName("day")
    String week;
    @SerializedName("shift_start")
    String shiftStart;
    @SerializedName("shift_end")
    String shiftEnd;
    @SerializedName("emp_id")
    String emp_id;
    @SerializedName("firstname")
    String firstName;
    @SerializedName("lastname")
    String lastName;
    @SerializedName("email")
    String email;
    @SerializedName("contactNumber")
    String contactNumber;

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
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

    public String getEmp_id() {
        return emp_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
