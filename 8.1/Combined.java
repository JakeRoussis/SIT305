
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

================================================================================================
################################################################################################
================================================================================================

package com.example.youtubeplayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static android.content.ContentValues.TAG;

public class Player extends YouTubeBaseActivity {

    YouTubePlayerView mYouTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    String videoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        videoURL = intent.getStringExtra("url");
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "LOGGING: Done initializing.");
                youTubePlayer.loadVideo(videoURL);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "LOGGING: Failed initializing.");
            }
        };
        mYouTubePlayerView.initialize(Config.getAPI(), mOnInitializedListener);
    }
}

================================================================================================
################################################################################################
================================================================================================

package com.example.youtubeplayer;

public class Config {

    public Config() {
    }

    private static final String API_KEY = "AIzaSyCuMtEYBHYQ9BvQo6WnruuoLcNN0FujzJ4";

    public static String getAPI() {
        return API_KEY;
    }
}