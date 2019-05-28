package com.pace.tripacer;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.pace.tripacer.MainActivity.HALF;
import static com.pace.tripacer.MainActivity.IRONMAN;
import static com.pace.tripacer.MainActivity.METRIC;
import static com.pace.tripacer.MainActivity.OLYMPIC;
import static com.pace.tripacer.MainActivity.SPRINT;

public class Calculation {

    private static final String REGEX = ":";
    private static final int TIME_MOD = 60;
    //private static final int YARD_IN_METER = 1760;
    private static final int SWIM_DISTANCE_PACE = 100;
    private static final int METRES_IN_KM = 1000;
    private static final int SECONDS_IN_HOUR = 3600;

    private static final int sprint_distance_metric_swim = 750;
    private static final double sprint_distance_imperial_swim = 820;//y
    private static final int olympic_distance_metric_swim = 1500;
    private static final double olympic_distance_imperial_swim = 1640;//y
    private static final int half_distance_metric_swim = 1900;
    private static final double half_distance_imperial_swim = 2078;//y
    private static final int iron_distance_metric_swim = 3800;
    private static final double iron_distance_imperial_swim = 4156;//y

    private static final int sprint_distance_metric_bicycle = 20;
    private static final double sprint_distance_imperial_bicycle = 12.4;
    private static final int olympic_distance_metric_bicycle = 40;
    private static final double olympic_distance_imperial_bicycle = 24.9;
    private static final int half_distance_metric_bicycle = 90;
    private static final double half_distance_imperial_bicycle = 55.9;
    private static final int iron_distance_metric_bicycle = 180;
    private static final double iron_distance_imperial_bicycle = 111.8;

    private static final int sprint_distance_metric_run = 5;
    private static final double sprint_distance_imperial_run = 3.1;
    private static final int olympic_distance_metric_run = 10;
    private static final double olympic_distance_imperial_run = 6.2;
    private static final double half_distance_metric_run = 21.1;
    private static final double half_distance_imperial_run = 13.1;
    private static final double iron_distance_metric_run = 42.195;
    private static final double iron_distance_imperial_run = 26.2;

    private String mTotalTime;
    private String mSwimTotalTime;
    private String mT1Time;
    private String mBicycleTotalTime;
    private String mT2time;
    private String mRunTotalTime;

    private String mSwimPace;
    private String mBicePace;
    private String mRunPace;


    public Calculation(String swim, String t1, String bike, String t2, String run) {
        mSwimTotalTime = swim;
        mT1Time = t1;
        mBicycleTotalTime = bike;
        mT2time = t2;
        mRunTotalTime = run;

        calculate();
    }
    public Calculation() {}

    public String getTotalTime() {
        return mTotalTime;
    }

    private void calculate() {
        StringBuilder totalTime;
        String arraySwim[] = mSwimTotalTime.split(REGEX);
        String arrayT1[] = mT1Time.split(REGEX);
        String arrayBicycle[] = mBicycleTotalTime.split(REGEX);
        String arrayT2[] = mT2time.split(REGEX);
        String arrayRun[] = mRunTotalTime.split(REGEX);

        int seconds = Integer.valueOf(arraySwim[2]) + Integer.valueOf(arrayT1[1]) + Integer.valueOf(arrayBicycle[2]) + Integer.valueOf(arrayT2[1]) + Integer.valueOf(arrayRun[2]);
        int minutes = seconds/TIME_MOD + Integer.valueOf(arraySwim[1]) + Integer.valueOf(arrayT1[0]) + Integer.valueOf(arrayBicycle[1]) + Integer.valueOf(arrayT2[0]) + Integer.valueOf(arrayRun[1]);
        seconds = seconds%TIME_MOD;
        int hours = minutes/TIME_MOD + Integer.valueOf(arraySwim[0]) + Integer.valueOf(arrayBicycle[0]) + Integer.valueOf(arrayRun[0]);
        minutes = minutes%TIME_MOD;

        totalTime = new StringBuilder(format(hours));
        totalTime.append(REGEX);
        totalTime.append(format(minutes));
        totalTime.append(REGEX);
        totalTime.append(format(seconds));
        mTotalTime = totalTime.toString();
    }

    public static String format(int value) {
        return String.format("%02d", value);
    }

