package com.example.winner10.markteacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Login extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private EditText etPassword;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    StringRes sr;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Reference to variables
        etEmail = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        sr = ((StringRes)getApplicationContext());
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Login.MODE_PRIVATE);

        username = sharedPreferences.getString("username","");
        if(!username.isEmpty()){
            Intent intent = new Intent(Login.this,SuccessActivity.class);
            startActivity(intent);
            Login.this.finish();
        }
    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {

        // Get text from email and passord field
        username = etEmail.getText().toString();
        password = etPassword.getText().toString();

        // Initialize  AsyncLogin() class with email and password
        new AysnchLogin(Login.this,"login.inc.php");

    }

    public void changeHost(View arg0) {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(Login.this);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Login.this);

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
                                Toast.makeText(Login.this,"HOST set successfully!",Toast.LENGTH_LONG).show();
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
//        Intent intent = new Intent(Login.this,SetHost.class);
//        startActivity(intent);

    }

    private class AysnchLogin extends GlobalAsyncTask{

        AysnchLogin(Context context, String url){
            super(context,url);
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("username", username)
                    .appendQueryParameter("password", password);
        }

        @Override
        public void goPostExecute(String result,String content) {

            if(content.equalsIgnoreCase("application/json"))
//            if(result.equalsIgnoreCase("true"))
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.apply();

                Intent intent = new Intent(Login.this,SuccessActivity.class);
                startActivity(intent);
                Login.this.finish();

            }else if (result.equalsIgnoreCase("false")){
                Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_LONG).show();
            }
        }

    }

}
