package com.strongexplorers.schedulemanagement.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.application.ScheduleManagementApplication;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.constants.Consts;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.service.ServiceGenerator;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils.HelperUtils;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.views.ManagerViewEmployeesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerViewEmployeesActivity extends AppCompatActivity {
    ManagerViewEmployeesAdapter manager;
    private ArrayList<SignupDetails> signupDetailsArrayList;
    SharedPreferences sharedPreferences;
    String empid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_employees);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        sharedPreferences = getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        empid = sharedPreferences.getString(Consts.EMP_ID,"");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ServiceGenerator.getService().getSignupList().enqueue(new Callback<ArrayList<SignupDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<SignupDetails>> call, Response<ArrayList<SignupDetails>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }
                signupDetailsArrayList = response.body();
                manager = new ManagerViewEmployeesAdapter(signupDetailsArrayList,ManagerViewEmployeesActivity.this);
                recyclerView.setAdapter(manager);
            }

            @Override
            public void onFailure(Call<ArrayList<SignupDetails>> call, Throwable t) {
                Log.e("onFailure: ", "onFailure");
            }
        });


    }
}
