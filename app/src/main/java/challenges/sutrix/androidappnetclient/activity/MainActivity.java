package challenges.sutrix.androidappnetclient.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.function.overview.OverviewFragment;
import challenges.sutrix.androidappnetclient.utils.PreferenceUtils;


public class MainActivity extends BaseActivity implements GraphRequest.GraphJSONObjectCallback{


//    private ProfileTracker mProfileTracker;
//    private AccessTokenTracker accessTokenTracker;

//    private Animator mCurrentAnimator;
//    private long mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve and cache the system's default "short" animation time.
//        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        FacebookSdk.sdkInitialize(getApplicationContext());
        LogoutFB();
        super.initNavigationDrawer();

//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(
//                    AccessToken oldAccessToken,
//                    AccessToken currentAccessToken) {
//
//                if (currentAccessToken == null){
//                    showToast("Logout");
//                }
//            }
//        };
//
//        mProfileTracker = new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(Profile oldProf, Profile profile) {
//
//                showToast("Profile changed");
//                if(profile!=null) {
//                    String bName = profile.getName();
//                    PreferenceUtils.saveString(MainActivity.this, PreferenceUtils.PREF_LOGIN_NAME, bName);
//                    refreshSlideMenu();
//                }
//            }
//        };
//
//        accessTokenTracker.startTracking();
//        mProfileTracker.startTracking();

//        if (isLoggedIn()) {
            super.addFragment(new OverviewFragment());
//        } else {
//            super.addFragment(new MainFragment());
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
//        accessTokenTracker.stopTracking();
//        mProfileTracker.stopTracking();
        super.onStop();
    }

    private boolean isLoggedIn() {
        boolean loggedIn = false;
        if (AccessToken.getCurrentAccessToken() != null) {
            loggedIn = true;
        }
        return loggedIn;
    }

    @Override
    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
        if (graphResponse.getError() == null) {
            if (Profile.getCurrentProfile() != null) {
                String bName = Profile.getCurrentProfile().getName();
                String bID = jsonObject.optString("id");
                String bEmail = jsonObject.optString("email");

                PreferenceUtils.saveString(this, PreferenceUtils.PREF_FACEBOOK_USER_ID, Profile.getCurrentProfile().getId());
                PreferenceUtils.saveString(this, PreferenceUtils.PREF_LOGIN_NAME, bName);
                PreferenceUtils.saveString(this, PreferenceUtils.PREF_LOGIN_EMAIL, bEmail);
                PreferenceUtils.saveString(this, PreferenceUtils.PREF_USER_IMAGE, String.format(PreferenceUtils.PREF_USER_IMAGE_URL, bID));
                //Refresh slide menu
                super.refreshSlideMenu();

            } else {
                Toast.makeText(this, getString(R.string.error_facebook_login_string), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, graphResponse.getError().getErrorUserMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void LogoutFB(){
        LoginManager.getInstance().logOut();
        PreferenceUtils.remove(getApplicationContext(), PreferenceUtils.PREF_FACEBOOK_USER_ID);
        PreferenceUtils.remove(getApplicationContext(), PreferenceUtils.PREF_LOGIN_NAME);
        PreferenceUtils.remove(getApplicationContext(), PreferenceUtils.PREF_LOGIN_EMAIL);
        PreferenceUtils.remove(getApplicationContext(), PreferenceUtils.PREF_USER_IMAGE);
    }

}
