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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.function.vocabulary.adapter.VocabularyDetailsAdapter;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.PopupCloseListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.RememberedCheckChangeListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;

/**
 * Created by root on 27/05/2015.
 */
public class VocabularyDetailsFragment extends Fragment implements PopupCloseListener, ListView.OnItemClickListener, RememberedCheckChangeListener {

    private ListView mListView;
    private ArrayList<VocabularyModel> mVocabularyList;
    public static String ID = "id";
    private long mCategoryId = -1;
    private VocabularyDetailsAdapter mAdapter;
    private View mPopupContainer;
    private RelativeLayout mExpandedImageView;
    private RelativeLayout mPopupLayout;
    private boolean isPopupShown = false;
    private VocabularyDetailsPopupHandler mVocabularyPopUpHandler;


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

        mPopupContainer = view.findViewById(R.id.container);
        mExpandedImageView = (RelativeLayout) view.findViewById(R.id.expanded_layout);
        mPopupLayout = (RelativeLayout) view.findViewById(R.id.rl_vocabulary_popup);
    }

    @Override
    public void onResume() {
        Toast.makeText(getActivity(), String.valueOf(mCategoryId), Toast.LENGTH_SHORT).show();
//        ((MainActivity) getActivity()).speak("We're in pop fragment");
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View sView, int position, long id) {
        boolean isRemembered = false;
        if (sView != null) {
            CheckBox checkBox = (CheckBox) sView.findViewById(R.id.cb_vocabulary_details_remembered_item_item);
            isRemembered = checkBox.isChecked();
        }
        zoomPopUpView(sView, mVocabularyList.get(position), position, this, isRemembered);
    }

    @Override
    public void onRememberedCheckChangeListener(boolean isChecked, int position) {
        //TODO update in sqlite
        Log.i("Is checked", "Checked = " + isChecked);
        mVocabularyList.get(position).setRemember(isChecked);

    }

    @Override
    public void onPopupButtonOkClicked(boolean isChecked, int position, View sView) {
        isPopupShown = false;
        if (sView != null) {
            CheckBox checkBox = (CheckBox) sView.findViewById(R.id.cb_vocabulary_details_remembered_item_item);
//            mVocabularyList.get(position).setRemember(isChecked);
            Log.i("ischeck", "CHeck = " + isChecked);
            checkBox.setChecked(isChecked);
        }
    }

    /**
     * @param sView        The view to zoom in.
     * @param position
     * @param isRemembered
     */
    public void zoomPopUpView(final View sView, final VocabularyModel tVocabularyModel, int position, PopupCloseListener sListener, boolean isRemembered) {
        isPopupShown = true;
        if(mVocabularyPopUpHandler == null) {
            mVocabularyPopUpHandler = new VocabularyDetailsPopupHandler(getActivity());
        }
        mVocabularyPopUpHandler.showVocabularyPopup(sView, mExpandedImageView, mPopupLayout, mPopupContainer, tVocabularyModel, position, sListener, isRemembered);
    }
    public boolean getPopupStatus(){
        return isPopupShown;
    }

    public void hidePopup() {
        isPopupShown = false;
        mVocabularyPopUpHandler.closedPopup();
    }
}
