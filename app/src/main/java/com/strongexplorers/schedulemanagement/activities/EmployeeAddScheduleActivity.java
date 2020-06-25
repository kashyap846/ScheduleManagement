package com.strongexplorers.schedulemanagement.activities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.constants.Consts;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ShiftDetails;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ShiftDetailsResponse;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeeAddScheduleActivity extends AppCompatActivity {
    TableLayout scheduleLayout;
    Button addButton,submitSchedule,back_button;
    Resources res;
    TimePickerDialog timePickerDialog;

    int currentHour, currentMinute;
    String amPm;
    SharedPreferences sharedPreferences;
    String empid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_schedule);
        sharedPreferences = getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        empid = sharedPreferences.getString(Consts.EMP_ID,"");
        res = getResources();
        scheduleLayout = findViewById(R.id.employee_schedule_layout);
        addButton = findViewById(R.id.employee_prepare_schedule_add);
        addButton.setOnClickListener(this::addScheduleTemplate);
        submitSchedule = findViewById(R.id.submit_schedule);
        submitSchedule.setOnClickListener(this::submitSchedule);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        populateShiftDetails();
    }

    private void populateShiftDetails() {
        ServiceGenerator.getService().getEmployeeAvailabilityDetails(empid).enqueue(new Callback<ArrayList<ShiftDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<ShiftDetails>> call, Response<ArrayList<ShiftDetails>> response) {
               // Log.e("onResponse: ", "success"+response.body().get(0).getShiftEnd() +" "+empid);

                for(ShiftDetails shiftDetails: response.body()){

                    TableRow tr = new TableRow(getApplicationContext());

                    int count = scheduleLayout.getChildCount();


                    Spinner daySpinner =  new Spinner(getApplicationContext());
                    daySpinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    String[] daylist = res.getStringArray(R.array.day_name);
                    int index = Arrays.asList(daylist).indexOf(shiftDetails.getWeek());
                    ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,daylist);
                    dayArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

                    daySpinner.setAdapter(dayArrayAdapter);
                    daySpinner.setSelection(index);
                    tr.addView(daySpinner);


                    Button startButton = new Button(getApplicationContext());
                    startButton.setText(shiftDetails.getShiftStart());
                    startButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectTime(startButton);
                        }
                    });
                    tr.addView(startButton);


                    Button endButton = new Button(getApplicationContext());
                    endButton.setText(shiftDetails.getShiftEnd());
                    endButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectTime(endButton);
                        }
                    });
                    tr.addView(endButton);



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

            @Override
            public void onFailure(Call<ArrayList<ShiftDetails>> call, Throwable t) {



            }
        });


    }

    private void submitSchedule(View view) {
        Log.e( "submitSchedule: ",""+scheduleLayout.getChildCount() );
        HashMap<String,ShiftDetails> details = new HashMap<>();
        for(int i =0,j = scheduleLayout.getChildCount(); i< j ;i++) {

            //get Child views of the table with table.getChildAt(int index), this returns a view
            //cast it to a TextView
            if(i==0)
                continue;
            ShiftDetails shiftDetails = new ShiftDetails();
            View view1 = scheduleLayout.getChildAt(i);
            TableRow r = (TableRow) view1;

            Spinner spinner = (Spinner) r.getChildAt(0);
            shiftDetails.setWeek(spinner.getSelectedItem().toString());
            Log.e( "submitSchedule: ",""+spinner.getSelectedItem().toString());

            Button button1 = (Button) r.getChildAt(1);
            shiftDetails.setShiftStart(button1.getText().toString());
            Log.e( "submitSchedule: ",""+button1.getText().toString());

            Button button2 = (Button) r.getChildAt(2);
            shiftDetails.setShiftEnd(button2.getText().toString());
            Log.e( "submitSchedule: ",""+button2.getText().toString());


            //in this row (row i) of the table get the child element(column) where the first column would have a value of 0
           // TextView textView = (TextView) r.getChildAt(0).getId();
           // String week = textView.getText().toString();
                    //spinner1.getSelectedItem().toString();
            Log.e("row count: "+i, ""+r.getChildCount());
            details.put(spinner.getSelectedItem().toString(),shiftDetails);


            //details.

            //String CatNo = getCatno.getText().toString();
        }

        Gson gson = new Gson();
       Log.e( "gsom: ",""+ gson.toJson(details).toString());
        Log.e("submitSchedule: ", ""+empid);

        ServiceGenerator.getService().addAvailability(gson.toJson(details).toString(),empid).enqueue(new Callback<ShiftDetailsResponse>() {
            @Override
            public void onResponse(Call<ShiftDetailsResponse> call, Response<ShiftDetailsResponse> response) {
                Log.e("onResponse: ", "onResponse"+response.body().getMessage());
                String displayMessage = "";
                if (response.body().getMessage().contains("Successfully inserted") || response.body().getMessage().contains("Already present")){
                    Toast.makeText(EmployeeAddScheduleActivity.this,"Successfully updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EmployeeAddScheduleActivity.this,"Not updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShiftDetailsResponse> call, Throwable t) {
                Log.e( "onFailure: ", "onfailure");
                Log.e("onFailure: ", ""+t.getMessage());
                //t.getMessage();

            }
        });

    }

    private void addScheduleTemplate(View view) {
        TableRow tr = new TableRow(getApplicationContext());

        int count = scheduleLayout.getChildCount();


        Spinner daySpinner =  new Spinner(getApplicationContext());
        daySpinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        String[] daylist = res.getStringArray(R.array.day_name);
        ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,daylist);
        dayArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        daySpinner.setAdapter(dayArrayAdapter);
        tr.addView(daySpinner);



//        TextView startText = new TextView(getApplicationContext());
//        startText.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//        startText.setText("START TIME");
//        startText.setTextSize(16);
//        startText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//        tr.addView(startText);
//
//        TextView endText = new TextView(getApplicationContext());
//        endText.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//        endText.setText("END TIME");
//        startText.setTextSize(16);
//        startText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
//        tr.addView(endText);

        Button startButton = new Button(getApplicationContext());
        startButton.setText("START");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(startButton);
            }
        });
        tr.addView(startButton);


        Button endButton = new Button(getApplicationContext());
        endButton.setText("END");
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(endButton);
            }
        });
        tr.addView(endButton);



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
        Log.e("onClick: ", ""+scheduleLayout.getChildCount());
    }

    private void selectTime(Button button) {
        timePickerDialog = new TimePickerDialog(EmployeeAddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
            Calendar calendar = Calendar.getInstance();



            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if(hourOfDay>=12){
                    amPm = "pm";
                }else{
                    amPm = "am";
                }

               button.setText(String.format("%02d:%02d "+amPm,hourOfDay,minute));
            }
        },0,0,false);
        timePickerDialog.show();
    }
}
