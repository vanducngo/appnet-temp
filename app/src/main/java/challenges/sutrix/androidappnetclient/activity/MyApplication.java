package challenges.sutrix.androidappnetclient.activity;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;

import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.utils.ApplicationMode;

/**
 * Created by root on 19/05/2015.
 */
@ReportsCrashes(
        mailTo = "vdn@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.abc_action_bar_home_description)
public class MyApplication extends Application {
    private static Context mContext;

    public static ApplicationMode getApplicationMode() {
        return mApplicationMode;
    }

    private static ApplicationMode mApplicationMode;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        mContext = this;
        mApplicationMode = ApplicationMode.DEVELOP_MODE;
        // The following line triggers the initialization of ACRA
//        ACRA.init(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
