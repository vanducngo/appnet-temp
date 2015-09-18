package challenges.sutrix.androidappnetclient.utils;

import android.content.res.Resources;
import android.util.Log;

import challenges.sutrix.androidappnetclient.activity.MyApplication;

/**
 * Created by ngovd on 9/13/15.
 */
public class GeneralUtils {
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * Show log in non-production mode
     * @param tag
     * @param message
     */
    public static void showLog(String tag, String message){
        if(MyApplication.getApplicationMode() != ApplicationMode.PRODUCTION_MODE){
            Log.i(tag, message);
        }
    }
}
