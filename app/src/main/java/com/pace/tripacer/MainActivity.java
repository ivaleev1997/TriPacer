package com.pace.tripacer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    public static final String TRI_ID_KEY = "tri";
    public static final int LAYOUT = R.layout.tab_basic_tripacer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            TriPacerFragmentSlider fragment = new TriPacerFragmentSlider();
            transaction.replace(R.id.view_pager_frame, fragment);
            transaction.commit();
/*            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BasicTriPacerFragmentTab basicTriPacerFragmentTab = new BasicTriPacerFragmentTab();
            transaction.replace(R.id.view_pager_frame, basicTriPacerFragmentTab);
            transaction.commit();*/
        }
    }
}
