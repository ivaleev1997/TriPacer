package com.pace.tripacer;

import java.util.regex.Pattern;

public class TimeModel {
    private static final int HH_MM_SS = 3;
    private static final int MM_SS = 2;
    private int mHour;
    private int mMinute;
    private int mSeconds;

    public TimeModel(String timestring) {
        String values[] = timestring.split(":");
        if (values.length == HH_MM_SS) {
            mHour = Integer.valueOf(values[0]);
            mMinute = Integer.valueOf(values[1]);
            mSeconds = Integer.valueOf(values[2]);
        } else if (values.length == MM_SS) {
            mMinute = Integer.valueOf(values[0]);
            mSeconds = Integer.valueOf(values[1]);
        } else if ((values = timestring.split(Pattern.quote("."))).length == MM_SS) {
            mMinute = Integer.valueOf(values[0]);
            mSeconds = Integer.valueOf(values[1]);
        }
    }

    public int getHour() {
        return mHour;
    }

    public int getMinute() {
        return mMinute;
    }

    public int getSeconds() {
        return mSeconds;
    }
}
