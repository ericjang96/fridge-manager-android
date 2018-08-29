package com.example.kevin.fridgemanager.Managers;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.kevin.fridgemanager.CallbackInterface.IKeyboardChange;

import java.util.Objects;

/**
 * Created by kevin on Aug 25, 2018
 **/
public class KeyboardManager {
    private static final String TAG = "KeyboardManager";

    private Activity activity;

    public KeyboardManager(Activity activity){
        this.activity = activity;
    }

    // Referencing code from https://gist.github.com/elqsar/9278206
    // given parent view, iterate the child views
    public void setupParent(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupParent(innerView);
            }
        }
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }

    // code as seen from https://stackoverflow.com/questions/4745988/how-do-i-detect-if-software-keyboard-is-visible-on-android-device
    public void setUpKeyboardChanges(final View view, final IKeyboardChange callback){
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int screenHeight = view.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                Log.d(TAG, "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    callback.doWhenKeyboardOpens();
                }
                else {
                    callback.doWhenKeyboardCloses();
                }
            }
        });

    }
}
