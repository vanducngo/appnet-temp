package challenges.sutrix.androidappnetclient.function.vocabulary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;

/**
 * Created by root on 27/05/2015.
 */
public class VocabularyGeneralFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mVocabularySubject;
    private RelativeLayout mVocabularyRemembered;
    private RelativeLayout mVocabularyPractice;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_vocabulary_general,
                container, false);
        initData();
        initView(view);

        return view;
    }

    private void initData() {

    }

    private void initView(View view) {
        mVocabularySubject = (RelativeLayout) view.findViewById(R.id.rl_vocabulary_general_category);
        mVocabularyRemembered = (RelativeLayout) view.findViewById(R.id.rl_vocabulary_general_remember);
        mVocabularyPractice = (RelativeLayout) view.findViewById(R.id.rl_vocabulary_general_practice);

        //set OnClickListener
        mVocabularySubject.setOnClickListener(this);
        mVocabularyRemembered.setOnClickListener(this);
        mVocabularyPractice.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view.equals(mVocabularySubject)){
            ((MainActivity)getActivity()).replaceFragment(new VocabularyCategoryFragment(), true);
        }else if(view.equals(mVocabularyRemembered)){
            ((MainActivity)getActivity()).replaceFragment(new VocabularyRememberedFragment(), true);
        }else if(view.equals(mVocabularyPractice)){

        }
    }
}
