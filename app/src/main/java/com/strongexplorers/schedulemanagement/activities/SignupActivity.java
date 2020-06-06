package com.strongexplorers.schedulemanagement.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.strongexplorers.schedulemanagement.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {
    EditText firstname, lastname, password, email, number;
    Button submit;
    private static final String REGISTER_URL = "https://8243a65272d2.ngrok.io/schedule_signup.php";


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
        submit.setOnClickListener(this::registerUser);

        //setContentView(R.layout.activity_manager_home);
    }

    private void registerUser(View view) {
        String fname = firstname.getText().toString().trim().toLowerCase();
        String lname = lastname.getText().toString().trim().toLowerCase();
        String paswd = password.getText().toString();
        String emailID = email.getText().toString().trim().toLowerCase();
        String num = number.getText().toString();
        register(fname, lname, paswd, emailID, num);
    }

    private void register(String fname, String lname, String paswd, String emailID, String num) {
        Log.e("register: ", ""+fname+" "+lname+" "+paswd+" "+emailID+" "+num);
        String urlSuffix = "?firstname="+fname+"&lastname="+lname+"&password="+paswd+"&email="+emailID+"&number="+num;
        Log.e("register: ",""+urlSuffix );
        class RegisterUser extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignupActivity.this, "Please wait", null,true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
                loading.cancel();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(REGISTER_URL+urlSuffix);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    int responseCode = con.getResponseCode();
                    Log.e( "doInBackground: ","response"+responseCode );
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result = bufferedReader.readLine();
                    Log.e("result: ", ""+result);
                    return result;


                }catch (Exception e){
                    Log.e("excepton: ", e.getMessage());
                    return null;
                }

            }
        }
        RegisterUser ur = new RegisterUser();
        Log.e( "register: ", "before execute" );
        ur.execute(urlSuffix);


    }
}
