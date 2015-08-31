package challenges.sutrix.androidappnetclient.function.listening;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import challenges.sutrix.androidappnetclient.R;

/**
 * Created by root on 27/05/2015.
 */
public class ListeningFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_listening,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }

    @Override
    public void onResume() {
//        ((MainActivity)getActivity()).speak("We're in country music fragment");
        super.onResume();
    }
}
