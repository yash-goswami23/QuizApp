package com.example.onequiz.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.onequiz.models.User;
import com.example.onequiz.databinding.ActivitySignBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class signActivity extends AppCompatActivity {
    ActivitySignBinding binding;
    FirebaseAuth auth;

    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("We're creating new account...");


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, name, referCode;

                email = binding.emailBox.getText().toString();
                pass = binding.passwordBox.getText().toString();
                name = binding.nameBox.getText().toString();
                referCode = binding.referBox.getText().toString();
                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(signActivity.this, "empty", Toast.LENGTH_SHORT).show();
                }else {
                    final User user = new User(name, email, pass, referCode);
                    dialog.show();
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = task.getResult().getUser().getUid();

                                database
                                        .collection("users")
                                        .document(uid)
                                        .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    dialog.dismiss();
                                                    startActivity(new Intent(signActivity.this, MainActivity.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(signActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                dialog.dismiss();
                                Toast.makeText(signActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}