package challenges.sutrix.androidappnetclient.function.grammar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.function.grammar.adapter.GrammarMainAdapter;
import challenges.sutrix.androidappnetclient.function.grammar.model.GrammarModel;

public class GrammarFragment extends Fragment {

    //View
    private TextView mTvTitle;
    private ListView mLvGrammar;
    private GrammarMainAdapter mAdapter;


    //Data
    private ArrayList<GrammarModel> mGrammarModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_grammar,
                container, false);
        initData();
        initView(view);

        return view;
    }

    private void initData() {
        //Sample data
        mGrammarModel = new ArrayList<>();
        String[] tSample = getResources().getStringArray(R.array.grammar_type);
        GrammarModel tModel;
        for(String item : tSample){
            tModel = new GrammarModel(item, getString(R.string.grammar_sample_mean),getString(R.string.grammar_sample_description));
            mGrammarModel.add(tModel);
        }
    }

    private void initView(View view) {
        mLvGrammar = (ListView)view.findViewById(R.id.lv_grammar_main_list);
        mAdapter = new GrammarMainAdapter(getActivity(),mGrammarModel);
        mLvGrammar.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
