package com.pace.tripacer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.pace.tripacer.MainActivity.TRI_ID_KEY;

public class TriPacerFragmentSlider extends Fragment {

    private ViewPager mViewPager;
    private BasicTriPacerFragment mCurrentFragmentTab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewPager = view.findViewById(R.id.viewpager);
        TriPagerAdapter triPagerAdapter = new TriPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(triPagerAdapter);
    }


    public class TriPagerAdapter extends FragmentPagerAdapter {

        private String[] tabs = {"Iron","HIron","Olympic","Sprint"};

        public TriPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return getTabInstance(R.string.sprint);
                case 1:
                    return getTabInstance(R.string.olympic);
                case 2:
                    return getTabInstance(R.string.half);
                case 3:
                    return getTabInstance(R.string.iron);
                default:
                    break;
            }
            return null;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        private Fragment getTabInstance(int argument) {

            mCurrentFragmentTab = new BasicTriPacerFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(TRI_ID_KEY, argument);
            mCurrentFragmentTab.setArguments(bundle);

            return mCurrentFragmentTab;
        }
    }

}
