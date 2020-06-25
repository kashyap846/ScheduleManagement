package com.strongexplorers.schedulemanagement.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.strongexplorers.schedulemanagement.R;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class ManagerHomeActivity extends AppCompatActivity {
    Button prepareSchedule, viewEmployees;
    long scheduleDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        prepareSchedule = findViewById(R.id.manager_prepare_schedule);
        prepareSchedule.setOnClickListener(this::addDatePickDialog);
        viewEmployees = findViewById(R.id.manager_view_employee);
        viewEmployees.setOnClickListener(this::openEmployeeActivity);
    }

    private void openEmployeeActivity(View view) {
        Intent intent =  new Intent(ManagerHomeActivity.this,ManagerViewEmployeesActivity.class);
        startActivity(intent);
    }

    private void addDatePickDialog(View view) {
        Calendar appointmentCalendar = Calendar.getInstance();
        appointmentCalendar.setTime(scheduleDate > 0 ? new Date(scheduleDate) : new Date());
        int year = appointmentCalendar.get(Calendar.YEAR);
        int month = appointmentCalendar.get(Calendar.MONTH);
        int day = appointmentCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),this::onAppointmentDateSet,year,month,day);
        datePickerDialog.show();
    }

    private void onAppointmentDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,month,dayOfMonth);
        scheduleDate = calendar.getTimeInMillis();

        // Get the weekday and print it
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        Log.e("addDatePickDialog: ",""+weekday );

        // Get weekday name
        DateFormatSymbols dfs = new DateFormatSymbols();
        Log.e("addDatePickDialog " ,""+ dfs.getWeekdays()[weekday]);

        //dateTextView.setText(DoctorUtils.getDateString(appointmentDate));
        Log.e("onAppointmentDateSet: ", ""+scheduleDate);
        Intent intent = new Intent(this, ManagerAddDateSchedule.class);
        Bundle extras = new Bundle();
        extras.putLong("scheduleDate",scheduleDate);
        extras.putString("dayOfWeek",dfs.getWeekdays()[weekday]);
        intent.putExtras(extras);
       // intent.p
        startActivity(intent);

    }

}

