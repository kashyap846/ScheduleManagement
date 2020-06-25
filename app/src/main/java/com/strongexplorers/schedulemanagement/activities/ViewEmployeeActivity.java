package com.strongexplorers.schedulemanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;

public class ViewEmployeeActivity extends AppCompatActivity {
    EditText firstname, lastname, password, email, number;
    RadioGroup employeeGroup;
    SignupDetails signupDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        firstname = findViewById(R.id.signup_first_name_value);
        lastname = findViewById(R.id.signup_last_name_value);
        password = findViewById(R.id.signup_create_Password_value);
        email = findViewById(R.id.signup_email_ID_value);
        number = findViewById(R.id.signup_contact_Number_value);

        employeeGroup = findViewById(R.id.employeeGroup);
        Intent intent = getIntent();
        signupDetails = (SignupDetails) intent.getExtras().getSerializable("signupDetails");
        Log.e("ViewEmployeeActivity: ",""+signupDetails.getEmail() );
        populateData();
        //setContentView(R.layout.activity_manager_home);
    }

    private void populateData() {
        firstname.setText(signupDetails.getFirstName());
        lastname.setText(signupDetails.getLastName());
        password.setText(signupDetails.getPassword());
        email.setText(signupDetails.getEmail());
        number.setText(signupDetails.getContactNumber());
        RadioButton rd = (RadioButton)employeeGroup.getChildAt(1);
        rd.setChecked(true);
    }
}
