package com.xgw.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.xgw.androidkotlindemo.R;

public class CommonToolBar extends FrameLayout {

    private View layoutView;
    private FrameLayout layoutBack;
    private AppCompatImageView imageBack;
    private TextView toolbarTitle;
    private FrameLayout layoutMore;
    private TextView moreTitle;
    private TextView textBack;
    private AppCompatImageView moreImage;

    public CommonToolBar(@NonNull Context context) {
        this(context, null);
    }

    public CommonToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonToolBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttrs(context, attrs, defStyleAttr);
    }

    public void initCustomAttrs(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonToolBar, defStyleAttr, 0);
        int btnBackColor = typedArray.getColor(R.styleable.CommonToolBar_btn_back_color, ContextCompat.getColor(context, R.color.black_alpha_224));
        String backContent = typedArray.getString(R.styleable.CommonToolBar_back_content);

        int titleColor = typedArray.getColor(R.styleable.CommonToolBar_title_color, ContextCompat.getColor(context, R.color.black_alpha_224));
        float titleSize = typedArray.getDimensionPixelSize(R.styleable.CommonToolBar_title_size, 0);
        String titleContent = typedArray.getString(R.styleable.CommonToolBar_title_content);

        int moreTitleColor = typedArray.getColor(R.styleable.CommonToolBar_more_color, ContextCompat.getColor(context, R.color.black_alpha_224));
        float moreTitleSize = typedArray.getDimensionPixelSize(R.styleable.CommonToolBar_more_size, 0);
        String moreTitleContent = typedArray.getString(R.styleable.CommonToolBar_more_content);
        int moreImageRes = typedArray.getResourceId(R.styleable.CommonToolBar_more_image, 0);
        boolean showLeftBack = typedArray.getBoolean(R.styleable.CommonToolBar_show_left_back, true);

        typedArray.recycle();

        layoutView = LayoutInflater.from(context).inflate(R.layout.module_common_toolbar, this);

        layoutBack = layoutView.findViewById(R.id.layout_back);
        imageBack = layoutView.findViewById(R.id.toolbar_image_back);
        textBack = layoutView.findViewById(R.id.toolbar_text_back);
        toolbarTitle = layoutView.findViewById(R.id.toolbar_title);
        layoutMore = layoutView.findViewById(R.id.layout_more);
        moreTitle = layoutView.findViewById(R.id.more_title);
        moreImage = layoutView.findViewById(R.id.more_image);

        setBtnBackColor(btnBackColor);
        setBackTitleContent(backContent);

        setTitleColor(titleColor);
        setTitle(titleContent);
        setTitleSize(titleSize);

        setMoreTitleSize(moreTitleSize);

        setMoreImage(moreImageRes);

        setLeftBack(showLeftBack);

        isInEditMode();
    }

    public void setLeftBack(boolean isShow) {
        layoutView.findViewById(R.id.layout_back).setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setBackTitleContent(String titleContent) {
        textBack.setVisibility(TextUtils.isEmpty(titleContent) ? GONE : VISIBLE);
        imageBack.setVisibility(TextUtils.isEmpty(titleContent) ? VISIBLE : GONE);
        textBack.setText(titleContent);
    }

    public void setBtnBackColor(int btnBackColor) {
        if (btnBackColor == 0) {
            return;
        }
        Drawable originalBitmapDrawable = imageBack.getDrawable();
        imageBack.setImageDrawable(tintDrawable(originalBitmapDrawable, ColorStateList.valueOf(btnBackColor)));
    }

    public void setTitleColor(int titleColor) {
        if (titleColor == 0) {
            return;
        }
        toolbarTitle.setTextColor(titleColor);
    }

    public void setTitle(String titleContent) {
        toolbarTitle.setVisibility(VISIBLE);
        toolbarTitle.setText(titleContent);
    }

    public String getTitle() {
        return toolbarTitle.getText().toString();
    }

    public void setTitleSize(float titleSize) {
        if (titleSize == 0) {
            return;
        }
        toolbarTitle.setTextSize(titleSize);
    }

    public void setMoreTitleSize(float titleSize) {
        if (titleSize == 0) {
            return;
        }
        moreTitle.setTextSize(titleSize);
    }

    public void setMoreImage(int resId) {
        if (resId == 0) {
            return;
        }
        moreImage.setVisibility(VISIBLE);
        layoutMore.setVisibility(VISIBLE);
        moreImage.setImageResource(resId);
    }

    public Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public void addOnBackListener(OnClickListener listener) {
        layoutBack.setOnClickListener(listener);
    }

    public void addOnMoreListener(OnClickListener listener) {
        layoutMore.setOnClickListener(listener);
    }

    public void setMoreEnable(boolean enable) {
        layoutMore.setEnabled(enable);
    }
}
