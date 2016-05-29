package com.maxwellsport.maxwellsportapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.services.LocaleService;
import com.maxwellsport.maxwellsportapp.services.SharedPreferencesService;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Wymuszenie jezyka angielskiego */
        LocaleService.setLocale(this, "pl");

        setContentView(R.layout.activity_login);

        final EditText usernameField = (EditText) findViewById(R.id.username_field);
        final EditText passwordField = (EditText) findViewById(R.id.password_field);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameField.getText().toString().equals("admin") && passwordField.getText().toString().equals("admin")) {
                    SharedPreferencesService.saveValue(getApplication(), "userID", "21232f297a57a5a743894a0e4a801fc3");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Authentication error", Toast.LENGTH_LONG).show();
                }
            }
        });

        /* Jesli SDK powyżej lollipop to pokoloruj status bar */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences(SharedPreferencesService.TAG, MODE_PRIVATE);
        if (prefs.getString("userID", null) != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
