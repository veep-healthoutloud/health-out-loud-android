package com.veephealthoutloud.healthoutloud;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.veephealthoutloud.healthoutloud.Classes.VolleyRequestsUtils;
import com.veephealthoutloud.healthoutloud.Interfaces.JSONObjectVolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    public Button login;
    public EditText emailEditText;
    public EditText passwordEditText;
    public TextView errorTextView;

    public void init() {
        emailEditText = findViewById(R.id.login_activity_email_edit_text);
        passwordEditText = findViewById(R.id.login_activity_password_edit_text);
        errorTextView = findViewById(R.id.login_activity_error_text_view);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TryLogin();
            }
        });

        Button signupButton = (Button)findViewById(R.id.login_activity_signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void TryLogin(){

        errorTextView.setText("");
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        try {
            VolleyRequestsUtils.login(getApplicationContext(), email, password, new JSONObjectVolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        String token = result.getString("token");
                        SuccessfulLogin(token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError result) {
                    FailedLogin(result);
                }
            });
        } catch (JSONException e) {
            // TODO: print error message on screen if there is a JSON error(different from wrong password)
            e.printStackTrace();
        }
    }

    private void SuccessfulLogin(String token){

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    private void FailedLogin(VolleyError result){
        NetworkResponse networkResponse = result.networkResponse;
        errorTextView.setTextColor(Color.RED);
        if (networkResponse == null){
            errorTextView.setText(R.string.could_not_connect_error);
            return;
        }
        if (networkResponse.statusCode == 401){
            errorTextView.setText(R.string.wrong_login_error);
        }
    }
}
