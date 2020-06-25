package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.constants.Consts;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static ApiService SERVICE;

    private static void initialize() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        SERVICE = retrofit.create(ApiService.class);
    }



    public static ApiService getService() {
        if (SERVICE == null) {
            initialize();
        }
        return SERVICE;
    }
}
