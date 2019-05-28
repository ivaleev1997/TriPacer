package com.pace.tripacer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomSheetResult extends BottomSheetDialogFragment {

    public BottomSheetResult() {

    }

    public static BottomSheetResult getInstance() {
        return new BottomSheetResult();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment, container, false);

        TextView TotalTimeResult = view.findViewById(R.id.resultTextView);
        if (getArguments() != null) {
            String totalTime = getArguments().getString(MainActivity.TOTAL_TIME, "00:00:00");
            TotalTimeResult.setText(totalTime);
        }

        return view;
    }
}
