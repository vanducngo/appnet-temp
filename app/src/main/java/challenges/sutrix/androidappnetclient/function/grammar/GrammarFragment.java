package challenges.sutrix.androidappnetclient.function.grammar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;

public class GrammarFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_rock_and_roll,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).speak("We're in Rock and Roll fragment");
        super.onResume();
    }
}
