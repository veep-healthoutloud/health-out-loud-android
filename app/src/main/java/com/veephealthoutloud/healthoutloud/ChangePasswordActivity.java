package com.veephealthoutloud.healthoutloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    public void OnCancelChangePassword(View view) {
        onBackPressed();
    }

    public void OnChangePassword(View view) {
        // TODO: Add API call to change password
        onBackPressed();
    }
}
