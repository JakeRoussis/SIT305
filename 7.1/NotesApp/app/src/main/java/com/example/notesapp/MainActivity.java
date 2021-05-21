package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewNote(View v) {
        Intent intent = new Intent(MainActivity.this, NewNote.class);
        startActivity(intent);
    }

    public void onClickShowNote(View v) {
        Intent intent = new Intent(MainActivity.this, ShowNotes.class);
        startActivity(intent);
    }
}