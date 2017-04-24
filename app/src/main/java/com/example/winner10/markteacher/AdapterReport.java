package com.example.winner10.markteacher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Winner 10 on 4/24/2017.
 */

public class AdapterReport extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Report> data= Collections.emptyList();
    Report current;
    private RecyclerView attendanceList;
    private AdapterAttendanceCheckList mAdapter;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterReport(Context context, List<Report> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_report, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);

        myHolder.textSutdent.setText(current.student+"    "+current.la+"    "+current.lt+"    "+current.need+"    "+current.bunk);
//        myHolder.textLA.setText(current.la);
//        myHolder.textLT.setText(current.lt);
//        myHolder.textNeed.setText(current.need);
//        myHolder.textBunk.setText(current.bunk);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView textSutdent;
        TextView textLA;
        TextView textLT;
        TextView textNeed;
        TextView textBunk;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textSutdent= (TextView) itemView.findViewById(R.id.textSutdent);
            textLA= (TextView) itemView.findViewById(R.id.textLA);
            textLT= (TextView) itemView.findViewById(R.id.textLT);
            textNeed= (TextView) itemView.findViewById(R.id.textNeed);
            textBunk= (TextView) itemView.findViewById(R.id.textBunk);
        }
    }
}
