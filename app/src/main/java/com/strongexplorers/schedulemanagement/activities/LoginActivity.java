package com.strongexplorers.schedulemanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.constants.Consts;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnLogin, signup;
    private boolean isManager = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.userid);
        editTextPassword = findViewById(R.id.loginpassword);
        btnLogin = (Button) findViewById(R.id.loginbutton);
        btnLogin.setOnClickListener(this::login);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


        //setContentView(R.layout.activity_manager_home);
    }

    private void login(View view) {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL+Consts.LOGIN_URL, new Response.Listener<String>() {
        Gson gson = new Gson();
            @Override
            public void onResponse(String response) {
                //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                if (!response.trim().equalsIgnoreCase("user not present please signup")) {

                    SignupDetails signupDetails = gson.fromJson(response.trim(), SignupDetails.class);
                    Log.e("SignupDetails: ",""+signupDetails.getEmail() );
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Consts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Consts.MANAGER_FLAG, (signupDetails.getManager().equalsIgnoreCase("1"))?true:false);
                    editor.putString(Consts.EMP_ID,(signupDetails.getId()));
                    editor.commit();
                    getActivities(signupDetails);
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid password or email", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put(Consts.KEY_EMAIL,email);
                params.put(Consts.KEY_PASSWORD,password);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getActivities(SignupDetails signupDetails) {
        SharedPreferences sharedPreferences = getSharedPreferences(Consts.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        isManager = sharedPreferences.getBoolean(Consts.MANAGER_FLAG,false);
        if(isManager){
            Bundle bundle = new Bundle();
            bundle.putSerializable("signupDetails", signupDetails);
            Intent intent =  new Intent(LoginActivity.this, ManagerHomeActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Bundle bundle = new Bundle();
            bundle.putSerializable("signupDetails", signupDetails);
            Intent intent =  new Intent(LoginActivity.this, EmployeeHomeActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Consts.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //getActivities();

    }

}
