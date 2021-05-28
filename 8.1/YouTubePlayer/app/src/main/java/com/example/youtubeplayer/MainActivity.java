package com.example.youtubeplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends AppCompatActivity {

    EditText editTextURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextURL = (EditText) findViewById(R.id.editTextURL);
    }

    public void onPlayClicked(View v)
    {
        if (!editTextURL.getText().toString().isEmpty()) {
            Intent showAllIntent = new Intent(this, Player.class);
            String[] parts = editTextURL.getText().toString().split("=");
            String url = parts[1];
            showAllIntent.putExtra("url", url);
            startActivity(showAllIntent);
        } else {
            Toast.makeText(this, "You must enter a url!", Toast.LENGTH_SHORT).show();
        }
    }

}