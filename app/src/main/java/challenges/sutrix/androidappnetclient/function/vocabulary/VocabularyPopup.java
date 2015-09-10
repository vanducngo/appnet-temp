package challenges.sutrix.androidappnetclient.function.vocabulary;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import appnetmedia.lib.customdialog.CustomLayoutDialog;
import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.NextPreviousWordListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;

/**
 * Created by root on 10/09/2015.
 */
public class VocabularyPopup extends CustomLayoutDialog {

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
    private View mLayoutView;
    private NextPreviousWordListener mAnotherWordListener;

    public VocabularyPopup(Activity context, VocabularyModel sVocabularyModel,NextPreviousWordListener sAnotherWordListener ) {
        super(context);
        this.mContext = context;
        this.mVocabularyModel = sVocabularyModel;
        this.mAnotherWordListener = sAnotherWordListener;
        initViews();
        initData();
    }

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
}
