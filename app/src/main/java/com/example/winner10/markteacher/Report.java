package com.example.winner10.markteacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Winner 10 on 4/24/2017.
 */

public class Report {
    String student,la, lt, need, bunk;
    String studentid,subname,subteach,Attended,Total;

    Report parseObject(JSONObject obj) throws JSONException {
        Report rp = new Report();

        rp.studentid = obj.getString("studentid");
        rp.subname = obj.getString("subname");
        rp.subteach = obj.getString("subteach");
        rp.Attended = obj.getString("Attended");
        rp.Total = obj.getString("Total");

        return rp;
    }

    List<Report> parseList(JSONArray jArray) throws JSONException {
        List<Report> list = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject json_data = jArray.getJSONObject(i);
            list.add(parseObject(json_data));
        }
        return list;
    }

    public String toString(){
        return studentid + "," + Attended+ "," + Total;
    }

}
