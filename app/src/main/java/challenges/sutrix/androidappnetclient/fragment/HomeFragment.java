package challenges.sutrix.androidappnetclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;

/**
 * Created by root on 27/05/2015.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private TextView mTvTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((MainActivity) getActivity()).setSlideMenuSelected(1);
        mTvTitle = (TextView)view.findViewById(R.id.tv_home_fragment_title);
        mTvTitle.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).speak("We're in home fragment");
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view.equals(mTvTitle)){
            ((MainActivity)getActivity()).zoomPopUpView(mTvTitle, mTvTitle.getText().toString());
        }
    }
}
