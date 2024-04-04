package com.example.onequiz.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.onequiz.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class resultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    int POINTS = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswers = getIntent().getIntExtra("correct", 0);
        int totalQuestions = getIntent().getIntExtra("total", 0);

        long points = (long) correctAnswers * POINTS;
        String Score = String.format("%d/%d",correctAnswers, totalQuestions);
        String Point = String.valueOf(points);
        binding.score.setText(Score);
        binding.earnedCoins.setText(Point);

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

        binding.restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(resultActivity.this, MainActivity.class));
                finishAffinity();
            }
        });
        binding.shareBtn.setOnClickListener(v->{
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Score : "+Score + "\n" + "Points : " +Point );
            startActivity(Intent.createChooser(shareIntent, "Share link using"));
        });

    }
}