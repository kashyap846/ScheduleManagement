package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.strongexplorers.schedulemanagement.R;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.ManagerAvailabilityResponse;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.model.SignupDetails;
import com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.service.ServiceGenerator;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerViewEmployeesAdapter extends RecyclerView.Adapter<ManagerViewEmployeesAdapter.ManagerViewEmployeesViewHolder> {

    private ArrayList<SignupDetails> data;
    String message;

    Context context;
    public ManagerViewEmployeesAdapter(ArrayList<SignupDetails> data, Context context) {

        setHasStableIds(true);
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public ManagerViewEmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_manager_employees,parent,false);
        return  new ManagerViewEmployeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerViewEmployeesViewHolder holder, int position) {
        holder.populate(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ManagerViewEmployeesViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public ManagerViewEmployeesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);

        }

        public void showDetails(View view,SignupDetails signupDetails){
            Log.e("showDetails: ", ""+signupDetails.getId());
            ServiceGenerator.getService().getEmployeesDetailsAvailabilityByID(signupDetails.getId()).enqueue(new Callback<ArrayList<ManagerAvailabilityResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<ManagerAvailabilityResponse>> call, Response<ArrayList<ManagerAvailabilityResponse>> response) {
                    if(response.body().size()>0){
                    message = "Name::\t" + response.body().get(0).getFirstName() + " " + response.body().get(0).getLastName() + "\n"
                            + "Email::\t" + response.body().get(0).getEmail() +
                            "\n __________________________________________\n" +
                            ""+"Weekname"+""+"\t"+ ""+"Start_Time"+""+"\t"+""+"End_time"+""+"\n";

                    for (ManagerAvailabilityResponse manager : response.body()) {
                        message += manager.getWeek() + "\t\t\t" + manager.getShiftStart() + "\t\t\t" + manager.getShiftEnd() + "\n\n";

                    }



                }else{
                        message = "Name::\t" + signupDetails.getFirstName() + " " + signupDetails.getLastName() + "\n"
                                + "Email::\t" + signupDetails.getEmail();
                    }

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage(message);


                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                @Override
                public void onFailure(Call<ArrayList<ManagerAvailabilityResponse>> call, Throwable t) {

                }
            });
        }
        public void populate(SignupDetails signupDetails) {
            name.setText(signupDetails.getFirstName()+" "+signupDetails.getLastName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetails(v,signupDetails);
                }
            });
        }
    }
}
