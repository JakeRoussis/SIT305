package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class quiz extends AppCompatActivity {
    // Declare all variables
    TextView txtHeader, txtQuestionTitle, txtQuestionDescription, txtProgress;
    Button btnAns1, btnAns2, btnAns3, btnNext;
    ProgressBar progressBar;
    String name;

    int selected = 0;
    String[] questionTitle, questionDescription;
    String[][] options;
    int[] answers = {2, 3, 1, 2, 2};
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Set-up text items
        txtHeader = findViewById(R.id.txtHeader);
        txtQuestionDescription = findViewById(R.id.txtQuestionDescription);
        txtQuestionTitle = findViewById(R.id.txtQuestionTitle);
        txtProgress = findViewById(R.id.txtProgress);

        // Set-up Arrays
        questionTitle = getResources().getStringArray(R.array.questionTitle);
        questionDescription = getResources().getStringArray(R.array.questionDescription);

        // Set-up buttons
        btnAns1 = findViewById(R.id.btnAns1);
        btnAns2 = findViewById(R.id.btnAns2);
        btnAns3 = findViewById(R.id.btnAns3);
        btnNext = findViewById(R.id.btnNext);
        btnAns1.setBackgroundColor(Color.BLUE);
        btnAns2.setBackgroundColor(Color.BLUE);
        btnAns3.setBackgroundColor(Color.BLUE);

        // Get intent
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        // Initialize Progress bar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(1);
        txtProgress.setText(progressBar.getProgress() + "/" + progressBar.getMax());

        // Get array of answers
        TypedArray optionsArray = getResources().obtainTypedArray(R.array.options);
        options = new String[optionsArray.length()][3];
        for (int i = 0; i < optionsArray.length(); ++i) {
            int id = optionsArray.getResourceId(i, 0);
            if (id == 0) Toast.makeText(this, "No answers available!", Toast.LENGTH_SHORT);
            options[i] = getResources().getStringArray(id);
        }
        optionsArray.recycle();

        // Show welcome message
        txtHeader.setText("Welcome " + name + "!");   // Welcome text

        // Set-up first question
        txtQuestionTitle.setText(questionTitle[0]);
        txtQuestionDescription.setText(questionDescription[progressBar.getProgress() - 1]);

        btnAns1.setText(options[progressBar.getProgress()-1][0]);
        btnAns2.setText(options[progressBar.getProgress()-1][1]);
        btnAns3.setText(options[progressBar.getProgress()-1][2]);
    }

    public void btnSelect(View view)
    {
        switch (view.getId()) {
            case R.id.btnAns1:
                btnAns1.setBackgroundColor(Color.YELLOW);
                btnAns2.setBackgroundColor(Color.DKGRAY);
                btnAns3.setBackgroundColor(Color.DKGRAY);
                selected = 1;
                break;
            case R.id.btnAns2:
                btnAns1.setBackgroundColor(Color.DKGRAY);
                btnAns2.setBackgroundColor(Color.YELLOW);
                btnAns3.setBackgroundColor(Color.DKGRAY);
                selected = 2;
                break;
            case R.id.btnAns3:
                btnAns1.setBackgroundColor(Color.DKGRAY);
                btnAns2.setBackgroundColor(Color.DKGRAY);
                btnAns3.setBackgroundColor(Color.YELLOW);
                selected = 3;
                break;
        }
    }

    public void btnSetAnswer()
    {
        int correctAnswer = answers[progressBar.getProgress() -1];
        if (selected == correctAnswer) score++;
        switch (correctAnswer) {
            case 1:
                btnAns1.setBackgroundColor(Color.GREEN);
                btnAns2.setBackgroundColor(Color.RED);
                btnAns3.setBackgroundColor(Color.RED);
                break;
            case 2:
                btnAns1.setBackgroundColor(Color.RED);
                btnAns2.setBackgroundColor(Color.GREEN);
                btnAns3.setBackgroundColor(Color.RED);
                break;
            case 3:
                btnAns1.setBackgroundColor(Color.RED);
                btnAns2.setBackgroundColor(Color.RED);
                btnAns3.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    private void updateOptions() {
        progressBar.setProgress(progressBar.getProgress() + 1);
        txtProgress.setText(progressBar.getProgress() + "/" + progressBar.getMax());

        txtQuestionTitle.setText(questionTitle[progressBar.getProgress() - 1]);
        txtQuestionDescription.setText(questionDescription[progressBar.getProgress() - 1]);

        btnAns1.setText(options[progressBar.getProgress() - 1][0]);
        btnAns2.setText(options[progressBar.getProgress() - 1][1]);
        btnAns3.setText(options[progressBar.getProgress() - 1][2]);

        btnAns1.setClickable(true);
        btnAns2.setClickable(true);
        btnAns3.setClickable(true);
        btnAns1.setBackgroundColor(Color.BLUE);
        btnAns2.setBackgroundColor(Color.BLUE);
        btnAns3.setBackgroundColor(Color.BLUE);
        btnNext.setText("Submit");
        selected = 0;
    }

    public void submitAnswers(View view) {
        if (selected == 0) {
            Toast.makeText(getApplicationContext(),"Select an answer!", Toast.LENGTH_SHORT).show();
        } else {
            if (btnNext.getText().toString().equals("Submit")) {
                btnAns1.setClickable(false);
                btnAns2.setClickable(false);
                btnAns3.setClickable(false);
                btnSetAnswer();
                btnNext.setText("Next");
            } else {
                if (progressBar.getProgress() < 5) {
                    updateOptions();
                } else {
                    Intent intent = new Intent(this, results.class);
                    intent.putExtra("name", name);
                    intent.putExtra("score", Integer.toString(score));
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}