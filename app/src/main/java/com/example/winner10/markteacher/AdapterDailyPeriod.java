package com.example.winner10.markteacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
    List<DailyPeriod> data = Collections.emptyList();

    public AdapterDailyPeriod(Context context, List<DailyPeriod> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.data = data;
        StringRes sr = new StringRes();
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_daily, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyHolder myHolder = (MyHolder) holder;
        final DailyPeriod current = data.get(position);

        myHolder.textSubjectName.setText(current.subname);
        myHolder.textTeacherName.setText(" - " + current.tname);
        myHolder.textTime.setText(current.START + " - " + current.END);
        myHolder.textLocation.setText(current.location);

        if(!current.access.equalsIgnoreCase("2")){
            myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Attendance.class);
                    intent.putExtra("currentPeriod", current);
                    context.startActivity(intent);
                }
            });
        }else {
            myHolder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.LightGrey));
        }

    }

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
            textSubjectName = (TextView) itemView.findViewById(R.id.textSubjectName);
            textTeacherName = (TextView) itemView.findViewById(R.id.textTeacherName);
            textTime = (TextView) itemView.findViewById(R.id.textTime);
            textLocation = (TextView) itemView.findViewById(R.id.textLocation);
        }

    }
}


