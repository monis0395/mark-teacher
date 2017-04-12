package com.example.winner10.markteacher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class AdapteAttendance extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<AttendanceList> data= Collections.emptyList();
    AttendanceList current;

    // create constructor to innitilize context and data sent from Login
    public AdapteAttendance(Context context, List<AttendanceList> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_students_at_list, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        AttendanceList current=data.get(position);
        myHolder.textStudent.setText(current.sap);
        String status = current.status;
        if(status.equals("0") || status.equals("1")){
            int statusInt = Integer.parseInt(status);
            if(statusInt == 0){
                status = "Absent";
//                myHolder.textStatus.setTextColor(0xDB7093);
            }
            else{
                status = "Present";
//                myHolder.textStatus.setTextColor(0x98FB98);
            }
        }
        else{
            status = "Absent";
//          myHolder.textStatus.setTextColor(0xDB7093);
        }
        myHolder.textStatus.setText(status);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textStudent;
        TextView textStatus;
        LinearLayout lnrLayout;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textStudent= (TextView) itemView.findViewById(R.id.Student);
            textStatus = (TextView) itemView.findViewById(R.id.status);
        }

    }
}


