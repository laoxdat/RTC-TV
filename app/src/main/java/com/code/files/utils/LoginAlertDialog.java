package com.code.files.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.oxootv.spagreen.R;
import com.code.files.view.LoginChooserActivity;


public class LoginAlertDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "LoginAlertDialog";

    private Context context;
    private Button loginButton;

    public LoginAlertDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_alert_dialog);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginButton) {
            context.startActivity(new Intent(context, LoginChooserActivity.class));
        }
        dismiss();
    }
}
