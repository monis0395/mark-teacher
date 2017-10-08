package com.example.winner10.markteacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
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

public class Login extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    StringRes sr;
    private String username;
    private String password;
    Context self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        self = Login.this;

        etEmail = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        sr = ((StringRes) getApplicationContext());

        UserDetails ud = new UserDetails(self);
        if (ud.islogedIn()) {
            ud.refreshValuesFromSP();
            statSuccessActivity();
        }
    }

    void statSuccessActivity() {
        Intent intent = new Intent(self, SuccessActivity.class);
        startActivity(intent);
        Login.this.finish();
    }

    // Triggers when LOGIN Button clicked
    public void checkLogin(View arg0) {
        username = etEmail.getText().toString();
        password = etPassword.getText().toString();
        new AysnchLogin(self, "login.php");
    }

    public void changeHost(View args0) {
        Util.changeHost(self);
    }

    private class AysnchLogin extends GlobalAsyncTask {

        AysnchLogin(Context context, String url) {
            super(context, url);
            execute();
        }

        @Override
        public Uri.Builder urlBuilder() {
            return new Uri.Builder()
                    .appendQueryParameter("userid", username)
                    .appendQueryParameter("password", password);
        }

        @Override
        public void goPostExecute(String result, String content) {

            if (content.equalsIgnoreCase("application/json")) {
                try {
                    JSONArray jArray = new JSONArray(result);
                    JSONObject userObject = jArray.getJSONObject(0);

                    UserDetails ud = new UserDetails(self);
                    ud.setValues(userObject);
                    ud.setValuesInSP(userObject);
                    Toast.makeText(self, "Login success", Toast.LENGTH_LONG).show();
                    statSuccessActivity();
                } catch (JSONException e) {
                    Toast.makeText(self, e.toString(), Toast.LENGTH_LONG).show();
                }

            } else if (result.equalsIgnoreCase("user not found")) {
                Toast.makeText(self, "Invalid email or password", Toast.LENGTH_LONG).show();
            } else if (result.equalsIgnoreCase("values not set")) {
                Toast.makeText(self, "Values Not Set!", Toast.LENGTH_LONG).show();
            }
        }

    }

}
