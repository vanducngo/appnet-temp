package challenges.sutrix.androidappnetclient.function.vocabulary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import appnetmedia.lib.customdialog.CustomLayoutDialog;
import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.function.vocabulary.adapter.VocabularyDetailsAdapter;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.NextPreviousWordListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.RememberedCheckChangeListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;
import challenges.sutrix.androidappnetclient.utils.GeneralUtils;

/**
 * Created by root on 27/05/2015.
 */
public class VocabularyRememberedFragment extends Fragment implements ListView.OnItemClickListener, RememberedCheckChangeListener, CustomLayoutDialog.OnSweetClickListener, NextPreviousWordListener {

    private ListView mListView;
    private List<VocabularyModel> mVocabularyList;
    private VocabularyDetailsAdapter mAdapter;

    private int mCurrentItemPosition = -1;
    private CustomLayoutDialog mVocabularyPopup;
    private final String TAG = "VocabularyDetailsFragment";


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
        mVocabularyList = VocabularyModel.getRememberedVocabularies();
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_vocabulary_details_list);
        mAdapter = new VocabularyDetailsAdapter(getActivity(), mVocabularyList, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onResume() {
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
        GeneralUtils.showLog(TAG, "Is checked  = " + isChecked);
        mVocabularyList.get(position).setRemember(isChecked);
        //save to database
        mVocabularyList.get(position).save();

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
    public void onClick(CustomLayoutDialog sweetAlertDialog, boolean isRemember) {
        sweetAlertDialog.dismissWithAnimation();

        mVocabularyList.get(mCurrentItemPosition).setRemember(isRemember);
        mVocabularyList.get(mCurrentItemPosition).save();
        mAdapter.notifyDataSetInvalidated();
        mCurrentItemPosition = -1;
    }

    @Override
    public void onNextWordClick() {
        if(mCurrentItemPosition == mVocabularyList.size()-1) {
            if(getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).showToast(R.string.vocabulary_reach_end_string);
            }
        }else{
            mCurrentItemPosition++;
            if(mVocabularyPopup !=null){
                ((VocabularyPopup)mVocabularyPopup).setVocabularyModel(mVocabularyList.get(mCurrentItemPosition));
            }
        }
    }

    @Override
    public void onPreviousWordClick() {
        if(mCurrentItemPosition == 0) {

            if(getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).showToast(R.string.vocabulary_reach_begining_string);
            }
        }else{
            mCurrentItemPosition--;
            if(mVocabularyPopup !=null){
                ((VocabularyPopup)mVocabularyPopup).setVocabularyModel(mVocabularyList.get(mCurrentItemPosition));
            }
        }
    }
}
