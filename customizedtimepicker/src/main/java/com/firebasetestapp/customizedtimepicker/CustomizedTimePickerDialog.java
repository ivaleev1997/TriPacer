package com.firebasetestapp.customizedtimepicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import java.util.Calendar;

public class CustomizedTimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, TimePicker.OnTimeChangedListener, TimePickerNoHour.OnTimeChangedListener {

    /**
     * The callback interface used to indicate the user is done filling in
     * the time (they clicked on the 'Set' button).
     */
    public interface OnTimeSetListener {
        /**
         * @param view The view associated with this listener.
         * @param hourOfDay The hour that was set.
         * @param minute The minute that was set.
         */
        void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds);
    }

    public interface OnTimeSetListenerNohour {
        /**
         * @param view The view associated with this listener.
         * @param minute The minute that was set.
         */
        void onTimeSet(TimePickerNoHour view, int minute, int seconds);

    }

    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String SECONDS = "seconds";
    private static final String IS_24_HOUR = "is24hour";
    private static final String IS_NO_HOUR = "no_hour";

    private TimePicker mTimePicker;
    private TimePickerNoHour mTimePickerNoHour;
    private OnTimeSetListener mCallback;
    private OnTimeSetListenerNohour mCallBackNoHour;

    private final Calendar mCalendar;
    private final java.text.DateFormat mDateFormat;

    int mInitialHourOfDay;
    int mInitialMinute;
    int mInitialSeconds;
    boolean mIs24HourView;
    boolean mIfNoHour = false;

    /**
     * @param context Parent.
     * @param callBack How parent is notified.
     * @param hourOfDay The initial hour.
     * @param minute The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public CustomizedTimePickerDialog(Context context,
                              OnTimeSetListener callBack,
                              int hourOfDay, int minute, int seconds, boolean is24HourView) {

        super(context, 0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCallback = callBack;
        mInitialHourOfDay = hourOfDay;
        mInitialMinute = minute;
        mInitialSeconds = seconds;
        mIs24HourView = is24HourView;

        mDateFormat = DateFormat.getTimeFormat(context);
        mCalendar = Calendar.getInstance();
        updateTitle(mInitialHourOfDay, mInitialMinute, mInitialSeconds);

        setButton(context.getText(R.string.time_set), this);
        setButton2(context.getText(R.string.cancel), (OnClickListener) null);
        //setIcon(android.R.drawable.ic_dialog_time);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker_dialog, null);
        setView(view);
        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);

        // initialize state
        mTimePicker.setCurrentHour(mInitialHourOfDay);
        mTimePicker.setCurrentMinute(mInitialMinute);
        mTimePicker.setCurrentSecond(mInitialSeconds);
        mTimePicker.setIs24HourView(mIs24HourView);
        mTimePicker.setOnTimeChangedListener(this);
    }

    /**
     * @param context Parent.
     * @param callBack How parent is notified.
     * @param minute The initial minute.
     */
    public CustomizedTimePickerDialog(Context context,
                                      OnTimeSetListenerNohour callBack,
                                      int minute, int seconds) {
        super(context, 0);
        mIfNoHour = true;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCallBackNoHour = callBack;
        //mInitialHourOfDay = hourOfDay;
        mInitialMinute = minute;
        mInitialSeconds = seconds;
        //mIs24HourView = is24HourView;

        mDateFormat = DateFormat.getTimeFormat(context);
        mCalendar = Calendar.getInstance();
        updateTitle(mInitialHourOfDay, mInitialMinute, mInitialSeconds);

        setButton(context.getText(R.string.time_set), this);
        setButton2(context.getText(R.string.cancel), (OnClickListener) null);
        //setIcon(android.R.drawable.ic_dialog_time);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker_dialog_no_hour, null);
        setView(view);
        mTimePickerNoHour = (TimePickerNoHour) view.findViewById(R.id.timePickerNoHour);

        mTimePickerNoHour.setCurrentMinute(mInitialMinute);
        mTimePickerNoHour.setCurrentSeconds(mInitialSeconds);
        mTimePickerNoHour.setOnTimeChangedListener(this);
    }

    public void onClick(DialogInterface dialog, int which) {
        if (!mIfNoHour) {
            if (mCallback != null) {
                mTimePicker.clearFocus();
                mCallback.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                        mTimePicker.getCurrentMinute(), mTimePicker.getCurrentSeconds());
            }
        } else {
            if (mCallBackNoHour != null) {
                mTimePickerNoHour.clearFocus();
                mCallBackNoHour.onTimeSet(mTimePickerNoHour, mTimePickerNoHour.getCurrentMinute(), mTimePickerNoHour.getCurrentSeconds());
            }
        }

    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute, int seconds) {
        updateTitle(hourOfDay, minute, seconds);
    }

    @Override
    public void onTimeChanged(TimePickerNoHour view, int minute, int seconds) {
        updateTitle(0, minute, seconds);
    }

    public void updateTime(int hourOfDay, int minutOfHour, int seconds) {
        mTimePicker.setCurrentHour(hourOfDay);
        mTimePicker.setCurrentMinute(minutOfHour);
        mTimePicker.setCurrentSecond(seconds);
    }

    private void updateTitle(int hour, int minute, int seconds) {
        String sMin = String.format("%02d", minute);
        String sSec = String.format("%02d", seconds);

        if (!mIfNoHour) {
            String sHour = String.format("%02d", hour);
            setTitle(sHour + ":" + sMin + ":" + sSec);
        }
        else
            setTitle(sMin + ":" + sSec);

    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        if (!mIfNoHour) {
            state.putInt(HOUR, mTimePicker.getCurrentHour());
            state.putInt(MINUTE, mTimePicker.getCurrentMinute());
            state.putInt(SECONDS, mTimePicker.getCurrentSeconds());
            state.putBoolean(IS_24_HOUR, mTimePicker.is24HourView());
        } else {
            state.putInt(MINUTE, mTimePickerNoHour.getCurrentMinute());
            state.putInt(SECONDS, mTimePickerNoHour.getCurrentSeconds());
        }

        state.putBoolean(IS_NO_HOUR, mIfNoHour);

        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean flag = savedInstanceState.getBoolean(IS_NO_HOUR);

        int hour = 0;

        if (!flag) {
            hour = savedInstanceState.getInt(HOUR);
        }

        int minute = savedInstanceState.getInt(MINUTE);
        int seconds = savedInstanceState.getInt(SECONDS);

        if (!flag) {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);
            mTimePicker.setCurrentSecond(seconds);
            mTimePicker.setIs24HourView(savedInstanceState.getBoolean(IS_24_HOUR));
            mTimePicker.setOnTimeChangedListener(this);
        }
        else {
            mTimePickerNoHour.setCurrentMinute(minute);
            mTimePickerNoHour.setCurrentSeconds(seconds);
            mTimePickerNoHour.setOnTimeChangedListener(this);
        }


        updateTitle(hour, minute, seconds);
    }


}
