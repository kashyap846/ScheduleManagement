package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.strongexplorers.schedulemanagement.activities.SignupActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class HelperUtils {
    private static String result;
    public static String getDateString(long dateInMilli) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Log.e("HelperUtilsget: ", ""+format);
        return format.format(new Date(dateInMilli));
    }
    public static String getURLConnection(String getUrl, String urlSuffix, Context context){
        class HttpGetConnection extends AsyncTask<String,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Please wait", null,true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
                loading.cancel();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(getUrl+urlSuffix);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    int responseCode = con.getResponseCode();
                    Log.e( "doInBackground: ","response"+responseCode );
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    result = bufferedReader.readLine();
                    Log.e("result: ", ""+result);
                    return result;


                }catch (Exception e){
                    Log.e("excepton: ", e.getMessage());
                    return null;
                }

            }
        }
        HttpGetConnection ur = new HttpGetConnection();
        Log.e( "register: ", "before execute" );
        ur.execute(urlSuffix);
        return result;
    }

    public  static long getDateInLong(String dateString){
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        long dateInLong = 0;
        try {
            Date date = format.parse(dateString);
            dateInLong = date.getTime();

        }catch (ParseException pe){
            pe.printStackTrace();
        }
        return dateInLong;
    }

    public  static String getTimeInString(long time){

        String timeInString = "";
        int hourOfDay = (int)time/60;
        int minute = (int)time%60;
        String amPm = "";

        if(hourOfDay>=12){
            amPm = "pm";
        }else{
            amPm = "am";
        }
        //appointmentTime = hourOfDay*60 + minute;
        //textView.setText(String.format("%2d:%2d "+amPm,hourOfDay,minute));

        return String.format("%02d:%02d "+amPm,hourOfDay,minute);
    }

    public  static long getTimeInLong(String time){
        Log.e("getTimeInLong111: ", ""+time);
        String temp[] = time.split(":");
        int hourOfDay = Integer.parseInt(temp[0]);
        String temp1[] = temp[1].split(" ");;
        //appointmentTime = hourOfDay*60 + minute;
        //textView.setText(String.format("%2d:%2d "+amPm,hourOfDay,minute));
        Log.e("getTimeInLong: ",""+hourOfDay + " "+ temp1[0]);
        return (hourOfDay*60) + Integer.parseInt(temp1[0]);
    }
}
