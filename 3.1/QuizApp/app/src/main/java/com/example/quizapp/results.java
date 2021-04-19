package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class results extends AppCompatActivity {
    TextView txtCongratulations, txtScoreVal;
    String name, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        score = intent.getStringExtra("score");

        txtCongratulations = findViewById(R.id.txtCongratulations);
        txtScoreVal = findViewById(R.id.txtScoreVal);

        txtCongratulations.setText("Congratulations " + name + "!" );
        txtScoreVal.setText(score + "/5");
    }

    public void newQuiz(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}