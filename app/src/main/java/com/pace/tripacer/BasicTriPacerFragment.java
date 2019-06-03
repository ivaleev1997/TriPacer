package com.pace.tripacer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebasetestapp.customizedtimepicker.CustomizedTimePickerDialog;
import com.firebasetestapp.customizedtimepicker.TimePicker;
import com.firebasetestapp.customizedtimepicker.TimePickerNoHour;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import static com.pace.tripacer.MainActivity.HALF;
import static com.pace.tripacer.MainActivity.IMPERIAL;
import static com.pace.tripacer.MainActivity.IRONMAN;
import static com.pace.tripacer.MainActivity.LAYOUT;
import static com.pace.tripacer.MainActivity.METRIC;
import static com.pace.tripacer.MainActivity.OLYMPIC;
import static com.pace.tripacer.MainActivity.SAVED_DATA_KEY_DISTANCE;
import static com.pace.tripacer.MainActivity.SAVED_DATA_KEY_MEASURE;
import static com.pace.tripacer.MainActivity.SPRINT;

public class BasicTriPacerFragment extends Fragment {

    private View view;
    private int mDistanceId;

    private TextView mSwimDistance;
    private TextView mBicycleDistance;
    private TextView mRunDistance;

    private TextView mSwimSpeedMeasure;
    private TextView mBicycleSpeedMeasure;
    private TextView mRunSpeedMeasure;

    private TextView mSwimTotalTime;
    private TextView mSwimPace;
    private TextView mBicycleTotalTime;
    private TextView mBicycleSpeed;
    private TextView mRunTotalTime;
    private TextView mRunPace;
    private TextView mT1Time;
    private TextView mT2Time;

    private int mCurrentMeasurement;
    private CalcUtil mCalcUtil;
    private LiveData<Integer> mDistanceLiveData;
    private LiveData<Integer> mMeasureLiveData;

    public BasicTriPacerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        mCalcUtil = new CalcUtil();
        mDistanceLiveData = ((MainActivity)getActivity()).getLiveDataCurrentDistance();
        mMeasureLiveData = ((MainActivity)getActivity()).getLiveDataCurrentMeasure();

        getMeasureId();
        getDistanceId();
        initView();

