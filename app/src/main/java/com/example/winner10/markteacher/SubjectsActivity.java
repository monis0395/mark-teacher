package com.example.winner10.markteacher;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;

public class SubjectsActivity extends AppCompatActivity {

    Context self;
    String[] map = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        setTitle("Subjects List");
        self = SubjectsActivity.this;

        new AsyncReportAT(self);
    }

    StringBuilder getHeadersString(List<Report> list) {
        StringBuilder sb = new StringBuilder();
        String subjectHeader = ",";
        String header2 = "";
        String header3 = "studentid,Attended,Total,";
        int i = 0;
        String old = list.get(i).studentid;
        String oldsubject = list.get(i).subname;
        subjectHeader += oldsubject;
        int j=0;
        while (list.get(i).studentid.equals(old)) {
            if (!list.get(i).subname.equals(oldsubject)) {
                subjectHeader += list.get(i).subname + ",,";
            } else {
                subjectHeader += ",,";
            }
            header2 += "," + list.get(i).subteach + ",";
            if (i != 0) {
                header3 += "Attended,Total,";
                map[j++] = list.get(i).subteach;
            }
            old = list.get(i).studentid;
            oldsubject = list.get(i).subname;
            i++;
        }
        sb.append(subjectHeader + "\n");
        sb.append(header2 + "\n");
        sb.append(header3 + "\n");
        return sb;

    }

    StringBuilder getStudentSring(List<Report> list) {
        StringBuilder sb = new StringBuilder();
        String row, old, current;
        old = list.get(0).studentid;
        row = "\n" + list.get(0).studentid + "," + list.get(0).Attended + "," + list.get(0).Total;
        sb.append(row);

        for (int i = 1; i < list.size(); i++) {
            current = list.get(i).studentid;
            if (current.equals(old)) {
                row = "," + list.get(i).Attended + "," + list.get(i).Total;
            } else {
                row = "\n" + list.get(i).studentid + "," + list.get(i).Attended + "," + list.get(i).Total;
            }
            sb.append(row);
            old = list.get(i).studentid;
        }

        return sb;
    }

    public void writeToFile(StringBuilder data) {

        String path = Environment.getExternalStorageDirectory() + File.separator + "mark";
        File folder = new File(path);
        folder.mkdirs();

        File file = new File(folder, "Report.csv");

        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
            Toast.makeText(self, "Report saved successful at " + path, Toast.LENGTH_LONG).show();
            ((Activity) self).finish();

        } catch (IOException e) {
            Util.alertDialog("File write failed" + path, self);
        }
    }

    List<Report> getList(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            Report rp = new Report();
            return rp.parseList(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    public class AsyncReportAT extends GlobalAsyncTask {

        Context context;

        AsyncReportAT(Context context) {
            super(context, "getReport.php");
            this.context = context;
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("clid", "1")
                    .appendQueryParameter("tid", UserDetails.tid);
        }

        @Override
        public void goPostExecute(String result, String content) {

            if (content.equalsIgnoreCase("application/json")) {
                List<Report> list = getList(result);
//        Util.alertDialog(getString(list).toString(),self);
                StringBuilder sb = new StringBuilder();
                sb.append(getHeadersString(list));
                sb.append("\n");
                sb.append(getStudentSring(list));
                sb.append("\n");
                writeToFile(sb);

            } else if (result.equalsIgnoreCase("No records found")) {
                Toast.makeText(self, "No records found", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(self, "Failed", Toast.LENGTH_LONG).show();
            }


        }
    }

}
