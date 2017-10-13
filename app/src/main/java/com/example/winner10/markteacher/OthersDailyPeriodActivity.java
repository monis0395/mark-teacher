package com.example.winner10.markteacher;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class OthersDailyPeriodActivity extends AppCompatActivity {

    Context self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_daily_period);

        Toolbar  mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("Today");
        setSupportActionBar(mActionBarToolbar);

        self = OthersDailyPeriodActivity.this;

        new AsyncFetch(self, "daily.php");
    }


    private class AsyncFetch extends GlobalAsyncTask {

        AsyncFetch(Context context, String url) {
            super(context, url);
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("cid", UserDetails.cid)
                    .appendQueryParameter("ntid", UserDetails.tid);
        }

        @Override
        public void goPostExecute(String result, String content) {

            try {
                JSONArray jArray = new JSONArray(result);

                DailyPeriod dp = new DailyPeriod();

                AdapterDailyPeriod mAdapter = new AdapterDailyPeriod(self, dp.parseList(jArray));
                RecyclerView dailyPeriodRecycler = (RecyclerView) findViewById(R.id.othersDailyPeriod);
                dailyPeriodRecycler.setAdapter(mAdapter);
                dailyPeriodRecycler.setLayoutManager(new LinearLayoutManager(self));

            } catch (JSONException e) {
                Toast.makeText(self, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }

}
