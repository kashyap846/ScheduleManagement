package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model;

import com.google.gson.Gson;
import com.strongexplorers.schedulemanagement.activities.SignupActivity;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.application.ScheduleManagementApplication;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils.HelperUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SignupDetailsList {
    private static final String REGISTER_URL = "https://baf6f1e005d1.ngrok.io/all_employees.php";
    private ArrayList<SignupDetails> signupDetailsArrayList;

    public ArrayList<SignupDetails> getSignupDetailsArrayList() {
        return signupDetailsArrayList;
    }

    public void setSignupDetailsArrayList(ArrayList<SignupDetails> signupDetailsArrayList) {
        this.signupDetailsArrayList = signupDetailsArrayList;
    }
    //    private ArrayList<SignupDetails> getDetailsListFromDB() {
//        String response = HelperUtils.getURLConnection(REGISTER_URL, "", ScheduleManagementApplication.getContext());
//        ArrayList<SignupDetails>  signupList = gson.fromJson(response.trim(), (Type) SignupDetailsList.class);
//
//        return  signupList;
//    }


    public int getCount(){
        return signupDetailsArrayList.size();
    }


    public SignupDetails get(int position){
        return signupDetailsArrayList.get(position);
    }



}
