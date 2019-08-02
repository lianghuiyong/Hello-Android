package com.xgw.common.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xgw.androidkotlindemo.R;

public class CommonStatusBar extends FrameLayout {
    public CommonStatusBar(@NonNull Context context) {
        this(context, null);
    }

    public CommonStatusBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonStatusBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttrs(context, attrs, defStyleAttr);
    }

    public void initCustomAttrs(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.common_status_bar, this);

        if (layoutView.isInEditMode()) {
            return;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int height;
                height = getStatusBarHeight(getContext());
                if (height == 0) {
                    height = heightMeasureSpec;
                }
                setMeasuredDimension(widthMeasureSpec, height);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //适配Android状态栏的字体颜色为暗色
    public void setStatusBarDark(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int vis = activity.getWindow().getDecorView().getSystemUiVisibility();
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(vis);
        }
    }

    //适配Android状态栏的字体颜色为白色
    public void setStatusBarLight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int vis = activity.getWindow().getDecorView().getSystemUiVisibility();

            vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(vis);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
