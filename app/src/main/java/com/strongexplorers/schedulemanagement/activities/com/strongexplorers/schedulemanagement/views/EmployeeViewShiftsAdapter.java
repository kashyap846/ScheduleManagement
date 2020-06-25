package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.RecyclerView;

import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.EmployeeViewScheduleActivity;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.EmployeeSchedule;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ManagerAvailabilityResponse;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.service.ServiceGenerator;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils.HelperUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeViewShiftsAdapter extends RecyclerView.Adapter<EmployeeViewShiftsAdapter.EmployeeViewShiftsViewHolder> {
    private ArrayList<EmployeeSchedule> data;
    String empid;


    public EmployeeViewShiftsAdapter(ArrayList<EmployeeSchedule> data,String empid) {

        setHasStableIds(true);
        this.data = data;
        this.empid = empid;

    }
    @NonNull
    @Override
    public EmployeeViewShiftsAdapter.EmployeeViewShiftsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_employees_shifts,parent,false);
        return  new EmployeeViewShiftsAdapter.EmployeeViewShiftsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewShiftsAdapter.EmployeeViewShiftsViewHolder holder, int position) {
        holder.populate(data.get(position));
        Log.e( "onBindViewHolder: ", ""+data.get(position).getEmpId());

    }

    @Override
    public int getItemCount() {
        Log.e("getItemCount: ", ""+data.size());
        return data.size();
    }

    public class EmployeeViewShiftsViewHolder extends RecyclerView.ViewHolder {

        TextView dateText, shiftStart, shiftEnd;


        public EmployeeViewShiftsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.dateText = itemView.findViewById(R.id.date);
            this.shiftStart = itemView.findViewById(R.id.start_time);
            this.shiftEnd = itemView.findViewById(R.id.end_time);
            //itemView.setOnClickListener(this::showDetails);
        }


        public void populate(EmployeeSchedule schedule) {
            dateText.setText(HelperUtils.getDateString(schedule.getShiftDate()));
            shiftStart.setText(HelperUtils.getTimeInString(schedule.getStartTime()));
            shiftEnd.setText(HelperUtils.getTimeInString(schedule.getEndTime()));
        }
    }
}
