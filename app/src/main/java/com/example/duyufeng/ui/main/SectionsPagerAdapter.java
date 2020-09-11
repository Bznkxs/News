package com.example.duyufeng.ui.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.duyufeng.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FixedPagerAdapter<Integer> {

    @StringRes
    private static int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    int len = 3;
    int config = 3;
    private final Context mContext;
    public View view;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Integer getItemData(int position) {
        return len > position ? TAB_TITLES[position] : null;
    }

    @Override
    public int getDataPosition(Integer s) {
        for (int i = 0; i < len; ++i)
        if (s.equals(TAB_TITLES[i]))
            return i;
        return -1;
    }

    @Override
    public boolean equals(Integer oldD, Integer newD) {
        return (oldD.equals(newD));
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                if (config == 1)
                    return NewsTabFragment.newInstance(1);
                return NewsTabFragment.newInstance(0);
            case 1:
                return NewsTabFragment.newInstance(1);
            default:
                return null;
        }

    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        return super.instantiateItem(container, position);

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
        config = tabConfig;
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
        notifyDataSetChanged();

    }
}
