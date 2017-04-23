package com.example.winner10.markteacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Attendance extends AppCompatActivity {

    public String subject;
    public String teacher;
    public String column;
    public String table;
    private RecyclerView attendanceList;
    private AdapteAttendance mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncATList(Attendance.this,"returnAT.inc.php");
            }
        });

        subject = getIntent().getStringExtra("subjectName");
        teacher = getIntent().getStringExtra("teacherName");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c.getTime());
        setTitle("Taking Attendance");

        column = date;
        table = subject+"-"+teacher;
        new AsyncStart(Attendance.this,"startAT.inc.php");
    }

    // Triggers when LOGIN Button clicked
    public void startAt(View arg0) {
        new AsyncStart(Attendance.this,"startAT.inc.php");
    }

    public void stopAt(View arg0) {
        Intent intent = new Intent(Attendance.this,AttendanceCheckList.class);
        intent.putExtra("subjectName",subject);
        intent.putExtra("teacherName",teacher);
        startActivity(intent);
        finish();
//        Toast.makeText(Attendance.this,"Attendance saved",Toast.LENGTH_LONG).show();
//        finish();
    }

    private class AsyncStart extends GlobalAsyncTask{

        AsyncStart(Context context, String url){
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

            if(result.equalsIgnoreCase("true")) {
                new AsyncATList(Attendance.this,"returnAT.inc.php");

            }else if (result.equalsIgnoreCase("false")){
                Toast.makeText(Attendance.this, "FALSE", Toast.LENGTH_LONG).show();
            }
        }
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
                    attendanceList = (RecyclerView)findViewById(R.id.studentslist);
                    mAdapter = new AdapteAttendance(Attendance.this, data);
                    attendanceList.setAdapter(mAdapter);
                    attendanceList.setLayoutManager(new LinearLayoutManager(Attendance.this));

                } catch (JSONException e) {
                    Toast.makeText(Attendance.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }else if (result.equalsIgnoreCase("false")){
                Toast.makeText(Attendance.this, "FALSE", Toast.LENGTH_LONG).show();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
