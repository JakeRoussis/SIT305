package com.example.workouttimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText inputWorkout;
    TextView txtSummary;
    Chronometer simpleChronometer;
    SharedPreferences sharedPreferences;
    boolean timerActive = false;
    long pausedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSummary = findViewById(R.id.txtSummary);
        inputWorkout = findViewById(R.id.inputWorkout);
        simpleChronometer = (Chronometer)findViewById(R.id.simpleChronometer);
        pausedTime = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
        sharedPreferences = getSharedPreferences("com.example.workouttimer", MODE_PRIVATE);
        retrieveSharedPreferences();

        if (savedInstanceState != null) {
            txtSummary.setText(savedInstanceState.getString("SUMMARY"));
            simpleChronometer.setBase(SystemClock.elapsedRealtime() + savedInstanceState.getLong("TIMER"));
            pausedTime = savedInstanceState.getLong("TIMER_PAUSED");

            if (savedInstanceState.getBoolean("TIMER_STATE")) {
                simpleChronometer.start();
                timerActive = true;
            }
            else simpleChronometer.setBase(SystemClock.elapsedRealtime() - pausedTime);
        }
        else timerActive = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("TIMER_STATE", timerActive);

        outState.putLong("TIMER", simpleChronometer.getBase() - SystemClock.elapsedRealtime());
        outState.putLong("TIMER_PAUSED", pausedTime);
        outState.putString("SUMMARY", txtSummary.getText().toString());
    }

    public void onPlayClick(View v) {
        if (!timerActive) {
            simpleChronometer.setBase(SystemClock.elapsedRealtime() - pausedTime);
            simpleChronometer.start();
            timerActive = true;
        }
    }

    public void onPauseClick(View v) {
        if (timerActive) {
            simpleChronometer.stop();
            pausedTime = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
            timerActive = false;
        }
    }

    public void onStopClick(View v) {
        updateWorkout(simpleChronometer.getText());
        simpleChronometer.stop();
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        pausedTime = 0;
        timerActive = false;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SUMMARY_MEM", txtSummary.getText().toString());
        editor.apply();
    }

    public void updateWorkout(CharSequence time) {
        if (inputWorkout.getText().toString().equals("")) txtSummary.setText("You spent " + time.toString() + " on your workout last time.");
        else txtSummary.setText("You spent " + time.toString() + " on " + inputWorkout.getText().toString() + " last time.");
    }

    public void retrieveSharedPreferences() {
        String text = sharedPreferences.getString("SUMMARY_MEM", "");
        txtSummary.setText(text);
    }

}