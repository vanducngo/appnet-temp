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

import java.util.List;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.function.overview.OverviewFragment;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyCategoryModel;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;
import challenges.sutrix.androidappnetclient.utils.ApplicationMode;
import challenges.sutrix.androidappnetclient.utils.GeneralUtils;
import challenges.sutrix.androidappnetclient.utils.PreferenceUtils;
import challenges.sutrix.androidappnetclient.utils.SecurityUtils;


public class MainActivity extends BaseActivity implements GraphRequest.GraphJSONObjectCallback{
    private final String TAG = "MainActivity";


//    private ProfileTracker mProfileTracker;
//    private AccessTokenTracker accessTokenTracker;

//    private Animator mCurrentAnimator;
//    private long mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call encrypt data - ENCRYPT_MODE only
        if(((MyApplication)getApplication()).getApplicationMode() == ApplicationMode.ENCRYPTION_MODE) {
            GeneralUtils.showLog(TAG, "encryptData");
            encryptData();
        }

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

    /**
     * Encrypt data and save it in SQLite
     * This function is executed in Develop mode only
     */
    private void encryptData() {

        //Category
        List<VocabularyCategoryModel> listPlainCategory = VocabularyCategoryModel.getAll();
        for(VocabularyCategoryModel item : listPlainCategory){
            item.setName(SecurityUtils.encodeString(item.getName()));
            item.setMeaning(SecurityUtils.encodeString(item.getMeaning()));
            item.setDescription(SecurityUtils.encodeString(item.getDescription()));
            item.save();
        }

        //Vocabulary
        List<VocabularyModel> listPlainVocabulary = VocabularyModel.getAll();
        for(VocabularyModel item : listPlainVocabulary){
            item.setWord(SecurityUtils.encodeString(item.getWord()));
            item.setPhonetic(SecurityUtils.encodeString(item.getPhonetic()));
            item.setShortMeanVietnamese(SecurityUtils.encodeString(item.getShortMeanVietnamese()));
            item.setMeanVietnamese(SecurityUtils.encodeString(item.getMeanVietnamese()));
            item.setSynonyms(SecurityUtils.encodeString(item.getSynonyms()));
            item.setExample(SecurityUtils.encodeString(item.getExample()));
            item.setExampleMeaning(SecurityUtils.encodeString(item.getExampleMeaning()));
            item.setParagraph(SecurityUtils.encodeString(item.getParagraph()));
            item.save();
        }
        GeneralUtils.showLog(TAG, "encrypt done");

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void LogoutFB(){
        LoginManager.getInstance().logOut();
        PreferenceUtils.remove(getApplicationContext(), PreferenceUtils.PREF_FACEBOOK_USER_ID);
        PreferenceUtils.remove(getApplicationContext(), PreferenceUtils.PREF_LOGIN_NAME);
        PreferenceUtils.remove(getApplicationContext(), PreferenceUtils.PREF_LOGIN_EMAIL);
        PreferenceUtils.remove(getApplicationContext(), PreferenceUtils.PREF_USER_IMAGE);
    }
}
