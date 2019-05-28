package com.firebasetestapp.customizedtimepicker;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import java.util.Calendar;

public class TimePickerNoHour extends FrameLayout {

    /**
     * A no-op callback used in the constructor to avoid null checks
     * later in the code.
     */
    private static final TimePickerNoHour.OnTimeChangedListener NO_OP_CHANGE_LISTENER = new TimePickerNoHour.OnTimeChangedListener() {

        public void onTimeChanged(TimePickerNoHour view, int minute, int seconds) {
        }
    };

    public static final NumberPicker.Formatter TWO_DIGIT_FORMATTER =
            new NumberPicker.Formatter() {

                @Override
                public String format(int value) {
                    return String.format("%02d", value);
                }
            };

    // state
    private int mCurrentMinute = 0; // 0-59
    private int mCurrentSeconds = 0; // 0-59

    // ui components
    private final NumberPicker mMinutePicker;
    private final NumberPicker mSecondPicker;

    // callbacks
    private TimePickerNoHour.OnTimeChangedListener mOnTimeChangedListener;

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * @param view The view associated with this listener.
         * @param minute The current minute.
         * @param seconds The current second.
         */
        void onTimeChanged(TimePickerNoHour view, int minute, int seconds);
    }

    public TimePickerNoHour(Context context) {
        this(context, null);
    }

    public TimePickerNoHour(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePickerNoHour(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.time_picker_widget_no_hour,
                this, // we are the parent
                true);

        // digits of minute
        mMinutePicker = (NumberPicker) findViewById(R.id.minuteNh);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setFormatter(TWO_DIGIT_FORMATTER);
        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                mCurrentMinute = newVal;
                onTimeChanged();
            }
        });

        // digits of seconds
        mSecondPicker = (NumberPicker) findViewById(R.id.secondsNh);
        mSecondPicker.setMinValue(0);
        mSecondPicker.setMaxValue(59);
        mSecondPicker.setFormatter(TWO_DIGIT_FORMATTER);
        mSecondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurrentSeconds = newVal;
                onTimeChanged();
            }
        });

        // initialize to current time
        Calendar cal = Calendar.getInstance();
        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);

        setCurrentMinute(cal.get(Calendar.MINUTE));
        setCurrentSeconds(cal.get(Calendar.SECOND));

        if (!isEnabled()) {
            setEnabled(false);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mMinutePicker.setEnabled(enabled);
        mSecondPicker.setEnabled(enabled);
    }

    /**
     * Used to save / restore state of time picker
     */
    private static class SavedState extends BaseSavedState {

        private final int mMinute;
        private final int mSecond;

        private SavedState(Parcelable superState, int minute, int second) {
            super(superState);
            mMinute = minute;
            mSecond = second;
        }

        private SavedState(Parcel in) {
            super(in);
            mMinute = in.readInt();
            mSecond = in.readInt();
        }

        public int getSecond() {
            return mSecond;
        }

        public int getMinute() {
            return mMinute;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mMinute);
            dest.writeInt(mSecond);
        }

        public static final Parcelable.Creator<TimePickerNoHour.SavedState> CREATOR
                = new Creator<TimePickerNoHour.SavedState>() {
            public TimePickerNoHour.SavedState createFromParcel(Parcel in) {
                return new TimePickerNoHour.SavedState(in);
            }

            public TimePickerNoHour.SavedState[] newArray(int size) {
                return new TimePickerNoHour.SavedState[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new TimePickerNoHour.SavedState(superState, mCurrentMinute, mCurrentSeconds);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        TimePickerNoHour.SavedState ss = (TimePickerNoHour.SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentMinute(ss.getMinute());
        setCurrentSeconds(ss.getSecond());
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     * @param onTimeChangedListener the callback, should not be null.
     */
    public void setOnTimeChangedListener(TimePickerNoHour.OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    /**
     * @return The current minute.
     */
    public Integer getCurrentMinute() {
        return mCurrentMinute;
    }

    /**
     * Set the current minute (0-59).
     */
    public void setCurrentMinute(Integer currentMinute) {
        this.mCurrentMinute = currentMinute;
        updateMinuteDisplay();
    }

    /**
     * @return The current minute.
     */
    public Integer getCurrentSeconds() {
        return mCurrentSeconds;
    }

    /**
     * Set the current second (0-59).
     */
    public void setCurrentSeconds(Integer currentSecond) {
        this.mCurrentSeconds = currentSecond;
        updateSecondsDisplay();
    }

    @Override
    public int getBaseline() {
        return mMinutePicker.getBaseline();
    }

    private void onTimeChanged() {
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds());
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private void updateMinuteDisplay() {
        mMinutePicker.setValue(mCurrentMinute);
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds());
    }

    /**
     * Set the state of the spinners appropriate to the current second.
     */
    private void updateSecondsDisplay() {
        mSecondPicker.setValue(mCurrentSeconds);
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds());
    }
}
