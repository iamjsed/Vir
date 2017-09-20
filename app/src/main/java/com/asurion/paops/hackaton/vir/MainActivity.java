package com.asurion.paops.hackaton.vir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobileconnectors.lex.interactionkit.listeners.AudioPlaybackListener;
import com.amazonaws.regions.Regions;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    TextView textView;
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        btnTest = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.text1);

        Log.d(TAG, "Refreshed token: " + refreshedToken);

        btnTest.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InteractiveVoiceActivity.class);
                startActivity(intent);
            }

        });


    }

    final AudioPlaybackListener audioPlaybackListener = new AudioPlaybackListener(){

        @Override
        public void onAudioPlaybackStarted() {
            Log.d(TAG, " -- Audio playback started");
        }

        @Override
        public void onAudioPlayBackCompleted() {
            Log.d(TAG, " -- Audio playback ended");
        }

        @Override
        public void onAudioPlaybackError(Exception e) {
            Log.d(TAG, " -- Audio playback error");
        }
    };

}
