package com.strongexplorers.schedulemanagement.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.EmployeeSchedule;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ManagerAvailabilityResponse;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ShiftDetails;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ShiftDetailsResponse;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.service.ServiceGenerator;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils.HelperUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerAddDateSchedule extends AppCompatActivity {
    TextView scheduleDateText;
    TableLayout scheduleLayout;
    Button addButton, submitButton;
    Resources res;
    String day;
    long scheduleDate;
    ArrayList<ManagerAvailabilityResponse> shiftDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_schedule);
        res = getResources();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        scheduleDate =extras.getLong("scheduleDate");
        day = extras.getString("dayOfWeek");

        Log.e("ManagerAddSchedule: ", ""+scheduleDate);
        scheduleDateText = findViewById(R.id.manager_schedule_date);
        String scheduleDateString =  HelperUtils.getDateString(scheduleDate);

        Log.e("ManagerScheduleStrng: ", ""+scheduleDateString);
        scheduleDateText.setText(scheduleDateString);

        scheduleLayout = findViewById(R.id.schedule_layout);
        addButton = findViewById(R.id.manager_prepare_schedule_add);
        addButton.setOnClickListener(this::addScheduleTemplate);

        submitButton = findViewById(R.id.submit_schedule);
        submitButton.setOnClickListener(this::submitSchedule);

        ServiceGenerator.getService().getEmployeeAsPerDay(day).enqueue(new Callback<ArrayList<ManagerAvailabilityResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ManagerAvailabilityResponse>> call, Response<ArrayList<ManagerAvailabilityResponse>> response) {
                Log.e("onResponse11: ", ""+response.body());
                shiftDetailsList = response.body();
                populateShiftEmployeeDetails();

            }

            @Override
            public void onFailure(Call<ArrayList<ManagerAvailabilityResponse>> call, Throwable t) {

            }
        });
        


    }

    private void populateShiftEmployeeDetails() {
        Log.e("populateShiftEmpl1111 ", ""+scheduleDate);
        ServiceGenerator.getService().getEmployeesShiftsByDate(String.valueOf(scheduleDate)).enqueue(new Callback<ArrayList<EmployeeSchedule>>() {
            @Override
            public void onResponse(Call<ArrayList<EmployeeSchedule>> call, Response<ArrayList<EmployeeSchedule>> response) {

                for(EmployeeSchedule employeeSchedule: response.body()){
                    Log.e("onResponse: ",""+employeeSchedule.getEmpId() );
                    TableRow tr = new TableRow(getApplicationContext());



                    Spinner shiftSpinner =  new Spinner(getApplicationContext());
                    shiftSpinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    String[] shiftList = res.getStringArray(R.array.shift_timings_array);
                    Log.e("onResponse small: ",""+HelperUtils.getTimeInString(employeeSchedule.getStartTime())+"-"+HelperUtils.getTimeInString(employeeSchedule.getEndTime()));
                    int index = Arrays.asList(shiftList).indexOf(HelperUtils.getTimeInString(employeeSchedule.getStartTime())+"-"+HelperUtils.getTimeInString(employeeSchedule.getEndTime()));
                    Log.e("onResponse index: ",""+index );
                    ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,shiftList);
                    dayArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

                    shiftSpinner.setAdapter(dayArrayAdapter);
                    shiftSpinner.setSelection(index);
                    tr.addView(shiftSpinner);



                    String itemString = shiftSpinner.getSelectedItem().toString();
                    Log.e("onResponse: ","itemString"+itemString );
                    String item[] = shiftSpinner.getSelectedItem().toString().split("-");
                    ArrayList<String> temp = new ArrayList<>();
                    String empFname = null;
                    temp.add("-");

                    if(!itemString.equalsIgnoreCase("-")) {
                        for (ManagerAvailabilityResponse shiftDetails : shiftDetailsList) {
                            Log.e("onItemSelected: ", "" + HelperUtils.getTimeInLong(shiftDetails.getShiftEnd()));

                            if(HelperUtils.getTimeInLong(item[0])>= HelperUtils.getTimeInLong(shiftDetails.getShiftStart())
                                    && HelperUtils.getTimeInLong(item[1])<= HelperUtils.getTimeInLong(shiftDetails.getShiftEnd())){
                                Log.e("HelperUtils: ", ""+"Emp_id:"+shiftDetails.getEmp_id()+"\n"+"Name: "+shiftDetails.getFirstName());
                                temp.add("Emp_id:"+shiftDetails.getEmp_id()+"\n"+"Name: "+shiftDetails.getFirstName());

                            }
                            if(shiftDetails.getEmp_id().equalsIgnoreCase( employeeSchedule.getEmpId())){
                            empFname = shiftDetails.getFirstName();
                                Log.e("empfname: ",""+empFname );
                            }
                        }
                        }

                    String arrnames[]=temp.toArray(new String[temp.size()]);


                    Spinner empSpinner = new Spinner(getApplicationContext());
                        // spinner.setBackgroundColor(getResources().getColor(R.color.colorPrimary,"");
                        ArrayAdapter<String> employeeArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,temp);
                    Log.e("actual Value: ", ""+"Emp_id:"+employeeSchedule.getEmpId()+"\n"+"Name: "+empFname);
                        int index1 = Arrays.asList(arrnames).indexOf("Emp_id:"+employeeSchedule.getEmpId()+"\n"+"Name: "+empFname);
                    Log.e("onResponse: ","indexxxx"+index1 );
                        employeeArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                        empSpinner.setAdapter(employeeArrayAdapter);
                    empSpinner.setSelection(index1);
                    tr.addView(empSpinner);


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
            public void onFailure(Call<ArrayList<EmployeeSchedule>> call, Throwable t) {
                Log.e("onFailure: ", ""+t.getMessage());

            }
        });








    }

    private void submitSchedule(View view) {
        Log.e("submitSchedule: ", "count"+scheduleLayout.getChildCount());
        HashMap<String,EmployeeSchedule> schedule = new HashMap<>();
        for(int i =0,j = scheduleLayout.getChildCount(); i< j ;i++) {
            if(i==0)
                continue;
            EmployeeSchedule employeeSchedule = new EmployeeSchedule();
            View view1 = scheduleLayout.getChildAt(i);
            TableRow r = (TableRow) view1;

            Spinner spinner = (Spinner) r.getChildAt(0);
            //shiftDetails.setWeek(spinner.getSelectedItem().toString());
            String time[] = spinner.getSelectedItem().toString().split("-");
            employeeSchedule.setShiftDate(scheduleDate);
            employeeSchedule.setStartTime(HelperUtils.getTimeInLong(time[0]));
            employeeSchedule.setEndTime(HelperUtils.getTimeInLong(time[1]));
            Log.e( "submitSchedule: ",""+HelperUtils.getTimeInLong(time[0])+" "+HelperUtils.getTimeInLong(time[1]));

            Spinner empSpinner = (Spinner) r.getChildAt(1);
            String content = empSpinner.getSelectedItem().toString();
            String contents[] = content.split("\n");
            String emp[] = contents[0].split(":");
            Log.e( "final submitSchedule: ", ""+emp[1]);
            employeeSchedule.setEmpId(emp[1]);

            schedule.put(emp[1],employeeSchedule);

        }

        Gson gson = new Gson();
        Log.e( "gsom: ",""+ gson.toJson(schedule).toString());


        ServiceGenerator.getService().addSchedule(gson.toJson(schedule).toString()).enqueue(new Callback<ShiftDetailsResponse>() {
            @Override
            public void onResponse(Call<ShiftDetailsResponse> call, Response<ShiftDetailsResponse> response) {

                String displayMessage = "";
                if (response.body().getMessage().contains("Successfully inserted") || response.body().getMessage().contains("Already present")){
                    Toast.makeText(ManagerAddDateSchedule.this,"Successfully updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ManagerAddDateSchedule.this,"Not updated", Toast.LENGTH_SHORT).show();
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
            Log.e("onClick: ", ""+scheduleLayout.getChildCount());
            int count = scheduleLayout.getChildCount();


            Spinner scheduleSpinner =  new Spinner(getApplicationContext());
            scheduleSpinner.setId(R.id.managerGroup);
            scheduleSpinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            String[] schedulelist = res.getStringArray(R.array.shift_timings_array);
            ArrayAdapter<String> scheduleArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,schedulelist);
            scheduleArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            scheduleSpinner.setAdapter(scheduleArrayAdapter);
            scheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String itemString = parentView.getItemAtPosition(position).toString();
                        String item[] = itemString.split("-");
                        ArrayList<String> temp = new ArrayList<>();
                        temp.add("-");
                        Log.e( "onItemSelected: ", ""+item+day);
                        if(!itemString.equalsIgnoreCase("-")) {
                            for (ManagerAvailabilityResponse shiftDetails : shiftDetailsList) {
                                Log.e("onItemSelected: ", "" + HelperUtils.getTimeInLong(shiftDetails.getShiftEnd()));

                                if(HelperUtils.getTimeInLong(item[0])>= HelperUtils.getTimeInLong(shiftDetails.getShiftStart())
                                        && HelperUtils.getTimeInLong(item[1])<= HelperUtils.getTimeInLong(shiftDetails.getShiftEnd())){
                                    Log.e("HelperUtils: ", shiftDetails.getEmp_id());
                                    temp.add("Emp_id:"+shiftDetails.getEmp_id()+"\n"+"Name: "+shiftDetails.getFirstName());
//                            ServiceGenerator.getService().getEmployeeAsPerId(shiftDetails.getEmp_id()).enqueue(new Callback<SignupDetails>() {
//                                @Override
//                                public void onResponse(Call<SignupDetails> call, Response<SignupDetails> response) {
//                                    temp.add(shiftDetails.getEmp_id());
//                                }
//
//                                @Override
//                                public void onFailure(Call<SignupDetails> call, Throwable t) {
//                                    Log.e("onFailure: ", "onfailure"+t.getMessage());
//                                    t.getMessage();
//
//                                }
//                            });





                                }
                    }

                    TableRow tr = (TableRow) parentView.getParent();
                    Spinner spinner = (Spinner) tr.getChildAt(1);
                   // spinner.setBackgroundColor(getResources().getColor(R.color.colorPrimary,"");
                    ArrayAdapter<String> employeeArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,temp);
                    employeeArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    spinner.setAdapter(employeeArrayAdapter);
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

            tr.addView(scheduleSpinner);

            Spinner employeeSpinner = new Spinner(getApplicationContext());

            String[] employeelist = res.getStringArray(R.array.employee_list_array);
            employeeSpinner.setId(R.id.employeeGroup);
           ArrayAdapter<String> employeeArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,employeelist);
           employeeArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
           employeeSpinner.setAdapter(employeeArrayAdapter);
            employeeSpinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            //employeeSpinner.setSelection(-1);
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

