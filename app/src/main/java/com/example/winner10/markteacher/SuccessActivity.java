package com.example.winner10.markteacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;


public class SuccessActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private String HOSTNAME;
    private RecyclerView dailyPeriod;
    private AdapterDailyPeriod mAdapter;
    public SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    StringRes sr;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Login.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        sr = ((StringRes)getApplicationContext());

//        Toast.makeText(SuccessActivity.this,"useranme: "+username,Toast.LENGTH_LONG).show();

        new AsyncFetch(SuccessActivity.this,"tdailyPeriod.inc.php");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private class AsyncFetch extends GlobalAsyncTask{

        AsyncFetch(Context context, String url){
            super(context,url);
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("tsap", username);
        }

        @Override
        public void goPostExecute(String result, String content) {

            List<DailyPeriod> data=new ArrayList<>();
            try {

                JSONArray jArray = new JSONArray(result);

                TextView textPeriod;
                textPeriod= (TextView) findViewById(R.id.textPeriod);
                textPeriod.setText("/ "+jArray.length()+"");

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    DailyPeriod periodData = new DailyPeriod();

                    periodData.did = json_data.getString("did");
                    periodData.subjectName= json_data.getString("Subject");
                    periodData.teacherName= json_data.getString("Teacher");

                    String tstart = json_data.getString("Time Start");
                    String tend = json_data.getString("Time End");

                    periodData.startTime= tstart.substring(0,tstart.length()-7);
                    periodData.endTime = tend.substring(0,tend.length()-7);
                    periodData.location = json_data.getString("location");
                    data.add(periodData);
                }

                // Setup and Handover data to recyclerview
                dailyPeriod = (RecyclerView)findViewById(R.id.dailyPeriod);
                mAdapter = new AdapterDailyPeriod(SuccessActivity.this, data);
                dailyPeriod.setAdapter(mAdapter);
                dailyPeriod.setLayoutManager(new LinearLayoutManager(SuccessActivity.this));

            } catch (JSONException e) {
                Toast.makeText(SuccessActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.success, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action


        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(SuccessActivity.this);
            View promptsView = li.inflate(R.layout.prompts, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    SuccessActivity.this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // get user input and set it to result
                                    // edit text
                                    sr.setHOSTNAME(userInput.getText().toString());
                                    Toast.makeText(SuccessActivity.this,"HOST set successfully!",Toast.LENGTH_LONG).show();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
//            Intent intent = new Intent(SuccessActivity.this,SetHost.class);
//            startActivity(intent);


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
