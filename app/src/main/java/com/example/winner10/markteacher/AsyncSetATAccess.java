package com.example.winner10.markteacher;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by monis.q on 09-10-2017.
 */

public class AsyncSetATAccess extends GlobalAsyncTask {
    DailyPeriod currentPeriod;
    Context context;
    String access;

    AsyncSetATAccess(Context context, DailyPeriod currentPeriod, String access) {
        super(context, "setATAccess.php");
        this.context = context;
        this.currentPeriod = currentPeriod;
        this.access = access;
        execute();
    }

    @Override
    public Uri.Builder urlBuilder() {
        return new Uri.Builder()
                .appendQueryParameter("did", currentPeriod.did)
                .appendQueryParameter("access", access);
    }

    @Override
    public void goPostExecute(String result, String content) {
        String status = access.equalsIgnoreCase("0") ? "revok" : "grant";

        if (result.equalsIgnoreCase("success")) {
            Toast.makeText(context, "Students attendance access " + status, Toast.LENGTH_SHORT).show();
        } else if (result.equalsIgnoreCase("failed")) {
            Toast.makeText(context, "Students attendance access " + status, Toast.LENGTH_SHORT).show();
        }
    }
}
