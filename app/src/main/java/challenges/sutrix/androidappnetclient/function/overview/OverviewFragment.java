package challenges.sutrix.androidappnetclient.function.overview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;

/**
 * Created by root on 27/05/2015.
 */
    public class OverviewFragment extends Fragment implements View.OnClickListener {
    //View
    private WebView mWvOverview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setSlideMenuSelected(1);
        }
        mWvOverview = (WebView)view.findViewById(R.id.wv_home_fragment_overview);

        byte[] data = Base64.decode(getString(R.string.overview_full), Base64.DEFAULT);
        String tOverview  = "";
        try {
            tOverview = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mWvOverview.loadDataWithBaseURL(null, tOverview, "text/html", "utf-8", null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }

}
