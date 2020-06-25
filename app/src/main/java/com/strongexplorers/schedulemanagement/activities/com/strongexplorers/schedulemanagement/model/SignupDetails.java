package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class SignupDetails implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("contactNumber")
    private String contactNumber;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("manager")
    private String manager;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getManager() {
        return manager;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignupDetails that = (SignupDetails) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
