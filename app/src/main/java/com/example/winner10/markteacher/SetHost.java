package com.example.winner10.markteacher;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetHost extends AppCompatActivity {


    private EditText hostname;
    private String HOSTNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_host);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        hostname = (EditText) findViewById(R.id.hostText);

    }

    public void setHost(View arg0) {
        // Get text from email and passord field
        HOSTNAME = hostname.getText().toString();
        StringRes sr = ((StringRes)getApplicationContext());
        sr.setHOSTNAME(HOSTNAME);
        Toast.makeText(SetHost.this,"Set HOST as "+sr.getHOSTNAME(),Toast.LENGTH_LONG).show();
        finish();
    }

}
