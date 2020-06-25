package com.strongexplorers.schedulemanagement.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.constants.Consts;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.EmployeeSchedule;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.service.ServiceGenerator;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.views.EmployeeViewShiftsAdapter;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.views.ManagerViewEmployeesAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeViewScheduleActivity extends AppCompatActivity {


    EmployeeViewShiftsAdapter employee;
    private ArrayList<EmployeeSchedule> scheduleDetails;
    SharedPreferences sharedPreferences;
    String empid;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employees_shifts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        empid = sharedPreferences.getString(Consts.EMP_ID,"");
        Log.e("onCreate: ", ""+empid);

        ServiceGenerator.getService().getEmployeesShiftsByID(empid).enqueue(new Callback<ArrayList<EmployeeSchedule>>() {
            @Override
            public void onResponse(Call<ArrayList<EmployeeSchedule>> call, Response<ArrayList<EmployeeSchedule>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }
                scheduleDetails = response.body();
                Log.e("onResponse: ", "onResponse"+scheduleDetails.size());
                employee = new EmployeeViewShiftsAdapter(scheduleDetails,empid);
                recyclerView.setAdapter(employee);
            }

            @Override
            public void onFailure(Call<ArrayList<EmployeeSchedule>> call, Throwable t) {
                Log.e("onFailure: ", "onFailure");
            }
        });


    }
}