    public String calculateSwimPace(int distance, int systemMeasure, String timeOfDistance) {
        StringBuilder result = new StringBuilder();
        String arrayTime[] = timeOfDistance.split(REGEX);
        int seconds = (Integer.valueOf(arrayTime[0]) * SECONDS_IN_HOUR) + (Integer.valueOf(arrayTime[1]) * TIME_MOD) + Integer.valueOf(arrayTime[2]);
        //double speed = (double) SWIM_DISTANCE_PACE / seconds_in_100;
        double seconds_total_speed = 1;
        switch (distance) {
            case SPRINT:
                if (systemMeasure == METRIC) {
                    //int pace_seconds = sprint_distance_metric_swim / seconds;
                    seconds_total_speed = (double) sprint_distance_metric_swim / seconds;
                } else {
                    seconds_total_speed = (sprint_distance_imperial_swim) / seconds;
                }

                break;
            case OLYMPIC:
                if (systemMeasure == METRIC) {
                    seconds_total_speed = (double) olympic_distance_metric_swim / seconds;
                } else {
                    seconds_total_speed = (olympic_distance_imperial_swim) / seconds;
                }

                break;
            case HALF:
                if (systemMeasure == METRIC) {
                    seconds_total_speed = (double) half_distance_metric_swim / seconds;
                } else {
                    seconds_total_speed = (half_distance_imperial_swim) / seconds;
                }

                break;
            case IRONMAN:
                if (systemMeasure == METRIC) {
                    seconds_total_speed = (double) iron_distance_metric_swim / seconds;
                } else {
                    seconds_total_speed = (iron_distance_imperial_swim) / seconds;
                }

                break;
            default:
                break;
        }

        double speed_per_100 = ((double) SWIM_DISTANCE_PACE / seconds_total_speed);//m/s or yrd/s
        int minutes_per_100 = (int) speed_per_100 / TIME_MOD;
        int seconds_per_100 = (int) speed_per_100 % TIME_MOD;

        result.append(format(minutes_per_100));
        result.append(REGEX);
        result.append(format(seconds_per_100));

        mSwimPace = result.toString();

        return result.toString();
    }

    public String calculateSwimTotalTime(int distance, int systemMeasure, String paceOfDistance) {
        StringBuilder result = new StringBuilder();
        String arrayTime[] = paceOfDistance.split(REGEX);
        int seconds_pace = (Integer.valueOf(arrayTime[0]) * TIME_MOD) + (Integer.valueOf(arrayTime[1]));
        //double speed = (double) SWIM_DISTANCE_PACE / seconds_in_100;
        double seconds_total = 1;
        switch (distance) {
            case SPRINT:
                if (systemMeasure == METRIC) {
                    seconds_total = (double)sprint_distance_metric_swim / ((double) SWIM_DISTANCE_PACE / seconds_pace);// s
                } else {
                    seconds_total = (sprint_distance_imperial_swim) / ((double) SWIM_DISTANCE_PACE / seconds_pace);// s
                }
                break;
            case OLYMPIC:
                if (systemMeasure == METRIC) {
                    seconds_total = (double)olympic_distance_metric_swim / ((double) (SWIM_DISTANCE_PACE / seconds_pace));// m/s
                } else {
                    seconds_total = (olympic_distance_imperial_swim) / ((double) SWIM_DISTANCE_PACE / seconds_pace);// s
                }
                break;
            case HALF:
                if (systemMeasure == METRIC) {
                    /*double speed_ = ((double)SWIM_DISTANCE_PACE / seconds_pace);
                    double test_ = half_distance_metric_swim / speed_;*/
                    seconds_total = (double)half_distance_metric_swim / ((double)SWIM_DISTANCE_PACE / seconds_pace);// m/s
                    //System.out.println(test_);
                } else {
                    seconds_total = (half_distance_imperial_swim) / ((double) SWIM_DISTANCE_PACE / seconds_pace);// s
                }
                break;
            case IRONMAN:
                if (systemMeasure == METRIC) {
                    seconds_total = (double)iron_distance_metric_swim / ((double) SWIM_DISTANCE_PACE / seconds_pace);// m/s
                } else {
                    seconds_total = (iron_distance_imperial_swim) / ((double) SWIM_DISTANCE_PACE / seconds_pace);// s
                }
                break;
            default:
                break;
        }

        int hour = (int) (seconds_total / SECONDS_IN_HOUR);
        int minutes = (int) (seconds_total % SECONDS_IN_HOUR) / 60;
        int seconds = (int) (seconds_total % SECONDS_IN_HOUR) % 60;

        result.append(format(hour));
        result.append(REGEX);
        result.append(format(minutes));
        result.append(REGEX);
        result.append(format(seconds));

        mSwimTotalTime = result.toString();

        return result.toString();
    }

