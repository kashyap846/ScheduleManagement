package com.strongexplorers.schedulemanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.strongexplorers.schedulemanagement.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static final String LOGIN_URL= "";
    public static final String KEY_EMAIL= "email" ;
    public static final String KEY_PASSWORD = "password" ;
    public static final String LOGIN_SUCCESS = "success" ;
    public static final String SHARED_PREF_NAME = "tech" ;
    public static final String EMAIL_SHARED_PREF = "email" ;
    public static final String LOGGEDIN_SHARED_PREF = "loggedin" ;
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin, signup;
    private boolean loggedIn = false;

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase(LOGIN_SUCCESS)) {
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                    editor.putBoolean(EMAIL_SHARED_PREF, true);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
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
                params.put(KEY_EMAIL,email);
                params.put(KEY_PASSWORD,password);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF,false);
        if (loggedIn){
            Intent intent =  new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}
