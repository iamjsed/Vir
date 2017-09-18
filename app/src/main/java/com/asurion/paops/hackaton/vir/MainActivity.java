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
        HashMap<String,Object> result = null;
        if ( extras != null ) {
            Log.d(TAG, "HashMap: " + getIntent().getStringExtra("DATA"));

            CognitoCredentialsProvider credentialsProvider = new CognitoCredentialsProvider(
                    getApplicationContext().getString(R.string.aws_region) + ":" + getApplicationContext().getString(R.string.identity_id_test),
                    Regions.fromName(getApplicationContext().getString(R.string.aws_region)));

//            InteractionClient lexInteractionClient = new InteractionClient(
//                    getApplicationContext(),
//                    credentialsProvider,
//                    Regions.fromName(getApplicationContext().getResources().getString(R.string.aws_region)),
//                    getApplicationContext().getResources().getString(R.string.bot_name),
//                    getApplicationContext().getResources().getString(R.string.bot_alias));
//            lexInteractionClient.setAudioPlaybackListener(audioPlaybackListener);
//            lexInteractionClient.setInteractionListener(interactionListener);


        }
        textView.setText("Hello World");

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
