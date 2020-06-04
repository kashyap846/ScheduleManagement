package com.strongexplorers.schedulemanagement.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils.HelperUtils;

import java.util.ArrayList;

public class ManagerAddDateSchedule extends AppCompatActivity {
    TextView scheduleDateText;
    TableLayout scheduleLayout;
    Button addButton;
    Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_schedule);
        res = getResources();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        long scheduleDate =extras.getLong("scheduleDate");
        Log.e("ManagerAddSchedule: ", ""+scheduleDate);
        scheduleDateText = findViewById(R.id.manager_schedule_date);
        String scheduleDateString =  HelperUtils.getDateString(scheduleDate);

        Log.e("ManagerScheduleStrng: ", ""+scheduleDateString);
        scheduleDateText.setText(scheduleDateString);

        scheduleLayout = findViewById(R.id.schedule_layout);
        addButton = findViewById(R.id.manager_prepare_schedule_add);
        addButton.setOnClickListener(this::addScheduleTemplate);


    }

    private void addScheduleTemplate(View view) {
            TableRow tr = new TableRow(getApplicationContext());
            Log.e("onClick: ", ""+scheduleLayout.getChildCount());
            int count = scheduleLayout.getChildCount();


            Spinner scheduleSpinner =  new Spinner(getApplicationContext());
            scheduleSpinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            String[] schedulelist = res.getStringArray(R.array.shift_timings_array);
            ArrayAdapter<String> scheduleArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,schedulelist);
            scheduleArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            scheduleSpinner.setAdapter(scheduleArrayAdapter);
            tr.addView(scheduleSpinner);

            Spinner employeeSpinner = new Spinner(getApplicationContext());
            String[] employeelist = res.getStringArray(R.array.employee_list_array);
            ArrayAdapter<String> employeeArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,employeelist);
            employeeArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            employeeSpinner.setAdapter(employeeArrayAdapter);
            employeeSpinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(employeeSpinner);


            Button deleteButton = new Button(getApplicationContext());
            deleteButton.setText("Delete");
            tr.addView(deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scheduleLayout.removeView(tr);
                }
            });


            scheduleLayout.addView(tr);
        }
    }

