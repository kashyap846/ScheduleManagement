package com.strongexplorers.schedulemanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils.HelperUtils;

public class ManagerAddDateSchedule extends AppCompatActivity {
    TextView scheduleDateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        long scheduleDate =extras.getLong("scheduleDate");
        Log.e("ManagerAddSchedule: ", ""+scheduleDate);
        scheduleDateText = findViewById(R.id.manager_schedule_date);
        String scheduleDateString =  HelperUtils.getDateString(scheduleDate);

        Log.e("ManagerScheduleStrng: ", ""+scheduleDateString);
        scheduleDateText.setText(scheduleDateString);

    }
}
