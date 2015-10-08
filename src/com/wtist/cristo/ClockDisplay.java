package com.wtist.cristo;

/**
 * Created by ce on 2015/10/8.
 */
public class ClockDisplay {
    private int minute = 0;
    private int second = 0;
    private int millsecond = 0;

    public void clear() {
        this.minute = 0;;
        this.second = 0;
        this.millsecond = 0;
    }

    public String refresh() {
        if (millsecond >= 999) {
            second++;
            millsecond = 0;
        } else {
            millsecond++;
        }

        if (second >= 59) {
            minute++;
            second = 0;
        }

        return toString();
    }

    @Override
    public String toString() {
        return String.format("%02d", minute) + ":" + String.format("%02d", second) + ":" + String.format("%03d", millsecond);
    }
}
