package com.example.winner10.markteacher;

/**
 * Created by Winner 10 on 4/24/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdpaterSubject extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Subjects> data= Collections.emptyList();
    Subjects current;
    private RecyclerView attendanceList;
    private AdapterReport mAdapter;

    // create constructor to innitilize context and data sent from MainActivity
    public AdpaterSubject(Context context, List<Subjects> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_subjects, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        final Subjects current = data.get(position);
        myHolder.textSubjectName.setText(current.subjectName+"\n\n"+current.className);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Your code
                Intent intent = new Intent(context,ReportActivity.class);
                intent.putExtra("subjectName",current.subjectName);
                intent.putExtra("className",current.className);
                context.startActivity(intent);

            }
        });
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView textSubjectName;
        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textSubjectName= (TextView) itemView.findViewById(R.id.textSubjectName);
        }
    }



}
