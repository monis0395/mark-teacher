package com.example.winner10.markteacher;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Winner 10 on 4/6/2017.
 */

class AttendanceList {
    String atrid, stuid, studentid, subid, clid, tid, start, date, status;

    public AttendanceList() {}


    public AttendanceList(AttendanceList copy) {
        this.atrid = copy.atrid;
        this.stuid = copy.stuid;
        this.studentid = copy.studentid;
        this.subid = copy.subid;
        this.clid = copy.clid;
        this.tid = copy.tid;
        this.start = copy.start;
        this.date = copy.date;
        this.status = copy.status;
    }

    AttendanceList parseObject(JSONObject data) throws JSONException {
        AttendanceList obj = new AttendanceList();

        obj.atrid = data.getString("atrid");
        obj.stuid = data.getString("stuid");
        obj.studentid = data.getString("studentid");
        obj.subid = data.getString("subid");
        obj.clid = data.getString("clid");
        obj.tid = data.getString("tid");
        obj.start = data.getString("start");
        obj.date = data.getString("date");
        obj.status = data.getString("status");

        return obj;
    }

    List<AttendanceList> parseStudentList(JSONArray jArray) throws JSONException {
        List<AttendanceList> list = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject json_data = jArray.getJSONObject(i);
            list.add(parseObject(json_data));
        }

        return list;
    }

    public String toString(){
        return "studi: " + stuid +
                " studentid: " + studentid +
                " subid: " + subid +
                " clid: " + clid +
                " tid: " + tid +
                " start: " + start +
                " date: " + date +
                " status: " + status;
    }
}
