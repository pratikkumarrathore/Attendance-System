package com.pratik.attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pratik.attendance.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Gradient Start
        LinearLayout rootLayout = findViewById(R.id.rootLayout); // Assuming "rootLayout" is the id of your LinearLayout in the XML layout file
        AnimationDrawable animDrawable = (AnimationDrawable) rootLayout.getBackground();

        animDrawable.setEnterFadeDuration(10);
        animDrawable.setExitFadeDuration(5000);
        animDrawable.start();


        //Gradient End

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String Email=binding.signupEmail.getText().toString().trim();
            String password=binding.signupPassword.getText().toString();
            progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(Email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                            progressDialog.cancel();
                            firebaseFirestore.collection("User")
                                    .document(firebaseAuth.getInstance().getUid())
                                    .set(new UserModel(Email));

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    });


            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

    }
}