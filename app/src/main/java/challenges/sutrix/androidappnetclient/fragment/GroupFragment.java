package challenges.sutrix.androidappnetclient.fragment;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;

@SuppressLint("SetJavaScriptEnabled")
public class GroupFragment extends Fragment {

    private WebView mWebView;
    private String mUrl = "https://m.facebook.com/englishforTOEIC";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View rView = inflater.inflate(R.layout.fragment_group, parent,
                false);

        initView(rView);

        if (isAdded()) {
            mWebView.setWebViewClient(new mBrowser());
            mWebView.loadUrl(mUrl);
        }
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        return rView;
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).speak("We're in group fragment");
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @SuppressWarnings("deprecation")
    private void initView(View sView) {
        mWebView = (WebView) sView.findViewById(R.id.ads_detail_webview);

        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());

    }

    private class mBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (isAdded()) {

                Log.i(GroupFragment.class.getSimpleName(), "shouldOverrideUrlLoading: " + url);
                mWebView.loadUrl(url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


}
