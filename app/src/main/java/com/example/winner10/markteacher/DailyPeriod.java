package com.example.winner10.markteacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Winner 10 on 4/6/2017.
 */

class DailyPeriod {
    String did, clid, subid, tid, subname, tname, subtype, START, END, location, batchid, cname, subsemester, cid, access;

    /*{
        "did": "16",
            "clid": "1",
            "subid": "1",
            "tid": "1",
            "subname": "ENTERPRISE RESOURCE PLANNING",
            "tname": "Arjun Jaiswal",
            "subtype": "lecture",
            "START": "11:00:00",
            "END": "12:00:00",
            "location": "61",
            "batchid": "0",
            "cname": "Information Technology",
            "subsemester": "8",
            "cid": "1",
            "access": "0"
    }*/

    DailyPeriod parseObject(JSONObject data) throws JSONException {
        DailyPeriod period = new DailyPeriod();

        period.did = data.getString("did");
        period.clid = data.getString("clid");
        period.subid = data.getString("subid");
        period.tid = data.getString("tid");
        period.subname = data.getString("subname");
        period.tname = data.getString("tname");
        period.subtype = data.getString("subtype");
        period.location = data.getString("location");
        period.batchid = data.getString("batchid");
        period.cname = data.getString("cname");
        period.subsemester = data.getString("subsemester");
        period.cid = data.getString("cid");
        period.access = data.getString("access");

        String tstart = data.getString("START");
        String tend = data.getString("END");
        period.START = tstart.substring(0, tstart.length() - 3);
        period.END = tend.substring(0, tend.length() - 3);
        return period;
    }

    List<DailyPeriod> parseList(JSONArray jArray) throws JSONException {
        List<DailyPeriod> list = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject json_data = jArray.getJSONObject(i);
            list.add(parseObject(json_data));
        }

        return list;
    }

}
