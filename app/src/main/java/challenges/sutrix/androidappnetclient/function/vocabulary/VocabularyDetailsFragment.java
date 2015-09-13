package challenges.sutrix.androidappnetclient.function.vocabulary;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import appnetmedia.lib.customdialog.CustomLayoutDialog;
import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.function.vocabulary.adapter.VocabularyDetailsAdapter;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.NextPreviousWordListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.RememberedCheckChangeListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;

/**
 * Created by root on 27/05/2015.
 */
public class VocabularyDetailsFragment extends Fragment implements ListView.OnItemClickListener, RememberedCheckChangeListener, CustomLayoutDialog.OnSweetClickListener, NextPreviousWordListener {

    private ListView mListView;
    private ArrayList<VocabularyModel> mVocabularyList;
    public static String ID = "id";
    private long mCategoryId = -1;
    private VocabularyDetailsAdapter mAdapter;

    private int mCurrentItemPosition = -1;
    private CustomLayoutDialog mVocabularyPopup;


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
        zoomPopUpView(position);
    }

    /**
     * Monitoring checkBox change in the listView.
     *
     * @param isChecked
     * @param position
     */
    @Override
    public void onRememberedCheckChangeListener(boolean isChecked, int position) {
        //TODO update in SQLite
        Log.i("Is checked", "Checked = " + isChecked);
        mVocabularyList.get(position).setRemember(isChecked);

    }

    public void zoomPopUpView(final int position) {
        mCurrentItemPosition = position;
        mVocabularyPopup = new VocabularyPopup(getActivity(),mVocabularyList.get(mCurrentItemPosition) , this)
                .setCancelClickListener(this)
                .setConfirmClickListener(this)
                .showCancelButton(false);
        mVocabularyPopup.show();
    }

    @Override
    public void onClick(CustomLayoutDialog sweetAlertDialog, boolean isConfirmed) {
        sweetAlertDialog.dismissWithAnimation();

//        mVocabularyList.get(mCurrentItemPosition).setRemember(isRememberPopupChecked);
//        mAdapter.notifyDataSetInvalidated();
//        mCurrentItemPosition = -1;
    }

    @Override
    public void onNextWordClick() {
        if(mCurrentItemPosition == mVocabularyList.size()-1) {
            Toast.makeText(getActivity(), getString(R.string.vocabulary_reach_end_string), Toast.LENGTH_SHORT).show();
        }else{
            mCurrentItemPosition++;
            if(mVocabularyPopup !=null){
                ((VocabularyPopup)mVocabularyPopup).setVocabularyModel(mVocabularyList.get(mCurrentItemPosition));
            }
        }
    }

    @Override
    public void onPreviousWordClick() {
        if(mCurrentItemPosition == mVocabularyList.size()-1) {
            Toast.makeText(getActivity(), getString(R.string.vocabulary_reach_begining_string), Toast.LENGTH_SHORT).show();
        }else{
            mCurrentItemPosition--;
            if(mVocabularyPopup !=null){
                ((VocabularyPopup)mVocabularyPopup).setVocabularyModel(mVocabularyList.get(mCurrentItemPosition));
            }
        }
    }
}
