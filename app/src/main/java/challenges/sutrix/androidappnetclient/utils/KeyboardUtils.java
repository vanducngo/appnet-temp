package challenges.sutrix.androidappnetclient.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtils {

	/**
	 * hide soft keyboard on runtime
	 * 
	 * @param sActivity
	 */
	public static void hideKeyboard(Activity sActivity) {
		if (null != sActivity.getCurrentFocus()) {
			InputMethodManager tInputMethodManager = (InputMethodManager) sActivity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			tInputMethodManager.hideSoftInputFromWindow(sActivity
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			sActivity.getCurrentFocus().clearFocus();
		}
	}

	public static void hideKeyboardFromView(final View caller) {
		caller.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) caller
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(caller.getWindowToken(),
						InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		}, 100);
	}

	public static void setupUIForKeyboard(final Activity act, View view) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {

			view.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					hideKeyboard(act);
					return false;
				}
			});
		} else {
			if (!((EditText) view).isEnabled()) {
				view.setOnTouchListener(new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {
						hideKeyboard(act);
						return false;
					}
				});
			}
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				setupUIForKeyboard(act, innerView);
			}
		}
	}
}
