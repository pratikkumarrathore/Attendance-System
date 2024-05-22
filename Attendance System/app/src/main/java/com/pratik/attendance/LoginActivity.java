package com.pratik.attendance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pratik.attendance.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
   ProgressDialog ProgressDialog;

   public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Gradient Start
        LinearLayout rootLayout = findViewById(R.id.rootLayout); // Assuming "rootLayout" is the id of your LinearLayout in the XML layout file
        AnimationDrawable animDrawable = (AnimationDrawable) rootLayout.getBackground();

        animDrawable.setEnterFadeDuration(10);
        animDrawable.setExitFadeDuration(5000);
        animDrawable.start();


        //Gradient End

        //auto login
        checkBox();

        firebaseAuth=firebaseAuth.getInstance();
        ProgressDialog=new ProgressDialog(this);


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=binding.loginEmail.getText().toString().trim();
                String password=binding.loginPassword.getText().toString().trim();
                ProgressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(Email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                ProgressDialog.cancel();
                                //auto login
                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name","true");
                                editor.apply();

                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,FullscreenActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                ProgressDialog.cancel();
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void checkBox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String check = sharedPreferences.getString("name","");
        if (check.equals("true"))
        {
            Intent intent = new Intent(LoginActivity.this,FullscreenActivity.class);
            startActivity(intent);
            finish();
        }
    }
}