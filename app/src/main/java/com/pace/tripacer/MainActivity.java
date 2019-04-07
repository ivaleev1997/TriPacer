package com.pace.tripacer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class MainActivity extends AppCompatActivity {
    public static final String TRI_ID_KEY = "tri";
    public static final int LAYOUT = R.layout.tab_basic_tripacer;

    public static final String APP_SHARED_PREFS = "com.pace.tripacer";
    public static final String SAVED_DATA_KEY_MEASURE = "measureSystem";
    public static final String SAVED_DATA_KEY_DISTANCE = "distance";

    public static final int DEFAULT_DISTANCE = R.string.half;//half iron

    public static final int METRIC = R.string.met;
    public static final int IMPERIAL = R.string.imp;

    private BasicTriPacerFragment mBasicTriPacerFragment;
    private SharedPreferences mSharedPreferences;
    private BottomSheetDistance mBottomSheetDistance;
    private Boolean isRestored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSharedPreference(getApplicationContext());

        initView();


        if (savedInstanceState != null) {
            isRestored = true;
        }

    }

    private void getSharedPreference(Context context) {
        mSharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, MODE_PRIVATE);
    }

    private void initView() {
        mBasicTriPacerFragment = new BasicTriPacerFragment();
        //mBottomSheetDistance = new BottomSheetDistance();
        //sheetBehavior = BottomSheetBehavior.from();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.view_pager_frame, mBasicTriPacerFragment);
        transaction.commit();

        BottomAppBar bar = (BottomAppBar) findViewById(R.id.bar);
        setSupportActionBar(bar);
        bar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);


        SegmentedButtonGroup segmentedButtonGroup = (SegmentedButtonGroup) findViewById(R.id.segmentedButtonGroup);
        segmentedButtonGroup.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                Toast.makeText(MainActivity.this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0://Metric position
                        //change state in current tab
                        if (mBasicTriPacerFragment != null) mBasicTriPacerFragment.changeMeasureSystem(METRIC);

                        saveMeasureSystem(METRIC);
                        break;
                    case 1://Imperial position
                        //change state in current tab
                        if (mBasicTriPacerFragment != null) mBasicTriPacerFragment.changeMeasureSystem(IMPERIAL);

                        saveMeasureSystem(IMPERIAL);
                        break;
                }
            }
        });

        int state;
        Bundle bundle = new Bundle();
        if ((state = getMeasureSystemState()) == -1) {
            segmentedButtonGroup.setPosition(1, 0);

            bundle.putInt(SAVED_DATA_KEY_MEASURE, METRIC);

        }
        else {
            segmentedButtonGroup.setPosition(state, 0);

            bundle.putInt(SAVED_DATA_KEY_MEASURE, state);
        }
        //put def distance
        if (isRestored)
            bundle.putInt(SAVED_DATA_KEY_DISTANCE, DEFAULT_DISTANCE);
        else
            bundle.putInt(SAVED_DATA_KEY_DISTANCE, getSavedCurrentDistance());

        mBasicTriPacerFragment.setArguments(bundle);
    }

    private int getSavedCurrentDistance() {
        return mSharedPreferences.getInt(SAVED_DATA_KEY_DISTANCE, DEFAULT_DISTANCE);
    }

    private void saveMeasureSystem(int state) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(SAVED_DATA_KEY_MEASURE, state);
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    private void saveCurrentDistance() {//save for restore
        if (mBasicTriPacerFragment != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(SAVED_DATA_KEY_DISTANCE, mBasicTriPacerFragment.getCurrentDistanceId());
            editor.apply();
        }
    }

    private int getMeasureSystemState() {
        int result = -1;

        result = mSharedPreferences.getInt(SAVED_DATA_KEY_MEASURE, -1);

        return result;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Toast.makeText(this, String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
            showModalBottomSheet();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showModalBottomSheet() {
        //View view = getLayoutInflater().inflate(R.layout.distance_recycler, null);
        mBottomSheetDistance = BottomSheetDistance.getInstance();
        mBottomSheetDistance.show(getSupportFragmentManager(), "checkDistance");
    }
}
