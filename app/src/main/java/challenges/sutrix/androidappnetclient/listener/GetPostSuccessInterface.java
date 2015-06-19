package challenges.sutrix.androidappnetclient.listener;

import java.util.ArrayList;

import challenges.sutrix.androidappnetclient.model.PostModel;

/**
 * Created by root on 19/05/2015.
 */
public interface GetPostSuccessInterface {
    public void onGetPostSuccess(ArrayList<PostModel> postModelArrayList);
}
