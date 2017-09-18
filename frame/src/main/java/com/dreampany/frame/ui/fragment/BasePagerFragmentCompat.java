package com.dreampany.frame.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.dreampany.frame.R;
import com.dreampany.frame.data.adapter.SupportFragmentAdapter;
import com.dreampany.frame.data.model.Color;
import com.dreampany.frame.data.util.AndroidUtil;
import com.dreampany.frame.data.util.ColorUtil;
import com.dreampany.frame.data.util.ViewUtil;

/**
 * Created by nuc on 10/9/2016.
 */

public abstract class BasePagerFragmentCompat extends BaseMenuFragmentCompat {

    public abstract String[] pageTitles();

    public abstract Class[] pageClasses();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tabpager;
    }

    public int getViewPagerId() {
        return R.id.viewPager;
    }

    public int getTabLayoutId() {
        return R.id.tabLayout;
    }

    @Override
    public void startUi() {

        ViewPager viewPager = AndroidUtil.getViewPager(getView(), getViewPagerId());
        TabLayout tabLayout = AndroidUtil.getTabLayout(getView(), getTabLayoutId());

        if (AndroidUtil.isNull(viewPager) || AndroidUtil.isNull(tabLayout)) return;

        SupportFragmentAdapter<BaseFragmentCompat> fragmentAdapter = resolveFragmentAdapter();

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);


        Color color = getColor();
        if (color == null) {

        }

        //tabLayout.setBackgroundColor(ColorUtil.getColor(getContext(), color.getColorPrimaryId()));


        tabLayout.setSelectedTabIndicatorColor(
                ColorUtil.getColor(getContext(), color.getColorAccentId())
        );

        tabLayout.setTabTextColors(
                ColorUtil.getColor(getContext(), color.getColorAccentId()),
                ColorUtil.getColor(getContext(), color.getColorPrimaryId())
        );

        // fragmentAdapter.removeAll();

        final SupportFragmentAdapter<BaseFragmentCompat> fixedAdapter = fragmentAdapter;
        final String[] pageTitles = pageTitles();
        final Class[] pageClasses = pageClasses();

        final Runnable pagerRunnable = new Runnable() {
            @Override
            public void run() {
                for (int index = 0; index < pageTitles.length; index++) {
                    fixedAdapter.addPage(pageTitles[index], pageClasses[index]);
                }
            }
        };

        AndroidUtil.postDelay(pagerRunnable);
    }


    @Override
    public BaseFragmentCompat getCurrentFragment() {
        return getCurrentPagerFragment();
    }

    @Override
    public boolean performBackPressed() {
        BaseFragmentCompat currentFragment = getCurrentPagerFragment();
        return currentFragment.performBackPressed();
    }

    public BaseFragmentCompat getCurrentPagerFragment() {
        ViewPager viewPager = AndroidUtil.getViewPager(getView(), getViewPagerId());
        SupportFragmentAdapter<BaseFragmentCompat> pagerAdapter = getFragmentAdapter();
        if (!AndroidUtil.isNull(viewPager) && !AndroidUtil.isNull(pagerAdapter)) {
            return pagerAdapter.getFragment(viewPager.getCurrentItem()).getCurrentFragment();
        }
        return null;
    }

    public <T extends BaseFragmentCompat> SupportFragmentAdapter<T> getFragmentAdapter() {
        ViewPager viewPager = AndroidUtil.getViewPager(getView(), getViewPagerId());
        PagerAdapter pagerAdapter = ViewUtil.getAdapter(viewPager);

        if (!AndroidUtil.isNull(pagerAdapter)) {
            return (SupportFragmentAdapter<T>) pagerAdapter;
        }

        return null;
    }

    public <T extends BaseFragmentCompat> SupportFragmentAdapter<T> resolveFragmentAdapter() {
        SupportFragmentAdapter<T> fragmentAdapter = getFragmentAdapter();

        if (AndroidUtil.isNull(fragmentAdapter)) {
            fragmentAdapter = (SupportFragmentAdapter<T>) SupportFragmentAdapter.newAdapter(getFragmentManager());
        }

        return fragmentAdapter;
    }
}
