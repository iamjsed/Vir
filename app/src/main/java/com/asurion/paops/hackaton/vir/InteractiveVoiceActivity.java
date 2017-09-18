package com.asurion.paops.hackaton.vir;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobileconnectors.lex.interactionkit.Response;
import com.amazonaws.mobileconnectors.lex.interactionkit.config.InteractionConfig;
import com.amazonaws.mobileconnectors.lex.interactionkit.ui.InteractiveVoiceView;
import com.amazonaws.regions.Regions;
import com.amazonaws.util.StringUtils;

import java.util.Locale;
import java.util.Map;


public class InteractiveVoiceActivity extends Activity implements InteractiveVoiceView.InteractiveVoiceListener {

    private static final String TAG = "VoiceActivity";
    private Context appContext;
    private InteractiveVoiceView voiceView;
    private TextView transcriptTextView;
    private TextView responseTextView;

    private static final String COGNITO_ID = "0e6d7e2a-8316-414a-aff4-384502a07f10";
    private static final String AWS_REGION_NAME = "us-east-1";

    private Bundle extras;
    private TextToSpeech ttsobj;
    private final int LANG_US = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_voice);
        extras = getIntent().getExtras();
        transcriptTextView = (TextView) findViewById(R.id.transcriptTextView);
        responseTextView = (TextView) findViewById(R.id.responseTextView);
        init();
        StringUtils.isBlank("notempty");

    }

    @Override
    public void dialogReadyForFulfillment(Map<String, String> slots, String intent) {

    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onError(String responseText, Exception e) {

    }

    private void init() {

        if ( extras != null ) {
            ttsobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    ttsobj.setLanguage(Locale.US);
                }
            });

            ttsobj.speak("hello", TextToSpeech.QUEUE_FLUSH, null);

        }

        appContext = getApplicationContext();
        voiceView = (InteractiveVoiceView) findViewById(R.id.voiceInterface);
        voiceView.setInteractiveVoiceListener(this);

        CognitoCredentialsProvider credentialsProvider = new CognitoCredentialsProvider(
                appContext.getString(R.string.aws_region) + ":" + appContext.getString(R.string.identity_id_test),
                Regions.fromName(AWS_REGION_NAME));

        voiceView.getViewAdapter().setCredentialProvider(credentialsProvider);

        /*
        * *
        * * Configure voiceInterface
        * */
        voiceView.getViewAdapter().setInteractionConfig(
                new InteractionConfig(appContext.getString(R.string.bot_name),
                appContext.getString(R.string.bot_alias)
                )
        );

        voiceView.getViewAdapter().setAwsRegion(appContext.getString(R.string.aws_region));
    }

}