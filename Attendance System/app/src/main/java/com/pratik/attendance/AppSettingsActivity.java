package com.pratik.attendance;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class AppSettingsActivity extends AppCompatActivity {

    Switch btnSwitch;
    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button signOutBtn;
    ImageButton aboutus,faqbtn;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);

        btnSwitch = findViewById(R.id.darkModeOff);

        //Shared Preferences is used in case we exit the app and start it again.

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night", false); //light mode is default mode

        if (nightMODE) {
            btnSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btnSwitch.setText("ON");
        }

        btnSwitch.setOnClickListener(v -> {
            if (nightMODE) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = sharedPreferences.edit();
                editor.putBoolean("night", false);
                btnSwitch.setText("OFF");
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = sharedPreferences.edit();
                editor.putBoolean("night", true);
                btnSwitch.setText("ON");
            }
            editor.apply();
        });

        signOutBtn = findViewById(R.id.exit);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // Sign out

                clearAppData(AppSettingsActivity.this); // Clear app data

                Intent intent = new Intent(AppSettingsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Finish the current activity
            }
        });

        aboutus= findViewById(R.id.aboutImgBtn);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppSettingsActivity.this,AboutUs.class);
                startActivity(intent);
            }
        });

        faqbtn = findViewById(R.id.faqImgbtn);
        faqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AppSettingsActivity.this,FAQ.class);
                startActivity(intent);
            }
        });

        setToolBar();

    }
    private void setToolBar() {

        //ToolBar Code
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        title.setText("Settings");
        subtitle.setVisibility(View.GONE);
        back.setOnClickListener(view -> onBackPressed());
        save.setVisibility(View.GONE);
    }

    private void clearAppData(Context context) {
        try {
            // Clear app data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
            } else {
                String packageName = context.getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear " + packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
