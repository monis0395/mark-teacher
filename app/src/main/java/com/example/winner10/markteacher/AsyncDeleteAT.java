package com.example.winner10.markteacher;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by monis.q on 09-10-2017.
 */

public class AsyncDeleteAT extends GlobalAsyncTask {

    DailyPeriod currentPeriod;
    Context context;
    String date;

    AsyncDeleteAT(Context context, DailyPeriod currentPeriod, String date) {
        super(context, "deleteAT.php");
        this.context = context;
        this.currentPeriod = currentPeriod;
        this.date = date;
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
                .appendQueryParameter("date", date);
    }

    @Override
    public void goPostExecute(String result, String content) {

        if (result.equalsIgnoreCase("success")) {
            Toast.makeText(context, "Attendance deleted", Toast.LENGTH_LONG).show();
        } else if (result.equalsIgnoreCase("failed")) {
            Toast.makeText(context, "Failed to delete Attendance", Toast.LENGTH_LONG).show();
        }
        ((Activity) context).finish();

    }
}
