package com.example.winner10.markteacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceCheckList extends AppCompatActivity {

    public String subject;
    public String teacher;
    public String column;
    public String table;
    private RecyclerView attendanceList;
    private AdapterAttendanceCheckList mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_check_list);

        subject = getIntent().getStringExtra("subjectName");
        teacher = getIntent().getStringExtra("teacherName");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c.getTime());
        setTitle("Mark Attendance");

        column = date;
        table = subject+"-"+teacher;
        new AsyncATList(AttendanceCheckList.this,"returnAT.inc.php");
    }

    private class AsyncATList extends GlobalAsyncTask{

        AsyncATList(Context context, String url){
            super(context,url);
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("column", column)
                    .appendQueryParameter("table", table);
        }

        @Override
        public void goPostExecute(String result,String content) {

            if(content.equalsIgnoreCase("application/json")) {
                List<AttendanceList> data=new ArrayList<>();
                try {
                    JSONArray jArray = new JSONArray(result);

                    // Extract data from json and store into ArrayList as class objects
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        AttendanceList studentData = new AttendanceList();

                        studentData.sap = json_data.getString("student");
                        studentData.status= json_data.getString("status");
                        data.add(studentData);
                    }

                    // Setup and Handover data to recyclerview
                    attendanceList = (RecyclerView)findViewById(R.id.studentsCheckList);
                    mAdapter = new AdapterAttendanceCheckList(AttendanceCheckList.this, data, column, table);
                    attendanceList.setAdapter(mAdapter);
                    attendanceList.setLayoutManager(new LinearLayoutManager(AttendanceCheckList.this));

                } catch (JSONException e) {
                    Toast.makeText(AttendanceCheckList.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }else if (result.equalsIgnoreCase("false")){
                Toast.makeText(AttendanceCheckList.this, "FALSE", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void deleteAT(View arg0) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        new AsyncDeleteAT(AttendanceCheckList.this,"deleteAT.inc.php");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceCheckList.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private class AsyncDeleteAT extends GlobalAsyncTask{

        AsyncDeleteAT(Context context, String url){
            super(context,url);
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("column", column)
                    .appendQueryParameter("table", table);
        }

        @Override
        public void goPostExecute(String result,String content) {
            Toast.makeText(AttendanceCheckList.this, "Deleted Attendance", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void confirmAt(View arg0) {
        Toast.makeText(AttendanceCheckList.this,"Attendance saved",Toast.LENGTH_LONG).show();
        finish();
    }
}
