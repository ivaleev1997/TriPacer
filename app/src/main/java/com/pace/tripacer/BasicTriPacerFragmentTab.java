package com.pace.tripacer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.pace.tripacer.MainActivity.LAYOUT;
import static com.pace.tripacer.MainActivity.TRI_ID_KEY;

public class BasicTriPacerFragmentTab extends Fragment {

    private View view;
    private int mTriId;

    private TextView mSwimDistance;
    private TextView mBicycleDistance;
    private TextView mRunDistance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        initView(getTriId());

        return view;
    }

    private int getTriId() {
        mTriId = getArguments().getInt(TRI_ID_KEY);
        return mTriId;
    }


    void initView(int tri) {
        mSwimDistance = view.findViewById(R.id.txtSwim);
        mBicycleDistance = view.findViewById(R.id.txtBicycle);
        mRunDistance = view.findViewById(R.id.txtRun);

        switch (tri) {
            case R.string.sprint:
                mSwimDistance.setText(R.string.swim_sprint);
                mBicycleDistance.setText(R.string.bicycle_sprint);
                mRunDistance.setText(R.string.run_sprint);
                break;
            case R.string.olympic:
                mSwimDistance.setText(R.string.swim_olympic);
                mBicycleDistance.setText(R.string.bicycle_olympic);
                mRunDistance.setText(R.string.run_olympic);
                break;
            case R.string.half:
                mSwimDistance.setText(R.string.swim_half);
                mBicycleDistance.setText(R.string.bicycle_half);
                mRunDistance.setText(R.string.run_half);
                break;
            case R.string.iron:
                mSwimDistance.setText(R.string.swim_iron);
                mBicycleDistance.setText(R.string.bicycle_iron);
                mRunDistance.setText(R.string.run_iron);
                break;
            default:
                break;
        }
    }


}