    public String calculateBicyclePace(int distance, int systemMeasure, String timeOfDistance) {
        StringBuilder result = new StringBuilder();
        String arrayTime[] = timeOfDistance.split(REGEX);
        int seconds_of_distance = (Integer.valueOf(arrayTime[0]) * SECONDS_IN_HOUR) + (Integer.valueOf(arrayTime[1]) * TIME_MOD) + Integer.valueOf(arrayTime[2]);
        double speed_km_or_mi_per_h = 1;
        switch (distance) {
            case SPRINT:
                if (systemMeasure == METRIC)
                    speed_km_or_mi_per_h = (double)(sprint_distance_metric_bicycle * SECONDS_IN_HOUR) / seconds_of_distance;
                else
                    speed_km_or_mi_per_h = (double)(sprint_distance_imperial_bicycle * SECONDS_IN_HOUR) / seconds_of_distance;

                break;
            case OLYMPIC:
                if (systemMeasure == METRIC)
                    speed_km_or_mi_per_h = (double)(olympic_distance_metric_bicycle * SECONDS_IN_HOUR) / seconds_of_distance;
                else
                    speed_km_or_mi_per_h = (double)(olympic_distance_imperial_bicycle * SECONDS_IN_HOUR) / seconds_of_distance;

                break;
            case HALF:
                if (systemMeasure == METRIC)
                    speed_km_or_mi_per_h = (double)(half_distance_metric_bicycle * SECONDS_IN_HOUR) / seconds_of_distance;
                else
                    speed_km_or_mi_per_h = (double)(half_distance_imperial_bicycle * SECONDS_IN_HOUR) / seconds_of_distance;

                break;
            case IRONMAN:
                if (systemMeasure == METRIC)
                    speed_km_or_mi_per_h = (double)(iron_distance_metric_bicycle * SECONDS_IN_HOUR) / seconds_of_distance;
                else
                    speed_km_or_mi_per_h = (double)(iron_distance_imperial_bicycle * SECONDS_IN_HOUR) / seconds_of_distance;

                break;
            default:
                break;
        }

        speed_km_or_mi_per_h = new BigDecimal(speed_km_or_mi_per_h).setScale(2, RoundingMode.DOWN).doubleValue();
        result.append(String.valueOf(speed_km_or_mi_per_h));

        mBicePace = result.toString();

        return result.toString();
    }

    public String calculateBicycleTotalTime(int distance, int systemMeasure, String speedOfDistance) {
        StringBuilder result = new StringBuilder();
        double speed = Double.valueOf(speedOfDistance);
        double totaltime_seconds = 1;
        switch (distance) {
            case SPRINT:
                if (systemMeasure == METRIC)
                    totaltime_seconds = (double) sprint_distance_metric_bicycle / speed;
                else
                    totaltime_seconds = (double) sprint_distance_imperial_bicycle / speed;

                break;
            case OLYMPIC:
                if (systemMeasure == METRIC)
                    totaltime_seconds = (double) olympic_distance_metric_bicycle / speed;
                else
                    totaltime_seconds = (double) olympic_distance_imperial_bicycle / speed;

                break;
            case HALF:
                if (systemMeasure == METRIC)
                    totaltime_seconds = (double) half_distance_metric_bicycle / speed;
                else
                    totaltime_seconds = (double) half_distance_imperial_bicycle / speed ;

                break;
            case IRONMAN:
                if (systemMeasure == METRIC)
                    totaltime_seconds = (double) sprint_distance_metric_bicycle / speed;
                else
                    totaltime_seconds = (double) sprint_distance_imperial_bicycle / speed;

                break;
            default:
                break;
        }
        totaltime_seconds *= SECONDS_IN_HOUR;

        int hour = (int) (totaltime_seconds / SECONDS_IN_HOUR);
        int minutes = (int) (totaltime_seconds % SECONDS_IN_HOUR) / 60;
        int seconds = (int) (totaltime_seconds % SECONDS_IN_HOUR) % 60;

        result.append(format(hour));
        result.append(REGEX);
        result.append(format(minutes));
        result.append(REGEX);
        result.append(format(seconds));

        mBicycleTotalTime = result.toString();

        return result.toString();
    }

