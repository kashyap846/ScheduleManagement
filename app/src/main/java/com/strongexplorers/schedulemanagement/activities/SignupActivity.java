package com.strongexplorers.schedulemanagement.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.constants.Consts;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils.HelperUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {
    EditText firstname, lastname, password, email, number;
    Button submit,login;
    RadioGroup managerGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstname = findViewById(R.id.signup_first_name_value);
        lastname = findViewById(R.id.signup_last_name_value);
        password = findViewById(R.id.signup_create_Password_value);
        email = findViewById(R.id.signup_email_ID_value);
        number = findViewById(R.id.signup_contact_Number_value);
        submit = findViewById(R.id.signup_submit);
        managerGroup = findViewById(R.id.managerGroup);
        submit.setOnClickListener(this::registerUser);
        login = findViewById(R.id.signup_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //setContentView(R.layout.activity_manager_home);
    }

    private void registerUser(View view) {
        String fname = firstname.getText().toString().trim().toLowerCase();
        String lname = lastname.getText().toString().trim().toLowerCase();
        String paswd = password.getText().toString();
        String emailID = email.getText().toString().trim().toLowerCase();
        String num = number.getText().toString();
        int selectedId = managerGroup.getCheckedRadioButtonId();
        int m = -1;
        if(selectedId!=-1){
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String manager = radioButton.getText().toString();
            m = (manager.trim().toLowerCase().equals("yes"))?1:0;
        }

        register(fname, lname, paswd, emailID, num, m);
    }

    private void register(String fname, String lname, String paswd, String emailID, String num, int manager) {
        Log.e("register: ", ""+fname+" "+lname+" "+paswd+" "+emailID+" "+num);
        String urlSuffix = "?firstname="+fname+"&lastname="+lname+"&password="+paswd+"&email="+emailID+"&number="+num+"&manager="+manager;
        Log.e("register: ",""+urlSuffix );
        HelperUtils.getURLConnection(Consts.BASE_URL+Consts.SIGNUP_REGISTER_URL, urlSuffix,SignupActivity.this);
    }
}
