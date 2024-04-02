package com.example.puzzle;

import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.TextView;

class DynamicSizing {
    public static final int MAX_FONT_SIZE = 200;
    public static final int MIN_FONT_SIZE = 1;

    public static int setFontSizeToFitInView(TextView tv) {
        int fontSize = MAX_FONT_SIZE;
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        tv.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int lines = tv.getLineCount();
        if (lines > 0) {
            while (lines != 1 && fontSize > MIN_FONT_SIZE + 2) {
                fontSize--;
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                tv.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                lines = tv.getLineCount();
            }
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        }
        return fontSize;
    }

    public static void fillGui(TextView[] tvs, String[] scrambledText) {
        int minFontSize = MAX_FONT_SIZE;
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setText(scrambledText[i]);
            tvs[i].setWidth(tvs[i].getLayoutParams().width); // Giả sử params đã được thiết lập ở một nơi khác
            tvs[i].setPadding(20, 5, 20, 5);
            int fontSize = setFontSizeToFitInView(tvs[i]);
            if (minFontSize > fontSize) {
                minFontSize = fontSize;
            }
        }
        for (TextView tv : tvs) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, minFontSize);
        }
    }
}
