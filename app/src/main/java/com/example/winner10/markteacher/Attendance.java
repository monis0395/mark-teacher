package com.example.winner10.markteacher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Locale;

public class Attendance extends AppCompatActivity {

    DailyPeriod currentPeriod;
    String date;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        setTitle("Taking Attendance");
        swipeRefresh();

        currentPeriod = (DailyPeriod) getIntent().getSerializableExtra("currentPeriod");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());

        new AsyncStartAT(Attendance.this, "getStudents.php");
    }

    void swipeRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncRefreshATList(Attendance.this, "getATRecords.php");
            }
        });
    }

    public void startAt(View arg0) {
        new AsyncStartAT(Attendance.this, "getATRecords.php");
    }

    public void stopAt(View arg0) {
        Intent intent = new Intent(Attendance.this, AttendanceCheckList.class);
        intent.putExtra("currentPeriod", currentPeriod);
        startActivity(intent);
        finish();
//        Toast.makeText(Attendance.this,"Attendance saved",Toast.LENGTH_LONG).show();
//        finish();
    }

    private class AsyncStartAT extends GlobalAsyncTask {

        AsyncStartAT(Context context, String url) {
            super(context, url);
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("batchid", currentPeriod.batchid)
                    .appendQueryParameter("clid", currentPeriod.clid);
        }

        @Override
        public void goPostExecute(String result, String content) {

            if (content.equalsIgnoreCase("application/json")) {
                List<AttendanceList> data = new ArrayList<>();
                try {
                    JSONArray jArray = new JSONArray(result);

                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        AttendanceList studentData = new AttendanceList();

                        studentData.sap = json_data.getString("student");
                        studentData.status = json_data.getString("status");
                        data.add(studentData);
                    }

                    // Setup and Handover data to recyclerview
                    RecyclerView attendanceList = (RecyclerView) findViewById(R.id.studentslist);
                    AdapteAttendance mAdapter = new AdapteAttendance(Attendance.this, data);
                    attendanceList.setAdapter(mAdapter);
                    attendanceList.setLayoutManager(new LinearLayoutManager(Attendance.this));

                } catch (JSONException e) {
                    Toast.makeText(Attendance.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            } else if (result.equalsIgnoreCase("false")) {
                Toast.makeText(Attendance.this, "FALSE", Toast.LENGTH_LONG).show();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private class AsyncRefreshATList extends GlobalAsyncTask {

        AsyncRefreshATList(Context context, String url) {
            super(context, url);
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("clid", currentPeriod.clid)
                    .appendQueryParameter("subid", currentPeriod.subid)
                    .appendQueryParameter("tid", currentPeriod.tid)
                    .appendQueryParameter("date", date);
        }

        @Override
        public void goPostExecute(String result, String content) {

            if (content.equalsIgnoreCase("application/json")) {
                List<AttendanceList> data = new ArrayList<>();
                try {
                    JSONArray jArray = new JSONArray(result);

                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        AttendanceList studentData = new AttendanceList();

                        studentData.sap = json_data.getString("student");
                        studentData.status = json_data.getString("status");
                        data.add(studentData);
                    }

                    // Setup and Handover data to recyclerview
                    RecyclerView attendanceList = (RecyclerView) findViewById(R.id.studentslist);
                    AdapteAttendance mAdapter = new AdapteAttendance(Attendance.this, data);
                    attendanceList.setAdapter(mAdapter);
                    attendanceList.setLayoutManager(new LinearLayoutManager(Attendance.this));

                } catch (JSONException e) {
                    Toast.makeText(Attendance.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            } else if (result.equalsIgnoreCase("false")) {
                Toast.makeText(Attendance.this, "FALSE", Toast.LENGTH_LONG).show();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
