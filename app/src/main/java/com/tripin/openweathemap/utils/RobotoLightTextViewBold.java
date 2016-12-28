package com.tripin.openweathemap.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoLightTextViewBold extends TextView {

    public RobotoLightTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RobotoLightTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoLightTextViewBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                                               "Roboto-Light.ttf");

        setTypeface(tf,Typeface.BOLD);
    }

}