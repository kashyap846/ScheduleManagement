package com.strongexplorers.schedulemanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;

public class EmployeeHomeActivity extends AppCompatActivity {
    Button viewEmployee, viewOrChangeSchedule, viewSchedule;
    TextView welcome_message_employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        viewEmployee = findViewById(R.id.view_details);
        viewOrChangeSchedule = findViewById(R.id.change_schedule);
        welcome_message_employee = findViewById(R.id.welcome_message_employee);
        viewEmployee.setOnClickListener(this::viewDetails);
        viewOrChangeSchedule.setOnClickListener(this::changeSchedule);

        viewSchedule = findViewById(R.id.view_schedule);
        viewSchedule.setOnClickListener(this::viewScheduleDetails);


    }

    private void viewScheduleDetails(View view){
        Intent intent = new Intent(getApplicationContext(),EmployeeViewScheduleActivity.class);
        startActivity(intent);
    }

    private void changeSchedule(View view) {
        Intent intent = new Intent(getApplicationContext(),EmployeeAddScheduleActivity.class);
        startActivity(intent);
    }

    private void viewDetails(View view) {
        Bundle bundle = new Bundle();
        Intent loginIntent = this.getIntent();
        SignupDetails signupDetails = (SignupDetails) loginIntent.getExtras().getSerializable("signupDetails");
        Intent intent =  new Intent(EmployeeHomeActivity.this, ViewEmployeeActivity.class);
        intent.putExtra("signupDetails",signupDetails);
        startActivity(intent);

    }
}
