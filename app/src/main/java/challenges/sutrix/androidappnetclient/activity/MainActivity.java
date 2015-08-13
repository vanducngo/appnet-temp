package challenges.sutrix.androidappnetclient.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.fragment.HomeFragment;
import challenges.sutrix.androidappnetclient.utils.PreferenceUtils;


public class MainActivity extends BaseActivity implements GraphRequest.GraphJSONObjectCallback{


//    private ProfileTracker mProfileTracker;
//    private AccessTokenTracker accessTokenTracker;

    private Animator mCurrentAnimator;
    private long mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
            super.addFragment(new HomeFragment());
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

    /**
     * @param sView  The view to zoom in.
     */
    public void zoomPopUpView(final View sView, final String title) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            return;
//            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final RelativeLayout expandedImageView = (RelativeLayout) findViewById(R.id.expanded_layout);
//        expandedImageView.setImageResource(imageResId);

        final ImageView tClickMeBtn = (ImageView)findViewById(R.id.main_popup_image_view);
        tClickMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hello: " + title, Toast.LENGTH_SHORT).show();
//                onClick(expandedImageView);
            }
        });
        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        sView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale = 0.0f;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
//            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
//            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
//        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.centerX(),
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.centerY(),
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel, back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        sView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        sView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}
