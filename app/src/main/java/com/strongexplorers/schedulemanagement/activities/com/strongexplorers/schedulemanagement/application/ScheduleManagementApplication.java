package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.application;

import android.app.Application;
import android.content.Context;

public class ScheduleManagementApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        ScheduleManagementApplication.context = getApplicationContext();
        //DoctorAppointmentDBHelper.initialize(getApplicationContext());
    }

    public static Context getContext(){
        return context;
    }
}
