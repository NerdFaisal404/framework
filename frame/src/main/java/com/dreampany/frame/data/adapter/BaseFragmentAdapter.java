package com.dreampany.frame.data.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.dreampany.frame.data.util.AndroidUtil;
import com.dreampany.frame.data.util.FragmentUtil;


public abstract class BaseFragmentAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    final SparseArray<T> fragments;
    final SparseArray<String> pageTitles;
    final SparseArray<Class<T>> pageClasses;

    BaseFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragments = new SparseArray<>();
        pageTitles = new SparseArray<>();
        pageClasses = new SparseArray<>();
    }

    @Override
    public int getCount() {
        int titleSize = pageTitles.size();
        int classSize = pageClasses.size();
        return titleSize <= classSize ? titleSize : classSize;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles.get(position);
    }

    @Override
    public int getItemPosition(Object inFragment) {
        if (fragments.indexOfValue((T) inFragment) < 0) {
            return FragmentStatePagerAdapter.POSITION_NONE;
        }
        return super.getItemPosition(inFragment);
    }

    public BaseFragmentAdapter<T> addPage(String pageTitle, Class<T> pageClass) {
        int index = getCount();
        setPageTitle(index, pageTitle);
        setPageClass(index, pageClass);
        notifyDataSetChanged();
        return this;
    }

    public BaseFragmentAdapter<T> removePage(int position) {
        return this;
    }

    public BaseFragmentAdapter<T> removeAll() {
        fragments.clear();
        pageTitles.clear();
        pageClasses.clear();
        notifyDataSetChanged();
        return this;
    }

    public T getFragment(int position) {
        return fragments.get(position, null);
    }

    private BaseFragmentAdapter<T> setFragment(int position, T fragment) {
        fragments.put(position, fragment);
        return this;
    }

    private BaseFragmentAdapter<T> setPageTitle(int position, String pageTitle) {
        pageTitles.put(position, pageTitle);
        return this;
    }

    private BaseFragmentAdapter<T> setPageClass(int position, Class<T> pageClass) {
        pageClasses.put(position, pageClass);
        return this;
    }

    T newFragment(int position) {

        T fragment = FragmentUtil.newFragment(pageClasses.get(position, null));

        if (!AndroidUtil.isNull(fragment)) {
            Bundle bundle = new Bundle();
            bundle.putString("page_title", getPageTitle(position).toString());
            fragment.setArguments(bundle);
            setFragment(position, fragment);
        }
        return fragment;
    }
}
