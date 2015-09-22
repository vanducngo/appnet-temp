package challenges.sutrix.androidappnetclient.api;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import challenges.sutrix.androidappnetclient.listener.GetPostSuccessInterface;
import challenges.sutrix.androidappnetclient.model.PostModel;
import challenges.sutrix.androidappnetclient.utils.ConnectionUtils;
import challenges.sutrix.androidappnetclient.utils.StringConstants;

/**
 * Created by root on 19/05/2015.
 */
public class APIGetRecentPost extends AsyncTask<String, Void, String> {
    private final Context mContext;

    //Interface on get recent post done.
    private GetPostSuccessInterface mCallback;

    public APIGetRecentPost(GetPostSuccessInterface callback, Context context) {
        this.mContext = context;
        this.mCallback = callback;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String rJsonString = null;
        try {
            //Request get recent post
            rJsonString = ConnectionUtils.executeHttpGet(StringConstants.RECENT_POST_API);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rJsonString;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result !=null) {
            JSONObject tJsonObject = null;
            try {
                tJsonObject = new JSONObject(result);
                // Parse the first list of the json
                String tJsonList = tJsonObject.getString(StringConstants.DATA);

                Gson gson = new Gson();
                PostModel[] tSuggestion1Array = gson.fromJson(
                        tJsonList, new TypeToken<PostModel[]>() {
                        }.getType());
                ArrayList<PostModel> tModelListResult = new ArrayList<PostModel>(
                        Arrays.asList(tSuggestion1Array));

                mCallback.onGetPostSuccess(tModelListResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(mContext, StringConstants.REQUEST_FALSE,Toast.LENGTH_SHORT).show();
        }
    }
}
