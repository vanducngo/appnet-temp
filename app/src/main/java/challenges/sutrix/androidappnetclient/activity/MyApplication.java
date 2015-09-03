package challenges.sutrix.androidappnetclient.activity;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import challenges.sutrix.androidappnetclient.R;

/**
 * Created by root on 19/05/2015.
 */
@ReportsCrashes(
        mailTo = "vdn@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.abc_action_bar_home_description)
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        // The following line triggers the initialization of ACRA
//        ACRA.init(this);
    }
}
