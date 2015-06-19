package challenges.sutrix.androidappnetclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import challenges.sutrix.androidappnetclient.activity.MainActivity;
import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.utils.ConnectionUtils;
import challenges.sutrix.androidappnetclient.utils.Constants;
import challenges.sutrix.androidappnetclient.utils.PreferenceUtils;

/**
 * Created by root on 27/05/2015.
 */
public class MainFragment extends Fragment implements FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback, View.OnClickListener {

    LoginButton mLoginBtn;
    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main,
                container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);

        mLoginBtn = (LoginButton) view.findViewById(R.id.login_button);
//        mLoginBtn.setOnClickListener(this);
        mLoginBtn.setFragment(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);
        request.executeAsync();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException e) {

    }

    @Override
    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
        if (graphResponse.getError() == null) {
            if (Profile.getCurrentProfile() != null) {
                String bName = Profile.getCurrentProfile().getName();
                String bID = jsonObject.optString("id");
                String bEmail = jsonObject.optString("email");

                PreferenceUtils.saveString(getActivity(), PreferenceUtils.PREF_FACEBOOK_USER_ID, Profile.getCurrentProfile().getId());
                PreferenceUtils.saveString(getActivity(), PreferenceUtils.PREF_LOGIN_NAME, bName);
                PreferenceUtils.saveString(getActivity(), PreferenceUtils.PREF_LOGIN_EMAIL, bEmail);
                PreferenceUtils.saveString(getActivity(), PreferenceUtils.PREF_USER_IMAGE, String.format(PreferenceUtils.PREF_USER_IMAGE_URL, bID));
                //Goto Activity main
                ((MainActivity) getActivity()).replaceFragment(new HomeFragment(), true);

            } else {
                Toast.makeText(getActivity(), getString(R.string.error_facebook_login_string), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), graphResponse.getError().getErrorUserMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mLoginBtn)) {
            if (ConnectionUtils.isInternetAvailable(getActivity())) {
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(Constants.FACEBOOK_PERMISSION));
            }
        }
    }
}
