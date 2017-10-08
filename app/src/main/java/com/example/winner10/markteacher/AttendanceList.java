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
    String stuid, studentid, subid, clid, tid, start, date, status;
    String sap;

    AttendanceList parseObject(JSONObject data) throws JSONException {
        AttendanceList obj = new AttendanceList();

        obj.stuid = data.getString("stuid");
        obj.subid = data.getString("subid");
        obj.clid = data.getString("clid");
        obj.tid = data.getString("tid");
        obj.date = data.getString("tid");
        obj.status = data.getString("status");

        String tstart = data.getString("start");
        obj.start = tstart.substring(0, tstart.length() - 3);

        return obj;
    }

    AttendanceList setStudent(JSONObject data) throws JSONException {
        AttendanceList obj = new AttendanceList();

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
            list.add(setStudent(json_data));
        }

        return list;
    }


}
