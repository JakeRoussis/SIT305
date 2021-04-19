package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = findViewById(R.id.txtName);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        txtName.setText(name);
    }

    public void start_quiz(View v) {
        if (txtName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please enter a name!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, quiz.class);
            intent.putExtra("name", txtName.getText().toString());
            startActivity(intent);
            finish();
        }
    }
}