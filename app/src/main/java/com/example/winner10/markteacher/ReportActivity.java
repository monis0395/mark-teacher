package com.example.winner10.markteacher;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportActivity extends AppCompatActivity {


    private String subjectColumn;
    private String table;
    RecyclerView reportList;
    AdapterReport  mAdapter;
    StringRes sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        subjectColumn = getIntent().getStringExtra("subjectName");
        table = getIntent().getStringExtra("className");
        setTitle("Report List");
//        Toast.makeText(ReportActivity.this,"subjectColumn "+subjectColumn, Toast.LENGTH_SHORT).show();
        new AsyncReport(ReportActivity.this,"report.inc.php");
    }
    private class AsyncReport extends GlobalAsyncTask{

        AsyncReport(Context context, String url){
            super(context,url);
            execute();

        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("column", subjectColumn)
                    .appendQueryParameter("table", table);
//            return new Uri.Builder()
//                    .appendQueryParameter("username", "60003130014")
//                    .appendQueryParameter("password", "60003130014");
        }

        @Override
        public void goPostExecute(String result,String content) {

            if(content.equalsIgnoreCase("application/json")) {
                List<Report> data=new ArrayList<>();
                try {
                    JSONArray jArray = new JSONArray(result);

                    // Extract data from json and store into ArrayList as class objects
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Report studentData = new Report();

                        studentData.student = json_data.getString("student");
//                        studentData.la = Integer.parseInt(json_data.getString("la"));
//                        studentData.lt= Integer.parseInt(json_data.getString("lt"));

                        data.add(studentData);
                    }

                    // Setup and Handover data to recyclerview
                    reportList = (RecyclerView)findViewById(R.id.reportList);
                    mAdapter = new AdapterReport(ReportActivity.this, data);
                    reportList.setAdapter(mAdapter);
                    reportList.setLayoutManager(new LinearLayoutManager(ReportActivity.this));

                } catch (JSONException e) {
                    Toast.makeText(ReportActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }else if (result.equalsIgnoreCase("false")){
                Toast.makeText(ReportActivity.this, "FALSE", Toast.LENGTH_LONG).show();
            }
        }
    }
}
