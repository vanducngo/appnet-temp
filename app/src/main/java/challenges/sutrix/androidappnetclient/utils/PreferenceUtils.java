package challenges.sutrix.androidappnetclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    private static final String PREF_NAME = "PREF";
    public static final String PREF_FIRST_OPEN_APP = "PREF_FIRST_OPEN_APP";
    public static final String PREF_FACEBOOK_USER_ID = "PREF_FACEBOOK_USER_ID";
    public static final String PREF_LOGIN_FIRST_NAME = "PREF_LOGIN_FIRST_NAME";
    public static final String PREF_LOGIN_NAME = "PREF_LOGIN_NAME";
    public static final String PREF_PROGRAM_PLAYED_VIDEO = "PREF_PROGRAM_PLAYED_VIDEO";
    public static final String PREF_LOGIN_EMAIL = "PREF_LOGIN_EMAIL";
    public static final String PREF_USER_IMAGE_URL = "https://graph.facebook.com/%s/picture?type=large";
    public static final String PREF_USER_IMAGE = "PREF_USER_IMAGE";


    /*
    * clear all prefrence except first_open_app
    * */
    public static void logout(Context context) {
        if (context == null) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        boolean tFirstOpenApp = pref.getBoolean(PREF_PROGRAM_PLAYED_VIDEO, false);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        editor.putBoolean(PREF_FIRST_OPEN_APP, tFirstOpenApp);
        editor.commit();
    }

    public static void saveString(Context context, String key, String value) {
        if (context == null)
            return;

        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key, String defValue) {
        if (context == null)
            return defValue;
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        return settings.getString(key, defValue);
    }

    public static void saveLong(Context context, String key, long value) {
        if (context == null)
            return;

        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key, long defValue) {
        if (context == null)
            return defValue;
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        return settings.getLong(key, defValue);
    }

    public static void saveInt(Context context, String key, int value) {
        if (context == null)
            return;

        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (context == null)
            return defValue;
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        return settings.getInt(key, defValue);
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        if (context == null)
            return;

        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (context == null)
            return defValue;
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        return settings.getBoolean(key, defValue);
    }

    public static void remove(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void saveProgramPlayedVideo(Context context, int sProgramId) {
        if (context == null)
            return;

        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PREF_PROGRAM_PLAYED_VIDEO + sProgramId, true);
        editor.apply();
    }

    public static boolean isProgramPlayedVideo(Context context, int sProgramId, boolean defValue) {
        if (context == null)
            return defValue;
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, /* MODE_PRIVATE */
                0);
        return settings.getBoolean(PREF_PROGRAM_PLAYED_VIDEO + "" + sProgramId, defValue);
    }
}
