package com.veephealthoutloud.healthoutloud;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.veephealthoutloud.healthoutloud.Classes.VolleyRequestsUtils;
import com.veephealthoutloud.healthoutloud.Interfaces.JSONObjectVolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    public Button signUp;
    public EditText emailEditText;
    public EditText passwordEditText;
    public EditText secondPasswordEditText;
    public TextView errorTextView;

    public void init() {
        emailEditText = findViewById(R.id.signup_activity_email_address);
        passwordEditText = findViewById(R.id.signup_activity_password);
        secondPasswordEditText = findViewById(R.id.signup_activity_password_again);
        errorTextView = findViewById(R.id.signup_activity_error_text_view);

        signUp = findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrySignUp();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    private void TrySignUp(){
        errorTextView.setText("");
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String secondPassword = secondPasswordEditText.getText().toString();

        if (!password.equals(secondPassword)) {
            errorTextView.setTextColor(Color.RED);
            errorTextView.setText("Passwords do not match");
            return;
        }

        try {
            VolleyRequestsUtils.register(getApplicationContext(), email, password, new JSONObjectVolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        String clientID = result.getString("clientID");
                        String verificationCode = result.getString("verificationCode");
                        SuccessfulSignUp(clientID, verificationCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError result) {
                    FailedSignUp(result);
                }
            });
        } catch (JSONException e) {
            // TODO: print error message on screen if there is a JSON error(different from wrong password)
            e.printStackTrace();
        }
    }

    private void SuccessfulSignUp(String clientID, String verificationCode){
        //TODO: Send Email verification using clientID and verificationCode

        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        intent.putExtra("clientID", clientID);
        intent.putExtra("verificationCode", verificationCode);
        startActivity(intent);
    }

    private void FailedSignUp(VolleyError result){
        NetworkResponse networkResponse = result.networkResponse;
        errorTextView.setTextColor(Color.RED);
        if (networkResponse == null){
            errorTextView.setText(R.string.could_not_connect_error);
            return;
        }
        if (networkResponse.statusCode == 400){
            String errorMsgJson = new String(networkResponse.data);
            String errorMsg;
            try {
                JSONObject obj = new JSONObject(errorMsgJson);
                errorMsg = obj.getString("message");
            } catch (Throwable tx) {
                errorMsg = "Invalid Credentials"; //Default message in case of malformed json
            }

            errorTextView.setText(errorMsg);
        }
    }
}
