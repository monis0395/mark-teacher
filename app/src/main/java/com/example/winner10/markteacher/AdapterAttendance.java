package com.example.winner10.markteacher;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

class AdapterAttendance extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    List<AttendanceList> data = Collections.emptyList();
    AttendanceList current;
    Context context;

    AdapterAttendance(Context context, List<AttendanceList> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_students_at_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyHolder myHolder = (MyHolder) holder;
        AttendanceList current = data.get(position);
        myHolder.textStudent.setText(current.studentid);
        String status = current.status;

        if (status.equals("0")) {
            status = "Absent";
            myHolder.textStatus.setText(status);
            myHolder.textStatus.setTextColor(ContextCompat.getColor(context, R.color.Crimson));
        } else if (status.equals("1")) {
            status = "Present";
            myHolder.textStatus.setText(status);
            myHolder.textStatus.setTextColor(ContextCompat.getColor(context, R.color.MediumSeaGreen));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textStudent;
        TextView textStatus;

        MyHolder(View itemView) {
            super(itemView);
            textStudent = (TextView) itemView.findViewById(R.id.Student);
            textStatus = (TextView) itemView.findViewById(R.id.status);
        }

    }
}


