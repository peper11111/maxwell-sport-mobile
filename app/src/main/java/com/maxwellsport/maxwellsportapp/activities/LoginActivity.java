package com.maxwellsport.maxwellsportapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.helpers.DataConversionHelper;
import com.maxwellsport.maxwellsportapp.helpers.SharedPreferencesHelper;

import java.util.Locale;

public class LoginActivity extends Activity {
    private Context mContext;
    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        /* Wczytanie języka aplikacji. Domyślny język angielski */
        String language = SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.settings_language_key, "en");
        setLocale(mContext, language);

        /* Wczytanie motywu aplikacji. Domyślny motyw CyanAccentColorTheme */
        int style = SharedPreferencesHelper.getInt(this, SharedPreferencesHelper.settings_theme_key, 7);
        setTheme(DataConversionHelper.convertTheme(style));

        setContentView(R.layout.activity_login);
        mUsernameField = (EditText) findViewById(R.id.username_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);
        mLoginButton = (Button) findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUsernameField.getText().toString().equals("admin") && mPasswordField.getText().toString().equals("admin")) {
                    SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.app_user_id_key, "21232f297a57a5a743894a0e4a801fc3");
                    SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.app_username_key, mUsernameField.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(mContext, R.string.toast_msg_login_error, Toast.LENGTH_SHORT).show();
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
        if (SharedPreferencesHelper.contains(mContext, SharedPreferencesHelper.app_user_id_key)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            mUsernameField.setText(SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.app_username_key, ""));
        }
    }

    public static void setLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
