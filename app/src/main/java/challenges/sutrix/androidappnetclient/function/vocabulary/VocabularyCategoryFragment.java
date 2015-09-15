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

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.function.vocabulary.adapter.VocabularyCategoryAdapter;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyCategoryModel;

/**
 * Created by root on 27/05/2015.
 */
public class VocabularyCategoryFragment extends Fragment implements ListView.OnItemClickListener {

    private ListView mListView;
    private List<VocabularyCategoryModel> mVocabularyCategoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_vocabulary,
                container, false);
        initData();
        initView(view);

        return view;
    }

    private void initData() {
        mVocabularyCategoryList = VocabularyCategoryModel.getAll();

    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_vocabulary_main_list);
        VocabularyCategoryAdapter adapter = new VocabularyCategoryAdapter(getActivity(), mVocabularyCategoryList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle data = new Bundle();
        data.putLong(VocabularyDetailsFragment.ID, mVocabularyCategoryList.get(position).getCategoryId());
        Fragment tFragment = new VocabularyDetailsFragment();
        tFragment.setArguments(data);
        ((MainActivity) getActivity()).replaceFragment(tFragment, true);
    }
}
