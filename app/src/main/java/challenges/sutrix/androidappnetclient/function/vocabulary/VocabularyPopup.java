package challenges.sutrix.androidappnetclient.function.vocabulary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import appnetmedia.lib.customdialog.CustomLayoutDialog;
import appnetmedia.lib.customdialog.SweetAlertDialog;
import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.NextPreviousWordListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;
import challenges.sutrix.androidappnetclient.utils.ConnectionUtils;

/**
 * Created by root on 10/09/2015.
 */
public class VocabularyPopup extends CustomLayoutDialog implements RecognitionListener{

    private Activity mContext;
    private VocabularyModel mVocabularyModel;
    private CheckBox mCbPopupIsRemember;
    private ImageView mIvPopupImage;
    private TextView mTvPopupName;
    private TextView mTvPopupSpelling;
    private TextView mTvPopupVNMeaning;
    private ImageView mIvPopupSpeak;
    private ImageView mIvPreviousWord;
    private ImageView mIvNextWord;
    private ImageView mIvRecordSpeech;
    private View mLayoutView;
    private NextPreviousWordListener mAnotherWordListener;
    private Intent mSpeechRecognitionIntent;
    private SpeechRecognizer mSpeedRecognizer = null;
    private SweetAlertDialog pDialog;
    private boolean isRecordSuccess = false;

    public VocabularyPopup(Activity context, VocabularyModel sVocabularyModel,NextPreviousWordListener sAnotherWordListener ) {
        super(context);
        this.mContext = context;
        this.mVocabularyModel = sVocabularyModel;
        this.mAnotherWordListener = sAnotherWordListener;
        initViews();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Speed intent
        mSpeechRecognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        mSpeechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                mContext.getString(R.string.speech_prompt));

        mSpeedRecognizer  = SpeechRecognizer.createSpeechRecognizer(mContext);
        mSpeedRecognizer.setRecognitionListener(this);
    }

    private int i = -1;
    private void initViews() {
        if (mLayoutView == null) {
            mLayoutView = mContext.getLayoutInflater().inflate(R.layout.vocabulary_details_popup_layout, null);


            mCbPopupIsRemember = (CheckBox) mLayoutView.findViewById(R.id.cb_vocabulary_popup_remember);
            mIvPopupImage = (ImageView) mLayoutView.findViewById(R.id.iv_vocabulary_details_image_popup);
            mTvPopupVNMeaning = (TextView) mLayoutView.findViewById(R.id.tv_vocabulary_details_vi_meaning_popup);
            mTvPopupSpelling = (TextView) mLayoutView.findViewById(R.id.tv_vocabulary_details_spelling_popup);
            mTvPopupName = (TextView) mLayoutView.findViewById(R.id.tv_vocabulary_details_name_popup);
            mIvPopupSpeak = (ImageView) mLayoutView.findViewById(R.id.iv_vocabulary_details_speak_popup);

            mIvPreviousWord = (ImageView) mLayoutView.findViewById(R.id.iv_vocabulary_details_previous_popup);
            mIvNextWord = (ImageView) mLayoutView.findViewById(R.id.iv_vocabulary_details_next_popup);

            mIvPreviousWord.setOnClickListener(this);
            mIvNextWord.setOnClickListener(this);

            mIvRecordSpeech = (ImageView)mLayoutView.findViewById(R.id.iv_vocabulary_details_record_popup);


            mIvRecordSpeech.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ConnectionUtils.isInternetAvailable(mContext)){
                        showRecordingDialog();
                    }else{
                        Toast.makeText(mContext,"No internet",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            mIvPopupSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) mContext).speak(mVocabularyModel.getWord());
                }
            });

            //Set layout to dialog.
            setCustomLayout(mLayoutView);
        }
    }

    private void showRecordingDialog(){
//        if(pDialog == null) {
            pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Loading");
//        }
        pDialog.show();
        pDialog.setCancelable(false);
        isRecordSuccess = false; //Flag
        mSpeedRecognizer.startListening(mSpeechRecognitionIntent);
        new CountDownTimer(800 * 3, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i) {
                    case 0:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.success_stroke_color));
                        break;
                    default:
                        break;
                }
            }

            public void onFinish() {
                Log.i("Onfinish", "onFinish()");
                i = -1;
                mSpeedRecognizer.stopListening();
            }

        }.start();
    }

    private void initData() {

        //Init dialog content
        if (mVocabularyModel != null) {
            mCbPopupIsRemember.setChecked(mVocabularyModel.isRemember());

            mTvPopupName.setText(mVocabularyModel.getWord());
            mTvPopupSpelling.setText(mVocabularyModel.getMeanVietnamese()); //TODO spelling text
            mTvPopupVNMeaning.setText(mVocabularyModel.getMeanVietnamese());
        }

    }

    public void setVocabularyModel(VocabularyModel sVocabularyModel){
        mVocabularyModel = sVocabularyModel;
        initData();
    }


    @Override
    public void onClick(View v) {
        if(v.equals(mIvNextWord)){
            mAnotherWordListener.onNextWordClick();
        }else if(v.equals(mIvPreviousWord)){
            mAnotherWordListener.onPreviousWordClick();
        }
        super.onClick(v);
    }


    //SpeedRecognizer listener

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }



    @Override
    public void onError(int error) {
        Log.i("onError", String.valueOf(error) + " - " +String.valueOf(isRecordSuccess));
        // Sometime onError will get called after onResults so we keep a boolean to ignore error also
        if(isRecordSuccess){
            return;
        }else {

            if (pDialog != null) {
                pDialog.dismissWithAnimation();
                Toast.makeText(mContext, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResults(Bundle results) {
        isRecordSuccess = true;
        Log.i("onResults", String.valueOf(results));
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String tStrResult = matches.get(0);
        Toast.makeText(mContext, tStrResult,Toast.LENGTH_SHORT).show();
        if(mVocabularyModel.getWord().trim().equals(tStrResult.trim())){
            pDialog.setTitleText("Success!")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        }else{
            pDialog.setTitleText("Fails!")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }



    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
