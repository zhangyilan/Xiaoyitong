package com.dsw.calendar.theme;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/7/30.
 */
public class DefaultDayTheme implements IDayTheme {
    @Override
    public int colorSelectBG() {
        return Color.parseColor("#A5D8F5");
    }

    @Override
    public int colorSelectDay() {
        return Color.parseColor("#E81010");
    }

    @Override
    public int colorToday() {
        return Color.parseColor("#68CB00");
    }

    @Override
    public int colorMonthView() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int colorWeekday() {
        return Color.parseColor("#404040");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#404040");
    }

    @Override
    public int colorDecor() {
        return Color.parseColor("#68CB00");
    }

    @Override
    public int colorRest() {
        return Color.parseColor("#68CB00");
    }

    @Override
    public int colorWork() {
        return Color.parseColor("#F74C31");
    }

    @Override
    public int colorDesc() {
        return Color.parseColor("#919191");
    }

    @Override
    public int sizeDay() {
        return 50;
    }

    @Override
    public int sizeDecor() {
        return 10;
    }

    @Override
    public int sizeDesc() {
        return 30;
    }

    @Override
    public int dateHeight() {
        return 170;
    }

    @Override
    public int colorLine() {
        return Color.parseColor("#CBCBCB");
    }

    @Override
    public int smoothMode() {
        return 0;
    }
}