        return view;
    }


    private void getDistanceId() {
        assert getArguments() != null;
        mDistanceId = getArguments().getInt(SAVED_DATA_KEY_DISTANCE);
    }

    private int getMeasureId() {
        assert getArguments() != null;
        mCurrentMeasurement = getArguments().getInt(SAVED_DATA_KEY_MEASURE);

        return mCurrentMeasurement;
    }

    void initView() {
        mSwimSpeedMeasure = view.findViewById(R.id.swim100);
        mBicycleSpeedMeasure = view.findViewById(R.id.bicSpeed);
        mRunSpeedMeasure = view.findViewById(R.id.runSpeed);

        mSwimDistance = view.findViewById(R.id.txtSwim);
        mBicycleDistance = view.findViewById(R.id.txtBicycle);
        mRunDistance = view.findViewById(R.id.txtRun);

        mSwimTotalTime = view.findViewById(R.id.timeSwim);
        mSwimTotalTime.setOnClickListener(view -> {
            TimeModel timeModel = new TimeModel(mSwimTotalTime.getText().toString());
            new CustomizedTimePickerDialog(getContext(), (view1, hourOfDay, minute, seconds) -> {
                //Toast.makeText(getContext(), CalcUtil.format(hourOfDay) + ":" + CalcUtil.format(minute) + ":" + CalcUtil.format(seconds), Toast.LENGTH_SHORT).show();
                mSwimTotalTime.setText(CalcUtil.format(hourOfDay) + ":" + CalcUtil.format(minute) + ":" + CalcUtil.format(seconds));
                mSwimPace.setText(mCalcUtil.calculateSwimPace(mDistanceId, mCurrentMeasurement, mSwimTotalTime.getText().toString()));
            }, timeModel.getHour(),timeModel.getMinute(),timeModel.getSeconds(),true).show();
        });

        mSwimPace = view.findViewById(R.id.paceSwim);
        mSwimPace.setOnClickListener(view -> {
            TimeModel timeModel = new TimeModel(mSwimPace.getText().toString());
            new CustomizedTimePickerDialog(getContext(), (view12, minute, seconds) -> {
                mSwimPace.setText(CalcUtil.format(minute) + ":" + CalcUtil.format(seconds));
                mSwimTotalTime.setText(mCalcUtil.calculateSwimTotalTime(mDistanceId, mCurrentMeasurement,mSwimPace.getText().toString()));
            }, timeModel.getMinute(),timeModel.getSeconds()).show();
        });

        mBicycleTotalTime = view.findViewById(R.id.bicycleTime);
        mBicycleTotalTime.setOnClickListener(view -> {
            TimeModel timeModel = new TimeModel(mBicycleTotalTime.getText().toString());
            new CustomizedTimePickerDialog(getContext(), new CustomizedTimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
                    mBicycleTotalTime.setText(CalcUtil.format(hourOfDay) + ":" + CalcUtil.format(minute) + ":" + CalcUtil.format(seconds));
                    //TODO: add calculation methods calculateBicyclePace..
                    mBicycleSpeed.setText(mCalcUtil.calculateBicyclePace(mDistanceId, mCurrentMeasurement, mBicycleTotalTime.getText().toString()));
                }
            }, timeModel.getHour(),timeModel.getMinute(),timeModel.getSeconds(),true).show();
        });

        mBicycleSpeed = view.findViewById(R.id.bicycleSpeed);
        mBicycleSpeed.setOnClickListener(view -> {
            TimeModel timeModel = new TimeModel(mBicycleSpeed.getText().toString());
            new CustomizedTimePickerDialog(getContext(), new CustomizedTimePickerDialog.OnTimeSetListenerNohour() {
                @Override
                public void onTimeSet(TimePickerNoHour view, int minute, int seconds) {
                    mBicycleSpeed.setText(CalcUtil.format(minute) + "." + CalcUtil.format(seconds));
                    mBicycleTotalTime.setText(mCalcUtil.calculateBicycleTotalTime(mDistanceId, mCurrentMeasurement, mBicycleSpeed.getText().toString()));
                }
            }, timeModel.getMinute(), timeModel.getSeconds()).show();
        });

        mRunTotalTime = view.findViewById(R.id.runTime);
        mRunTotalTime.setOnClickListener(view -> {
            TimeModel timeModel = new TimeModel(mRunTotalTime.getText().toString());
            new CustomizedTimePickerDialog(getContext(), new CustomizedTimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
                    mRunTotalTime.setText(CalcUtil.format(hourOfDay) + ":" + CalcUtil.format(minute) + ":" + CalcUtil.format(seconds));
                    mRunPace.setText(mCalcUtil.calculateRunPace(mDistanceId, mCurrentMeasurement, mRunTotalTime.getText().toString()));
                }
            }, timeModel.getHour(), timeModel.getMinute(), timeModel.getSeconds(),true).show();
        });

        mRunPace = view.findViewById(R.id.runPace);
        mRunPace.setOnClickListener(view -> {
            TimeModel timeModel = new TimeModel(mRunPace.getText().toString());
            new CustomizedTimePickerDialog(getContext(), new CustomizedTimePickerDialog.OnTimeSetListenerNohour() {
                @Override
                public void onTimeSet(TimePickerNoHour view, int minute, int seconds) {
                    mRunPace.setText(CalcUtil.format(minute) + ":" + CalcUtil.format(seconds));
                    mRunTotalTime.setText(mCalcUtil.calculateRunTotalTime(mDistanceId, mCurrentMeasurement, mRunPace.getText().toString()));
                }
            }, timeModel.getMinute(),timeModel.getSeconds()).show();
        });

        mT1Time = view.findViewById(R.id.t1);
        mT1Time.setOnClickListener(view -> {
            TimeModel timeModel = new TimeModel(mT1Time.getText().toString());
            new CustomizedTimePickerDialog(getContext(), new CustomizedTimePickerDialog.OnTimeSetListenerNohour() {
                @Override
                public void onTimeSet(TimePickerNoHour view, int minute, int seconds) {
                    mT1Time.setText(CalcUtil.format(minute) + ":" + CalcUtil.format(seconds));
                }
            }, timeModel.getMinute(), timeModel.getSeconds()).show();
        });

        mT2Time = view.findViewById(R.id.t2);
        mT2Time.setOnClickListener(view -> {
            TimeModel timeModel = new TimeModel(mT2Time.getText().toString());
            new CustomizedTimePickerDialog(getContext(), new CustomizedTimePickerDialog.OnTimeSetListenerNohour() {
                @Override
                public void onTimeSet(TimePickerNoHour view, int minute, int seconds) {
                    mT2Time.setText(CalcUtil.format(minute) + ":" + CalcUtil.format(seconds));
                }
            }, timeModel.getMinute(), timeModel.getSeconds()).show();
        });
        setDistancesTextViews();
        setMeasureTextViews();
        //TODO: Create calculation..
    }
    
    private void setMeasureTextViews() {
        switch (mCurrentMeasurement) {
            case METRIC:
                mSwimSpeedMeasure.setText(R.string.swim_unit_met);
                mBicycleSpeedMeasure.setText(R.string.bicycle_unit_met);
                mRunSpeedMeasure.setText(R.string.run_unit_met);
                break;

            case IMPERIAL:
                mSwimSpeedMeasure.setText(R.string.swim_unit_imp);
                mBicycleSpeedMeasure.setText(R.string.bicycle_unit_imp);
                mRunSpeedMeasure.setText(R.string.run_unit_imp);
                break;
            default:
                break;
        }
    }

    private void setDistancesTextViews() {
        switch (mDistanceId) {
            case SPRINT:
                if (mCurrentMeasurement == METRIC) {
                    mSwimDistance.setText(R.string.swim_sprint_met);
                    mBicycleDistance.setText(R.string.bicycle_sprint_met);
                    mRunDistance.setText(R.string.run_sprint_met);
                } else {
                    mSwimDistance.setText(R.string.swim_sprint_imp);
                    mBicycleDistance.setText(R.string.bicycle_sprint_imp);
                    mRunDistance.setText(R.string.run_sprint_imp);
                }
                break;

            case OLYMPIC:
                if (mCurrentMeasurement == METRIC) {
                    mSwimDistance.setText(R.string.swim_olympic_met);
                    mBicycleDistance.setText(R.string.bicycle_olympic_met);
                    mRunDistance.setText(R.string.run_olympic_met);
                } else {
                    mSwimDistance.setText(R.string.swim_olympic_imp);
                    mBicycleDistance.setText(R.string.bicycle_olympic_imp);
                    mRunDistance.setText(R.string.run_olympic_imp);
                }
                break;

            case HALF:
                if (mCurrentMeasurement == METRIC) {
                    mSwimDistance.setText(R.string.swim_half_met);
                    mBicycleDistance.setText(R.string.bicycle_half_met);
                    mRunDistance.setText(R.string.run_half_met);
                } else {
                    mSwimDistance.setText(R.string.swim_half_imp);
                    mBicycleDistance.setText(R.string.bicycle_half_imp);
                    mRunDistance.setText(R.string.run_half_imp);
                }
                break;

            case IRONMAN:
                if (mCurrentMeasurement == METRIC) {
                    mSwimDistance.setText(R.string.swim_iron_met);
                    mBicycleDistance.setText(R.string.bicycle_iron_met);
                    mRunDistance.setText(R.string.run_iron_met);
                } else {
                    mSwimDistance.setText(R.string.swim_iron_imp);
                    mBicycleDistance.setText(R.string.bicycle_iron_imp);
                    mRunDistance.setText(R.string.run_iron_imp);
                }
                break;
            default:
                break;
        }
    }

    void changeDistance(int distance) {
        mDistanceId = distance;

        setDistancesTextViews();
    }

    void changeMeasureSystem(int measurement) {
        mCurrentMeasurement = measurement;

        setMeasureTextViews();
        setDistancesTextViews();
    }

    int getCurrentDistanceId() {
        return mDistanceId;
    }

    String getSwimDistanceTotalTime() {
        return mSwimTotalTime.getText().toString();
    }

    String getBicycleDistanceTotalTime() {
        return mBicycleTotalTime.getText().toString();
    }

    String getRunDistancetotalTime() {
        return mRunTotalTime.getText().toString();
    }

    String getT1TotalTime() {
        return mT1Time.getText().toString();
    }

    String getT2TotalTime() {
        return mT2Time.getText().toString();
    }
}
