package com.example.winner10.markteacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class AdapterDailyPeriod extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DailyPeriod> data= Collections.emptyList();
    DailyPeriod current;
    int currentPos=0;
    public static String HOSTNAME;
    public String current_location;
    public String current_subjectName;
    public String current_teacherName;

    // create constructor to innitilize context and data sent from Login
    public AdapterDailyPeriod(Context context, List<DailyPeriod> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        StringRes sr = new StringRes();
        HOSTNAME = context.getString(R.string.hostname);
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_daily, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DailyPeriod current=data.get(position);
        myHolder.textSubjectName.setText(current.subjectName);
        myHolder.textTeacherName.setText(" - " + current.teacherName);
        myHolder.textTime.setText(current.startTime + " - " + current.endTime);
        myHolder.textLocation.setText(current.location);
        current_location = current.location;
        current_subjectName = current.subjectName;
        current_teacherName = current.teacherName;
        final String current_did = current.did;
        final DailyPeriod passsdata = current;
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Your code
                Intent intent = new Intent(context,Attendance.class);
                intent.putExtra("subjectName",passsdata.subjectName);
                intent.putExtra("teacherName",passsdata.teacherName);
                context.startActivity(intent);
            }
        });
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textSubjectName;
        TextView textTeacherName;
        TextView textTime;
        TextView textLocation;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textSubjectName= (TextView) itemView.findViewById(R.id.textSubjectName);
            textTeacherName = (TextView) itemView.findViewById(R.id.textTeacherName);
            textTime = (TextView) itemView.findViewById(R.id.textTime);
            textLocation = (TextView) itemView.findViewById(R.id.textLocation);
        }

    }
}


