package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.service;

import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.constants.Consts;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.EmployeeSchedule;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ManagerAvailabilityResponse;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ShiftDetails;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ShiftDetailsResponse;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET(Consts.GET_EMPLOYEES_URL)
    Call<ArrayList<SignupDetails>> getSignupList();


    @POST(Consts.ADD_EMPLOYEE_AVAILABILTY)
    Call<ShiftDetailsResponse> addAvailability(@Query("shiftDetails") String shiftDetails, @Query("emp_id") String emp_id);

    @GET(Consts.GET_EMPLOYEE_AVAILABILITY)
    Call<ArrayList<ShiftDetails>> getEmployeeAvailabilityDetails(@Query("emp_id") String empid);

    @GET(Consts.GET_EMPLOYEES_DAY)
    Call<ArrayList<ManagerAvailabilityResponse>> getEmployeeAsPerDay(@Query("day") String day);

    @GET(Consts.GET_EMPLOYEES_BY_ID)
    Call<SignupDetails> getEmployeeAsPerId(@Query("emp_id") String emp_id);

    @POST(Consts.ADD_EMPLOYEES_SCHEDULE)
    Call<ShiftDetailsResponse> addSchedule(@Query("scheduleDetails") String scheduleDetails);


    @GET(Consts.GET_EMPLOYEE_SHIFTS_BY_DATE)
    Call<ArrayList<EmployeeSchedule>> getEmployeesShiftsByDate(@Query("shift_date") String shift_date);


    @GET(Consts.GET_EMPLOYEE_SHIFTS_BY_ID)
    Call<ArrayList<EmployeeSchedule>> getEmployeesShiftsByID(@Query("emp_id") String emp_id);



    @GET(Consts.GET_EMPLOYEE_DETAILS_AVAILABLITY_BY_ID)
    Call<ArrayList<ManagerAvailabilityResponse>> getEmployeesDetailsAvailabilityByID(@Query("emp_id") String emp_id);


}
