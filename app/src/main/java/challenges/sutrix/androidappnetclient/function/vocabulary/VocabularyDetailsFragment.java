package challenges.sutrix.androidappnetclient.function.vocabulary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.function.vocabulary.adapter.VocabularyDetailsAdapter;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.RememberedCheckChangeListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;

/**
 * Created by root on 27/05/2015.
 */
public class VocabularyDetailsFragment extends Fragment implements ListView.OnItemClickListener, RememberedCheckChangeListener {

    private ListView mListView;
    private ArrayList<VocabularyModel> mVocabularyList;
    public static String ID = "id";
    private long mCategoryId = -1;
    VocabularyDetailsAdapter mAdapter;

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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ((MainActivity) getActivity()).zoomPopUpView(view, mVocabularyList.get(position).getWord());
    }

    @Override
    public void onRememberedCheckChangeListener(boolean isChecked, int position) {
        //TODO update in sqlite
        mVocabularyList.get(position).setRemember(isChecked);
        mAdapter.notifyDataSetChanged();

    }
}
