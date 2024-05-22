package com.pratik.attendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import com.pratik.attendance.shareSheet;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pratik.attendance.databinding.SheetListBinding;


public class FullscreenActivity extends AppCompatActivity {

    ImageButton enrollbtn,shareSheetnow,appSettings;
    ImageButton btnTakeAttendance;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        enrollbtn = (ImageButton) findViewById(R.id.btnEnrollStd);
        btnTakeAttendance = (ImageButton) findViewById(R.id.btnTakeAttendance);

        enrollbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FullscreenActivity.this, enroll.class);
                startActivity(intent);
            }
        });


        btnTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FullscreenActivity.this, enroll.class);
                startActivity(intent);
            }
        });

       // App Settings Button----------------------------------
        appSettings=findViewById(R.id.btnAppSettings);
        appSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FullscreenActivity.this,AppSettingsActivity.class);
                startActivity(intent);
            }
        });
        // App Settings Button----------------------------------

        //Generate Sheet Button---------------------------------
        shareSheetnow=findViewById(R.id.btnGenSheet);
        shareSheetnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             shareSheet.openFolderInFileManager(FullscreenActivity.this);

            }
        });

        //Generate Sheet Button---------------------------------


    }
}