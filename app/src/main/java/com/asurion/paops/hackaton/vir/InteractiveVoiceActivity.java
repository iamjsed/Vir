package com.asurion.paops.hackaton.vir;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobileconnectors.lex.interactionkit.Response;
import com.amazonaws.mobileconnectors.lex.interactionkit.config.InteractionConfig;
import com.amazonaws.mobileconnectors.lex.interactionkit.ui.InteractiveVoiceView;
import com.amazonaws.regions.Regions;
import com.amazonaws.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class InteractiveVoiceActivity extends Activity implements
        InteractiveVoiceView.InteractiveVoiceListener,
        TextToSpeech.OnInitListener {

    private static final String TAG = "VoiceActivity";
    private Context appContext;
    private InteractiveVoiceView voiceView;
    private TextView transcriptTextView;
    private TextView responseTextView;
    TextToSpeech tts;
    private String speakThis;
    private Bundle extras;



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

        Log.d(TAG, String.format(
                Locale.US,
                "Dialog ready for fulfillment:\n\tIntent: %s\n\tSlots: %s",
                intent,
                slots.toString()));

    }

    @Override
    public void onResponse(Response response) {

        Log.d(TAG, "Bot response: " + response.getTextResponse());
        Log.d(TAG, "Transcript: " + response.getInputTranscript());

        responseTextView.setText(response.getTextResponse());
        transcriptTextView.setText(response.getInputTranscript());

    }

    @Override
    public void onError(String responseText, Exception e) {
        Log.e(TAG, "Error: " + responseText, e);
    }

    private void init() {
        JSONObject jObject = null;
        if ( extras != null ) {
            String payload = getIntent().getStringExtra("DATA");
            HashMap<String, String> map = new HashMap<String, String>();

            try {
                jObject = new JSONObject(payload);
                speakThis = jObject.getString("utterance");
                Log.d(TAG, "Message Payload: " + jObject.getString("utterance"));
                Log.d(TAG, "Event data: " + jObject.getString("event_data"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (jObject != null) {

            tts = new TextToSpeech(getApplicationContext(), this);

        }

        appContext = getApplicationContext();
        voiceView = (InteractiveVoiceView) findViewById(R.id.voiceInterface);
        voiceView.setInteractiveVoiceListener(this);

        CognitoCredentialsProvider credentialsProvider = new CognitoCredentialsProvider(
                appContext.getString(R.string.aws_region) + ":" + appContext.getString(R.string.identity_id_test),
                Regions.fromName(appContext.getString(R.string.aws_region)));

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

    @Override
    public void onInit(int status) {
        if(status != TextToSpeech.ERROR) {
            tts.setLanguage(Locale.US);
            tts.speak(speakThis, TextToSpeech.QUEUE_FLUSH, null, "");
        }
    }
}