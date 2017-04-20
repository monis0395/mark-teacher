package com.example.winner10.markteacher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterAttendanceCheckList extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private LayoutInflater inflater;
    List<AttendanceList> data= Collections.emptyList();
    AttendanceList current;
    private String column, table;

    public AdapterAttendanceCheckList(Context context, List<AttendanceList> data, String column, String table){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.column = column;
        this.table = table;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_students_check_list, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder= (MyHolder) holder;
        AttendanceList current=data.get(position);
        myHolder.student.setText(current.sap);
        String status = current.status;
        boolean check_status = false;
        if(status.equals("0") || status.equals("1")){
            int statusInt = Integer.parseInt(status);
            if(statusInt == 0){
                check_status = false;
            }
            else{
                check_status = true;
            }
        }
        else{
            check_status = false;
        }
        final String student = current.sap;
        myHolder.student.setChecked(check_status);
        myHolder.student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    Toast.makeText(context,"Checked",Toast.LENGTH_LONG).show();
                    new AsyncMarkATList(context,"markAT.inc.php",student,"1");
                }else
//                    Toast.makeText(context,"Unchecked",Toast.LENGTH_LONG).show();
                    new AsyncMarkATList(context,"markAT.inc.php",student,"0");
            }
        });
    }

    private class AsyncMarkATList extends GlobalAsyncTask{
        String student,value;

        AsyncMarkATList(Context context, String url,String student, String value){
            super(context,url);
            execute();
            this.student = student;
            this.value = value;
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("column", column)
                    .appendQueryParameter("table", table)
                    .appendQueryParameter("student", student)
                    .appendQueryParameter("value", value);
        }

        @Override
        public void goPostExecute(String result,String content) {

            if(result.equalsIgnoreCase("1")) {
                Toast.makeText(context, "Marked Present", Toast.LENGTH_LONG).show();
            }else if (result.equalsIgnoreCase("0")){
                Toast.makeText(context, "Marked Absent", Toast.LENGTH_LONG).show();
            }
        }
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        CheckBox student;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            student= (CheckBox) itemView.findViewById(R.id.student_checkbox);
        }

    }
}
