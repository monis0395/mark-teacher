package com.example.winner10.markteacher;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by monis.q on 08-10-2017.
 */

public class UserDetails {
    public static String uid, tid, teacherid, tname, cname, cid;
    Context context;

    UserDetails(Context context) {
        this.context = context;
    }

    void setValues(JSONObject obj) {
        try {
            uid = obj.getString("uid");
            tid = obj.getString("tid");
            teacherid = obj.getString("teacherid");
            tname = obj.getString("tname");
            cname = obj.getString("cname");
            cid = obj.getString("cid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void refreshValuesFromSP() {
        SharedPreferences sp = context.getSharedPreferences("MarkUserDetails", Context.MODE_PRIVATE);
        String userDetails = sp.getString("useDetails","");
        try {
            JSONObject obj = new JSONObject(userDetails);
            setValues(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setValuesInSP(JSONObject obj) {
        SharedPreferences sp = context.getSharedPreferences("MarkUserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("useDetails", obj.toString());
        editor.apply();
    }

    boolean islogedIn(){
        SharedPreferences sp = context.getSharedPreferences("MarkUserDetails", Context.MODE_PRIVATE);
        String userDetails = sp.getString("useDetails","");

        return !userDetails.isEmpty();
    }
    void clearDetails(){
        SharedPreferences sp = context.getSharedPreferences("MarkUserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
