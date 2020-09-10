package com.example.duyufeng.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.duyufeng.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    int len = 3;
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return NewsTabFragment.newInstance(0);
            case 1:
            case 2:
                return NewsTabFragment.newInstance(1);
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return len;
    }

    public void setConfiguration(int tabConfig) {
        TAB_TITLES = new int[3];
        len = 0;
        if ((tabConfig & 2) == 2) {
            TAB_TITLES[len] = R.string.tab_text_1;
            ++len;
        }
        if ((tabConfig & 1) == 1) {
            TAB_TITLES[len] = R.string.tab_text_2;
            ++len;
        }
    }
}