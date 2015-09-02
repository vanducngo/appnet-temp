package challenges.sutrix.androidappnetclient.function.vocabulary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import appnetmedia.lib.customdialog.CustomLayoutDialog;
import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.function.vocabulary.adapter.VocabularyDetailsAdapter;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.RememberedCheckChangeListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;

/**
 * Created by root on 27/05/2015.
 */
public class VocabularyDetailsFragment extends Fragment implements ListView.OnItemClickListener, RememberedCheckChangeListener, CustomLayoutDialog.OnSweetClickListener {

    private ListView mListView;
    private ArrayList<VocabularyModel> mVocabularyList;
    public static String ID = "id";
    private long mCategoryId = -1;
    private VocabularyDetailsAdapter mAdapter;

    private boolean isRememberPopupChecked;
    private int mCurrentItemPosition = -1;
    private View mPopupLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_vocabulary_details,
                container, false);
        initData();
        initView(view);

        return view;
    }

    private void initData() {
        String[] tVocabulary = getResources().getStringArray(R.array.vocabulary_type);
        VocabularyModel tCategoryModel;
        int tCategorySize = tVocabulary.length;
        if (mVocabularyList == null) {
            mVocabularyList = new ArrayList<>();
            for (int i = 0; i < tCategorySize; i++) {
                tCategoryModel = new VocabularyModel();
                tCategoryModel.setWord(tVocabulary[i]);
                tCategoryModel.setMeanVietnamese(tVocabulary[i]);
                tCategoryModel.setRemember(false);
                mVocabularyList.add(tCategoryModel);
            }
        }


        Bundle bundle = getArguments();
        mCategoryId = bundle.getLong(ID);
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_vocabulary_details_list);
        mAdapter = new VocabularyDetailsAdapter(getActivity(), mVocabularyList, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
        Toast.makeText(getActivity(), String.valueOf(mCategoryId), Toast.LENGTH_SHORT).show();
//        ((MainActivity) getActivity()).speak("We're in pop fragment");
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View sView, int position, long id) {
        zoomPopUpView(mVocabularyList.get(position), position);
    }

    @Override
    public void onRememberedCheckChangeListener(boolean isChecked, int position) {
        //TODO update in sqlite
        Log.i("Is checked", "Checked = " + isChecked);
        mVocabularyList.get(position).setRemember(isChecked);

    }

    /**
     * @param position
     */
    public void zoomPopUpView(final VocabularyModel tVocabularyModel, int position) {
        mCurrentItemPosition = position;

        CheckBox tCbPopupIsRemember;
        ImageView tIvPopupImage;
        TextView tTvPopupName;
        TextView tTvPopupSpelling;
        TextView tTvPopupVNMeaning;
        ImageView tIvPopupSpeak;

        if (mPopupLayout == null) {
            mPopupLayout = getActivity().getLayoutInflater().inflate(R.layout.vocabulary_details_popup_layout, null);
        }

        tCbPopupIsRemember = (CheckBox) mPopupLayout.findViewById(R.id.cb_vocabulary_popup_remember);
        tIvPopupImage = (ImageView)mPopupLayout.findViewById(R.id.iv_vocabulary_details_image_popup);
        tTvPopupVNMeaning = (TextView)mPopupLayout.findViewById(R.id.tv_vocabulary_details_vi_meaning_popup);
        tTvPopupSpelling = (TextView)mPopupLayout.findViewById(R.id.tv_vocabulary_details_spelling_popup);
        tTvPopupName = (TextView)mPopupLayout.findViewById(R.id.tv_vocabulary_details_name_popup);
        tIvPopupSpeak = (ImageView)mPopupLayout.findViewById(R.id.iv_vocabulary_details_speak_popup);


        //Init dialog content
        if (tVocabularyModel.isRemember()) {
            isRememberPopupChecked = true;
            tCbPopupIsRemember.setChecked(true);
        } else {
            isRememberPopupChecked = false;
            tCbPopupIsRemember.setChecked(false);
        }
        tTvPopupName.setText(tVocabularyModel.getWord());
        tTvPopupSpelling.setText(tVocabularyModel.getMeanVietnamese()); //TODO spelling text
        tTvPopupVNMeaning.setText(tVocabularyModel.getMeanVietnamese());

        tIvPopupSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).speak(tVocabularyModel.getWord());
            }
        });


        tCbPopupIsRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isRememberPopupChecked = isChecked;
            }
        });

        new CustomLayoutDialog(getActivity())
                .setCustomLayout(mPopupLayout)
                .setCancelClickListener(this)
                .setConfirmClickListener(this)
                .show();
    }

    @Override
    public void onClick(CustomLayoutDialog sweetAlertDialog, boolean isConfirmed) {
        sweetAlertDialog.dismissWithAnimation();

        mVocabularyList.get(mCurrentItemPosition).setRemember(isRememberPopupChecked);
        mAdapter.notifyDataSetInvalidated();

        mCurrentItemPosition = -1;
    }
}
