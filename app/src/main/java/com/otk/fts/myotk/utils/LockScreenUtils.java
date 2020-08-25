package com.otk.fts.myotk.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.otk.fts.myotk.R;

public class LockScreenUtils {
    // Member variables
    private OverlayDialog mOverlayDialog;
    private OnLockStatusChangedListener mLockStatusChangedListener;

    // Interface to communicate with owner activity
    public interface OnLockStatusChangedListener
    {
        public void onLockStatusChanged(boolean isLocked);
    }

    // Reset the variables
    public LockScreenUtils() {
        reset();
    }

    // Display overlay dialog with a view to prevent home button click
    public void lock(Activity activity) {
        if (mOverlayDialog == null) {
            Log.e("LockScreenUtils", "lock() - show");
            mOverlayDialog = new OverlayDialog(activity);
            mOverlayDialog.show();
            mLockStatusChangedListener = (OnLockStatusChangedListener) activity;
        }
    }

    // Reset variables
    public void reset() {
        Log.e("LockScreenUtils", "lock() - reset");
        if (mOverlayDialog != null) {
            mOverlayDialog.dismiss();
            mOverlayDialog = null;
        }
    }

    // Unlock the home button and give callback to unlock the screen
    public void unlock() {
        if (mOverlayDialog != null) {
            Log.e("LockScreenUtils", "unlock() - dismiss");
            mOverlayDialog.dismiss();
            mOverlayDialog = null;
            if(mLockStatusChangedListener!=null)
            {
                mLockStatusChangedListener.onLockStatusChanged(false);
            }
        }
    }

    // Create overlay dialog for lockedscreen to disable hardware buttons
    private static class OverlayDialog extends AlertDialog {

        public OverlayDialog(Activity activity) {
            super(activity, R.style.AppBaseTheme);
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            params.dimAmount = 0.0F;
            params.width = 0;
            params.height = 0;
            params.gravity = Gravity.BOTTOM;
            getWindow().setAttributes(params);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    0xffffff);
            setOwnerActivity(activity);
            setCancelable(false);
        }

        // consume touch events
        public final boolean dispatchTouchEvent(MotionEvent motionevent) {
            Log.d("LockScreenUtils", "OverlayDialog dispatchTouchEvent");
            return true;
        }

    }
}

