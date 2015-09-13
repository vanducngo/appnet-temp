package challenges.sutrix.androidappnetclient.function.overview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.utils.Constants;

/**
 * Created by root on 27/05/2015.
 */
    public class OverviewFragment extends Fragment implements View.OnClickListener, RecognitionListener {
    private TextView mTvTitle;
    private Intent mSpeechRecognitionIntent;
    private SpeechRecognizer mSpeedRecognizer = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setSlideMenuSelected(1);
        mTvTitle = (TextView) view.findViewById(R.id.tv_home_fragment_title);
        mTvTitle.setOnClickListener(this);

        //Speed intent
        mSpeechRecognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        mSpeechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));

        mSpeedRecognizer  = SpeechRecognizer.createSpeechRecognizer(getActivity());
        mSpeedRecognizer.setRecognitionListener(this);
    }

    @Override
    public void onResume() {
//        ((MainActivity) getActivity()).speak("We're in home fragment");
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mTvTitle)) {
            Toast.makeText(getActivity(), "Start",Toast.LENGTH_SHORT).show();
            mSpeedRecognizer.startListening(mSpeechRecognitionIntent);
            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mSpeedRecognizer.stopListening();
                        }
                    });
                }
            }, 2000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult", "onActivityResult");
        switch (requestCode) {
            case Constants.SPEECH_REQUEST_CODE: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTvTitle.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {
        Log.i("onResults", "onResults");
        ArrayList<String> matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = matches.get(0);
        mTvTitle.setText(text);
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