    public String calculateRunPace(int distance, int systemMeasure, String timeOfDistance) {
        StringBuilder result = new StringBuilder();
        String arrayTime[] = timeOfDistance.split(REGEX);
        int seconds_of_distance = (Integer.valueOf(arrayTime[0]) * SECONDS_IN_HOUR) + (Integer.valueOf(arrayTime[1]) * TIME_MOD) + Integer.valueOf(arrayTime[2]);
        double seconds_total_speed = 1;
        switch (distance) {
            case SPRINT:
                if (systemMeasure == METRIC)
                    seconds_total_speed = (double) seconds_of_distance / sprint_distance_metric_run ;
                else
                    seconds_total_speed = (double) seconds_of_distance / (double) sprint_distance_imperial_run;

                break;
            case OLYMPIC:
                if (systemMeasure == METRIC)
                    seconds_total_speed = (double) seconds_of_distance / olympic_distance_metric_run ;
                else
                    seconds_total_speed = (double) seconds_of_distance / (double) olympic_distance_imperial_run;

                break;
            case HALF:
                if (systemMeasure == METRIC)
                    seconds_total_speed = (double) seconds_of_distance / (double) half_distance_metric_run ;
                else
                    seconds_total_speed = (double) seconds_of_distance / (double) half_distance_imperial_run;

                break;
            case IRONMAN:
                if (systemMeasure == METRIC)
                    seconds_total_speed = (double) seconds_of_distance / (double) iron_distance_metric_run ;
                else
                    seconds_total_speed = (double) seconds_of_distance / (double) iron_distance_imperial_run;

                break;
            default:
                break;
        }

        int minutes = (int) seconds_total_speed / 60;
        int seconds = (int) seconds_total_speed % 60;

        result.append(format(minutes));
        result.append(REGEX);
        result.append(format(seconds));

        mRunPace = result.toString();

        return result.toString();
    }

    public String calculateRunTotalTime(int distance, int systemMeasure, String paceOfDistance) {
        StringBuilder result = new StringBuilder();
        String arrayTime[] = paceOfDistance.split(REGEX);
        int seconds_pace = (Integer.valueOf(arrayTime[0]) * TIME_MOD) + (Integer.valueOf(arrayTime[1]));
        //double speed = (double) SWIM_DISTANCE_PACE / seconds_in_100;
        double seconds_total = 1;

        switch (distance) {
            case SPRINT:
                if (systemMeasure == METRIC)
                    seconds_total = (double) sprint_distance_metric_run * seconds_pace;
                else
                    seconds_total = (double) sprint_distance_imperial_run * seconds_pace;

                break;
            case OLYMPIC:
                if (systemMeasure == METRIC)
                    seconds_total = (double) olympic_distance_metric_run * seconds_pace;
                else
                    seconds_total = (double) olympic_distance_imperial_run * seconds_pace;

                break;
            case HALF:
                if (systemMeasure == METRIC)
                    seconds_total = (double) half_distance_metric_run * seconds_pace;
                else
                    seconds_total = (double) half_distance_imperial_run * seconds_pace;

                break;
            case IRONMAN:
                if (systemMeasure == METRIC)
                    seconds_total = (double) half_distance_metric_run * seconds_pace;
                else
                    seconds_total = (double) half_distance_imperial_run * seconds_pace;

                break;
            default:
                break;
        }

        int hour = (int) (seconds_total / SECONDS_IN_HOUR);
        int minutes = (int) (seconds_total % SECONDS_IN_HOUR) / 60;
        int seconds = (int) (seconds_total % SECONDS_IN_HOUR) % 60;

        result.append(format(hour));
        result.append(REGEX);
        result.append(format(minutes));
        result.append(REGEX);
        result.append(format(seconds));

        mRunTotalTime = result.toString();

        return result.toString();
    }
}
