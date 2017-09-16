package com.asurion.paops.hackaton.vir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        textView = (TextView) findViewById(R.id.text1);

        Log.d(TAG, "Refreshed token: " + refreshedToken);

        if ( extras != null ) {
            textView.setText(getIntent().getStringExtra("DATA"));
        }

    }
}
