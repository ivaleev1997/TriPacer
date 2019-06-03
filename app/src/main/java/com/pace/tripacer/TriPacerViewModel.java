package com.pace.tripacer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TriPacerViewModel extends ViewModel {

    private int mCurrentDistance = MainActivity.DEFAULT_DISTANCE;
    private int mCurrentMeasure = MainActivity.METRIC;
    private TriPacerInfo mTriPacerInfo;

    MutableLiveData<TriPacerInfo> mTriPacerInfoLiveData;
    MutableLiveData<Integer> mMeasureLiveData;
    MutableLiveData<Integer> mDistanceLiveData;


    public LiveData<TriPacerInfo> getTriPaceInfoData() {
        if (mTriPacerInfoLiveData == null) {
            mTriPacerInfoLiveData = new MutableLiveData<>();
        }

        return mTriPacerInfoLiveData;
    }

    public LiveData<Integer> getMeasureData() {
        if (mMeasureLiveData == null) {
            mMeasureLiveData = new MutableLiveData<>();
        }

        return mMeasureLiveData;
    }

    public LiveData<Integer> getDistanceData() {
        if (mDistanceLiveData == null) {
            mDistanceLiveData = new MutableLiveData<>();
        }

        return mDistanceLiveData;
    }

    public TriPacerViewModel() {
        mTriPacerInfo = new TriPacerInfo();
    }

    public void setCurrentDistance(int currentDistance) {
        mCurrentDistance = currentDistance;

        mDistanceLiveData.setValue(mCurrentDistance);
    }

    public void setTriPacerInfo(TriPacerInfo triPacerInfo) {
        mTriPacerInfo = triPacerInfo;
    }

    public void setCurrentMeasure(int currentMeasure) {
        mCurrentMeasure = currentMeasure;

        mMeasureLiveData.setValue(mCurrentMeasure);
    }


}
