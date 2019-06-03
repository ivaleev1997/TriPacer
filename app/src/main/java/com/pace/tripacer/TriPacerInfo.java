package com.pace.tripacer;

public class TriPacerInfo {
    private int mCurrentDistance;
    private int mCurrentMeasure;

    private String mTotalTime;
    private String mSwimTotalTime;
    private String mSwimPace;
    private String mT1Time;
    private String mBicycleTotalTime;
    private String mBikePace;
    private String mT2time;
    private String mRunTotalTime;
    private String mRunPace;

    public TriPacerInfo() {
        mCurrentDistance = MainActivity.DEFAULT_DISTANCE;
        mCurrentMeasure = MainActivity.METRIC;

        mTotalTime = "00:00:00";
        mSwimTotalTime = "00:00:00";
        mSwimPace = "00:00";
        mT1Time = "00:00";
        mBicycleTotalTime = "00:00:00";
        mBikePace = "00.00";
        mT2time = "00:00";
        mRunTotalTime = "00:00:00";
        mRunPace = "00:00";
    }

    public int getCurrentDistance() {
        return mCurrentDistance;
    }

    public int getCurrentMeasure() {
        return mCurrentMeasure;
    }

    public void setCurrentMeasure(int currentMeasure) {
        mCurrentMeasure = currentMeasure;
    }

    public void setCurrentDistance(int currentDistance) {
        mCurrentDistance = currentDistance;
    }

    public String getTotalTime() {
        return mTotalTime;
    }

    public void setTotalTime(String totalTime) {
        mTotalTime = totalTime;
    }

    public String getSwimTotalTime() {
        return mSwimTotalTime;
    }

    public void setSwimTotalTime(String swimTotalTime) {
        mSwimTotalTime = swimTotalTime;
    }

    public String getT1Time() {
        return mT1Time;
    }

    public void setT1Time(String t1Time) {
        mT1Time = t1Time;
    }

    public String getBicycleTotalTime() {
        return mBicycleTotalTime;
    }

    public void setBicycleTotalTime(String bicycleTotalTime) {
        mBicycleTotalTime = bicycleTotalTime;
    }

    public String getT2time() {
        return mT2time;
    }

    public void setT2time(String t2time) {
        mT2time = t2time;
    }

    public String getRunTotalTime() {
        return mRunTotalTime;
    }

    public void setRunTotalTime(String runTotalTime) {
        mRunTotalTime = runTotalTime;
    }

    public String getSwimPace() {
        return mSwimPace;
    }

    public void setSwimPace(String swimPace) {
        mSwimPace = swimPace;
    }

    public String getBikePace() {
        return mBikePace;
    }

    public void setBikePace(String bikePace) {
        mBikePace = bikePace;
    }

    public String getRunPace() {
        return mRunPace;
    }

    public void setRunPace(String runPace) {
        mRunPace = runPace;
    }

}
