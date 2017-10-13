package com.example.winner10.markteacher;

import android.app.Activity;
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
import java.util.Locale;

public class AttendanceCheckList extends AppCompatActivity {

    DailyPeriod currentPeriod;
    Context self;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_check_list);
        setTitle("Mark Attendance");
        init();

        new AsyncSetATAccess(self, currentPeriod, "0");
        new AsyncATList(self, "getATRecords.php");
    }

    void init() {
        self = AttendanceCheckList.this;
        currentPeriod = (DailyPeriod) getIntent().getSerializableExtra("currentPeriod");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
    }

    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(self)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to exit from taking attendance?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncDeleteAT(self, currentPeriod, date);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private class AsyncATList extends GlobalAsyncTask {

        AsyncATList(Context context, String url) {
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

                    AdapterAttendanceCheckList mAdapter = new AdapterAttendanceCheckList(self, sd.parseStudentList(jArray));
                    RecyclerView attendanceList = (RecyclerView) findViewById(R.id.studentsCheckList);
                    attendanceList.setAdapter(mAdapter);
                    attendanceList.setLayoutManager(new LinearLayoutManager(AttendanceCheckList.this));

                } catch (JSONException e) {
                    Toast.makeText(AttendanceCheckList.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            } else if (result.equalsIgnoreCase("false")) {
                Toast.makeText(AttendanceCheckList.this, "FALSE", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void confirmAt(View arg0) {
        Util.closeActivityAlert("Are you sure you want to submit attendance?", self);
    }
}
