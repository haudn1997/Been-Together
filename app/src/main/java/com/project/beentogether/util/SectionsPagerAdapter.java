package com.project.beentogether.util;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.beentogether.R;
import com.project.beentogether.activity.SliderFirstItem;
import com.project.beentogether.activity.SliderSecondItem;
import com.project.beentogether.activity.SliderThirdItem;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SliderFirstItem();
                break;
            case 1:
                fragment = new SliderSecondItem();
                break;
            case 2:
                fragment = new SliderThirdItem();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}