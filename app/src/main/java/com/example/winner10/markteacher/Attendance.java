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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Attendance extends AppCompatActivity {

    DailyPeriod currentPeriod;
    Context self;
    String date;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        setTitle("Taking Attendance");

        init();
        new AsyncStartAT(self, "getStudents.php");
    }

    void init() {
        swipeRefresh();
        self = Attendance.this;
        currentPeriod = (DailyPeriod) getIntent().getSerializableExtra("currentPeriod");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
    }

    void swipeRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncStartAT(self, "getATRecords.php");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Util.closeActivityAlert("Are you sure you want to exit from taking attendance?", self);
    }

    public void stopAt(View arg0) {
        Intent intent = new Intent(self, AttendanceCheckList.class);
        intent.putExtra("currentPeriod", currentPeriod);
        startActivity(intent);
        finish();
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
                    .appendQueryParameter("clid", currentPeriod.clid)
                    .appendQueryParameter("subid", currentPeriod.subid)
                    .appendQueryParameter("tid", currentPeriod.tid)
                    .appendQueryParameter("start", currentPeriod.START)
                    .appendQueryParameter("did", currentPeriod.did)
                    .appendQueryParameter("date", date);
        }

        @Override
        public void goPostExecute(String result, String content) {

            if (content.equalsIgnoreCase("application/json")) {
                try {
                    JSONArray jArray = new JSONArray(result);
                    AttendanceList sd = new AttendanceList();

                    AdapterAttendance mAdapter = new AdapterAttendance(self, sd.parseStudentList(jArray));
                    RecyclerView attendanceList = (RecyclerView) findViewById(R.id.studentslist);
                    attendanceList.setAdapter(mAdapter);
                    attendanceList.setLayoutManager(new LinearLayoutManager(self));

                } catch (JSONException e) {
                    Toast.makeText(self, e.toString(), Toast.LENGTH_LONG).show();
                }

            } else if (result.equalsIgnoreCase("No records found")) {
                Toast.makeText(self, "No Students Found", Toast.LENGTH_LONG).show();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
