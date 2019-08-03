package com.teemo.common.swipe;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.qmuiteam.qmui.arch.Utils;

class InnerBaseActivity extends AppCompatActivity {
    private static int NO_REQUESTED_ORIENTATION_SET = -100;
    private boolean mConvertToTranslucentCauseOrientationChanged = false;
    private int mPendingRequestedOrientation = NO_REQUESTED_ORIENTATION_SET;

    void convertToTranslucentCauseOrientationChanged() {
        Utils.convertActivityToTranslucent(this);
        mConvertToTranslucentCauseOrientationChanged = true;
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (mConvertToTranslucentCauseOrientationChanged && (Build.VERSION.SDK_INT == Build.VERSION_CODES.O
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1)) {
            Log.i("InnerBaseActivity", "setRequestedOrientation when activity is translucent");
            mPendingRequestedOrientation = requestedOrientation;
        } else {
            super.setRequestedOrientation(requestedOrientation);
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mConvertToTranslucentCauseOrientationChanged) {
            mConvertToTranslucentCauseOrientationChanged = false;
            Utils.convertActivityFromTranslucent(this);
            if (mPendingRequestedOrientation != NO_REQUESTED_ORIENTATION_SET) {
                super.setRequestedOrientation(mPendingRequestedOrientation);
                mPendingRequestedOrientation = NO_REQUESTED_ORIENTATION_SET;
            }
        }
    }
}
