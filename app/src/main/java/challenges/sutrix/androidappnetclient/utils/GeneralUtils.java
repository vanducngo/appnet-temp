package challenges.sutrix.androidappnetclient.utils;

import android.content.res.Resources;

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
}
