package challenges.sutrix.androidappnetclient.function.grammar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.api.APIConstants;

public class GrammarFragment extends Fragment{

    private TextView mTvTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_grammar,
                container, false);
        initView(view);
        //TODO sample test request
        tryGetSample();
        return view;
    }

    private void tryGetSample() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = APIConstants.API_CATEGORY_SAMPLE;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTvTitle.setText("Response is: "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTvTitle.setText("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void initView(View view) {
        mTvTitle = (TextView)view.findViewById(R.id.tv_grammar_title);
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).speak("We're in Rock and Roll fragment");
        super.onResume();
    }
}
