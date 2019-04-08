package com.pace.tripacer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.pace.tripacer.MainActivity.IMPERIAL;
import static com.pace.tripacer.MainActivity.LAYOUT;
import static com.pace.tripacer.MainActivity.METRIC;
import static com.pace.tripacer.MainActivity.SAVED_DATA_KEY_DISTANCE;
import static com.pace.tripacer.MainActivity.SAVED_DATA_KEY_MEASURE;

public class BasicTriPacerFragment extends Fragment {

    private View view;
    private int mDistanceId;

    private TextView mSwimDistance;
    private TextView mBicycleDistance;
    private TextView mRunDistance;

    private TextView mSwimSpeedMeasure;
    private TextView mBicycleSpeedMeasure;
    private TextView mRunSpeedMeasure;

    private int mCurrentMeasurement;

    public BasicTriPacerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

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

        setDistancesTextViews();
        setMeasureTextViews();

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
            case R.string.sprint:
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
            case R.string.olympic:
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
            case R.string.half:
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
            case R.string.iron:
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

    void changeMeasureSystem(int measurment) {
        mCurrentMeasurement = measurment;

        setMeasureTextViews();
        setDistancesTextViews();
    }

    int getCurrentDistanceId() {
        return mDistanceId;
    }



}
