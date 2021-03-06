package com.dreampany.frame.data.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daasuu.cat.CountAnimationTextView;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.AdView;
import com.dreampany.frame.data.adapter.BaseAdapter;
import com.dreampany.frame.data.listener.RecyclerClickListener;

import eu.davidea.flexibleadapter.common.SmoothScrollGridLayoutManager;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import mehdi.sakout.fancybuttons.FancyButton;


/**
 * Created by nuc on 4/14/2016.
 */
public final class ViewUtil {
    private ViewUtil() {
    }

    public static View inflate(Context context, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    public static boolean isVisible(View view) {
        if (view != null) {
            return view.getVisibility() == View.VISIBLE;
        }
        return false;
    }

    public static void setEnabled(android.support.v4.app.Fragment fragment, int viewId, boolean enabled) {
        setEnabled(getViewById(fragment, viewId), enabled);
    }

    public static void setEnabled(View view, boolean enabled) {
        if (view != null) {
            view.setEnabled(enabled);
        }
    }

    public static void setVisibility(Fragment fragment, int viewId, int visibility) {
        setVisibility(getViewById(fragment, viewId), visibility);
    }

    public static void setVisibility(android.support.v4.app.Fragment fragment, int viewId, int visibility) {
        setVisibility(getViewById(fragment, viewId), visibility);
    }

    public static void setVisibility(View parentView, int viewId, int visibility) {
        setVisibility(getViewById(parentView, viewId), visibility);
    }

    public static void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    public static int getVisibility(View view) {
        if (view != null) {
            view.getVisibility();
        }
        return -1;
    }

    public static void setTag(View view, Object tag) {
        if (view != null) {
            view.setTag(tag);
        }
    }

    public static void setClickListener(android.support.v4.app.Fragment fragment, int viewId) {
        setClickListener(getViewById(fragment.getView(), viewId), (View.OnClickListener) fragment);
    }

    public static void setClickListener(Activity activity, int viewId, View.OnClickListener clickListener) {
        setClickListener(getViewById(activity, viewId), clickListener);
    }

    public static void setClickListener(Fragment fragment, int viewId, View.OnClickListener clickListener) {
        setClickListener(getViewById(fragment.getView(), viewId), clickListener);
    }

    public static void setClickListener(android.support.v4.app.Fragment fragment, int viewId, View.OnClickListener clickListener) {
        setClickListener(getViewById(fragment.getView(), viewId), clickListener);
    }

    public static void setClickListener(View parentView, int viewId, View.OnClickListener clickListener) {
        setClickListener(getViewById(parentView, viewId), clickListener);
    }

    public static void setClickListener(View view, View.OnClickListener clickListener) {
        if (view != null) {
            view.setOnClickListener(clickListener);
        }
    }

    public static View getViewById(Activity activity, int viewId) {
        return (activity == null || viewId <= 0) ? null : activity.findViewById(viewId);
    }

    public static View getViewById(Fragment fragment, int viewId) {
        return (fragment == null || viewId <= 0) ? null : getViewById(fragment.getView(), viewId);
    }

    public static View getViewById(android.support.v4.app.Fragment fragment, int viewId) {
        return (fragment == null || viewId <= 0) ? null : getViewById(fragment.getView(), viewId);
    }

    public static View getViewById(View parentView, int viewId) {
        return (parentView == null || viewId <= 0) ? null : parentView.findViewById(viewId);
    }

    public static boolean setText(Fragment fragment, int viewId, String text) {
        View view = getViewById(fragment, viewId);
        return setText(view, text);
    }

    public static boolean setText(Fragment fragment, int viewId, int value) {
        View view = getViewById(fragment, viewId);
        return setText(view, value);
    }

    public static boolean setText(android.support.v4.app.Fragment fragment, int viewId, String text) {
        View view = getViewById(fragment, viewId);
        return setText(view, text);
    }

    public static boolean setText(android.support.v4.app.Fragment fragment, int viewId, SpannableStringBuilder span) {
        View view = getViewById(fragment, viewId);
        return setText(view, span);
    }

    public static boolean setText(android.support.v4.app.Fragment fragment, int viewId, int value) {
        View view = getViewById(fragment, viewId);
        return setText(view, Integer.toString(value));
    }

    public static boolean setError(android.support.v4.app.Fragment fragment, int viewId, String value) {
        View view = getViewById(fragment, viewId);
        return setError(view, value);
    }

    public static boolean setText(Activity activity, int viewId, String text) {
        View view = getViewById(activity, viewId);
        return setText(view, text);
    }

    public static boolean setText(Activity activity, int viewId, int value) {
        View view = getViewById(activity, viewId);
        return setText(view, value);
    }

    public static boolean setText(View parentView, int viewId, int value) {
        View view = getViewById(parentView, viewId);
        return setText(view, value);
    }

    public static boolean setText(View parentView, int viewId, long value) {
        View view = getViewById(parentView, viewId);
        return setText(view, value);
    }

    public static boolean setText(View parentView, int viewId, String text) {
        View view = getViewById(parentView, viewId);
        return setText(view, text);
    }

    public static boolean setText(View view, String text) {

        if (TextView.class.isInstance(view)) {
            ((TextView) view).setText(text);
            return true;
        }

        return false;
    }

    public static boolean setError(View view, String text) {

        if (TextView.class.isInstance(view)) {
            ((TextView) view).setError(text);
            view.requestFocus();
            return true;
        }

        return false;
    }



    public static boolean setText(View view, SpannableStringBuilder span) {

        if (TextView.class.isInstance(view)) {
            ((TextView) view).setText(span);
            return true;
        }

        return false;
    }

    public static boolean setText(View view, long value) {
        return setText(view, (int) value);
    }

    public static boolean setText(View view, int value) {
        if (CountAnimationTextView.class.isInstance(view)) {
            CountAnimationTextView countView = (CountAnimationTextView) view;
            int sourceValue = Integer.parseInt(countView.getText().toString());
            if (sourceValue != value) {
                countView.setAnimationDuration(500).countAnimation(sourceValue, value);
            }
        } else if (TextView.class.isInstance(view)) {
            ((TextView) view).setText(String.valueOf(value));
            return true;
        }
        return false;
    }

    public static String getText(android.support.v4.app.Fragment parent, int viewId) {
        View view = getViewById(parent, viewId);
        if (TextView.class.isInstance(view)) {
            TextView textView = (TextView) view;
            return textView.getText().toString().trim();
        }
        return null;
    }

    public static String getSelectedText(View view) {
        if (TextView.class.isInstance(view)) {
            TextView textView = (TextView) view;
            if (textView.isFocused()) {
                CharSequence text = textView.getText();
                int textStartIndex = textView.getSelectionStart();
                int textEndIndex = textView.getSelectionEnd();
                int min = Math.max(0, Math.min(textStartIndex, textEndIndex));
                int max = Math.max(0, Math.max(textStartIndex, textEndIndex));
                return text.subSequence(min, max).toString().trim();
            }
        }
        return null;
    }

    public static void setImageResource(Fragment fragment, int viewId, int resourceId) {
        setImageResource(fragment.getView(), viewId, resourceId);
    }

    public static void setImageResource(android.support.v4.app.Fragment fragment, int viewId, int resourceId) {
        setImageResource(fragment.getView(), viewId, resourceId);
    }

    public static void setImageResource(View parentView, int viewId, int resourceId) {
        setImageResource(getViewById(parentView, viewId), resourceId);
    }


    public static void setImageResource(final View view, final int resourceId) {
        if (FancyButton.class.isInstance(view)) {
            ((FancyButton) view).setIconResource(resourceId);
        } else if (FloatingActionButton.class.isInstance(view)) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ((FloatingActionButton) view).setImageDrawable(ContextCompat.getDrawable(view.getContext(), resourceId));
                }
            };
            AndroidUtil.post(runnable);
        } else if (ImageView.class.isInstance(view)) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ((ImageView) view).setImageResource(resourceId);
                }
            };
            AndroidUtil.post(runnable);
        }
    }

    public static void setFancyFont(final Fragment fragment, int viewId, final int resourceId) {
        setFancyFont(fragment.getView(), viewId, resourceId);
    }

    public static void setFancyFont(final android.support.v4.app.Fragment fragment, int viewId, final int resourceId) {
        setFancyFont(fragment.getView(), viewId, resourceId);
    }

    public static void setFancyFont(View parentView, int viewId, int resourceId) {
        setFancyFont(getViewById(parentView, viewId), resourceId);
    }

    public static void setFancyFont(final View view, final int resourceId) {
        if (FancyButton.class.isInstance(view)) {
            ((FancyButton) view).setIconResource(view.getResources().getString(resourceId));
        }
    }

    public static void setImage(View view, String uri, int defaultId) {
        if (SimpleDraweeView.class.isInstance(view)) {
            ((SimpleDraweeView) view).setImageURI(buildUri(uri, defaultId));
        }
    }

    public static Uri buildUri(String uri, int defaultId) {
        Uri.Builder builder = new Uri.Builder();

        if (TextUtils.isEmpty(uri)) {
            builder.scheme(UriUtil.LOCAL_RESOURCE_SCHEME).path(String.valueOf(defaultId));
        } else {
            builder.scheme(UriUtil.LOCAL_RESOURCE_SCHEME).path(uri);
        }

        return builder.build();
    }

    public static void setDrawable(View view, int resourceId) {
        if (view != null) {
            view.setBackgroundResource(resourceId);
        }
    }

    public static void setBackground(Fragment fragment, int viewId, int resourceId) {
        setBackground(fragment.getView(), viewId, resourceId);
    }

    public static void setBackground(android.support.v4.app.Fragment fragment, int viewId, int resourceId) {
        setBackground(fragment.getView(), viewId, resourceId);
    }

    public static void setBackground(View parentView, int viewId, int resourceId) {
        setBackground(getViewById(parentView, viewId), resourceId);
    }

    public static void setBackground(final View view, final int colorId) {
        if (FloatingActionButton.class.isInstance(view)) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ((FloatingActionButton) view).setBackgroundTintList(ColorStateList.valueOf(ColorUtil.getColor(view.getContext(), colorId)));
                }
            };
            AndroidUtil.post(runnable);
        } else if (ImageView.class.isInstance(view)) {
            /*Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ((ImageView) view).setImageResource(resourceId);
                }
            };
            AndroidUtil.post(runnable);*/
        } else if (View.class.isInstance(view)) {
            view.setBackgroundColor(ColorUtil.getColor(view.getContext(), colorId));
        }
    }


    public static void setStartDrawable(View parentView, int viewId, int resourceId) {
        setStartDrawable(getViewById(parentView, viewId), resourceId);
    }

    public static void setStartDrawable(final View view, final int resourceId) {
        if (TextView.class.isInstance(view)) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(resourceId, 0, 0, 0);
                }
            };
            AndroidUtil.post(runnable);
        }
    }

    public static void setTextColor(View parentView, int viewId, int colorId) {
        setTextColor(getViewById(parentView, viewId), colorId);
    }

    public static void setTextColor(View view, int colorId) {
        if (TextView.class.isInstance(view)) {
            ((TextView) view).setTextColor(ColorUtil.getColor(view.getContext(), colorId));
        }
    }

    public static void setCheckedListener(View view, CompoundButton.OnCheckedChangeListener changeListener) {
        if (CompoundButton.class.isInstance(view)) {
            ((CompoundButton) view).setOnCheckedChangeListener(changeListener);
        }
    }

    public static void setChecked(View view, boolean checked) {
        if (CompoundButton.class.isInstance(view)) {
            ((CompoundButton) view).setChecked(checked);
        }
    }

    public static Drawable getDrawable(Context context, int drawableId) {
        return ContextCompat.getDrawable(context, drawableId);
    }

    public static Toolbar getToolbar(Activity activity, int toolbarId) {
        return (Toolbar) activity.findViewById(toolbarId);
    }

    public static RecyclerView getRecyclerView(Fragment fragment, int recyclerId) {
        View view = getViewById(fragment, recyclerId);
        if (view != null) {
            return (RecyclerView) view;
        }
        return null;
    }

    public static RecyclerView getRecyclerView(android.support.v4.app.Fragment fragment, int recyclerId) {
        View view = getViewById(fragment, recyclerId);
        if (view != null) {
            return (RecyclerView) view;
        }
        return null;
    }

    public static RecyclerView getRecyclerView(View parentView, int recyclerId) {
        View view = getViewById(parentView, recyclerId);
        if (view != null) {
            return (RecyclerView) view;
        }
        return null;
    }

    public static AdView getAdView(Activity activity, int adViewId) {
        View view = getViewById(activity, adViewId);
        if (view != null) {
            return (AdView) view;
        }
        return null;
    }


    public static RecyclerView.LayoutManager newLinearLayoutManager(Context context) {
        return new SmoothScrollLinearLayoutManager(context);
    }

    public static RecyclerView.LayoutManager newGridLayoutManager(Context context, int spanCount) {
        return new SmoothScrollGridLayoutManager(context, spanCount);
    }

    public static RecyclerView.Adapter getAdapter(Fragment fragment, int recyclerId) {
        return getAdapter(fragment.getView(), recyclerId);
    }

    public static RecyclerView.Adapter getAdapter(android.support.v4.app.Fragment fragment, int recyclerId) {
        return getAdapter(fragment.getView(), recyclerId);
    }

    public static RecyclerView.Adapter getAdapter(View parentView, int recyclerId) {
        RecyclerView recyclerView = getRecyclerView(parentView, recyclerId);
        if (recyclerView != null) {
            return recyclerView.getAdapter();
        }
        return null;
    }

    public static PagerAdapter getAdapter(ViewPager viewPager) {
        if (!AndroidUtil.isNull(viewPager)) {
            return viewPager.getAdapter();
        }
        return null;
    }

    public static <T> void initRecyclerView(
            RecyclerView recyclerView,
            RecyclerView.LayoutManager layoutManager,
            BaseAdapter<T> adapter) {

        initRecyclerView(recyclerView, layoutManager, adapter, null, null, null);
    }

    public static <T> void initRecyclerView(
            RecyclerView recyclerView,
            RecyclerView.LayoutManager layoutManager,
            BaseAdapter<T> adapter,
            RecyclerClickListener.OnItemClickListener recyclerListener) {

        initRecyclerView(recyclerView, layoutManager, adapter, recyclerListener, null, null);
    }

    public static <T> void initRecyclerView(
            RecyclerView recyclerView,
            RecyclerView.LayoutManager layoutManager,
            BaseAdapter<T> adapter,
            RecyclerClickListener.OnItemClickListener recyclerListener,
            RecyclerClickListener.OnItemChildClickListener childListener) {

        initRecyclerView(recyclerView, layoutManager, adapter, recyclerListener, childListener, null);
    }

    public static <T> void initRecyclerView(
            RecyclerView recyclerView,
            RecyclerView.LayoutManager layoutManager,
            BaseAdapter<T> adapter,
            RecyclerClickListener.OnItemClickListener recyclerListener,
            RecyclerClickListener.OnItemChildClickListener childListener,
            RecyclerView.OnScrollListener scrollListener) {

        if (recyclerView != null) {

            RecyclerClickListener recyclerClickListener = null;

            if (recyclerListener != null) {
                recyclerClickListener = getRecyclerListener(recyclerView, recyclerListener);
            }

            if (recyclerClickListener != null && childListener != null) {
                adapter.setRecyclerClickListener(recyclerClickListener);
                recyclerClickListener.setOnItemChildClickListener(childListener);
            }

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);


            if (recyclerClickListener != null) {
                recyclerView.addOnItemTouchListener(recyclerClickListener);
            }

            if (scrollListener != null) {
                recyclerView.addOnScrollListener(scrollListener);
            }
        }
    }

    public static RecyclerClickListener getRecyclerListener(RecyclerView recyclerView, RecyclerClickListener.OnItemClickListener itemClickListener) {
        return new RecyclerClickListener(recyclerView.getContext(), recyclerView, itemClickListener);
    }
}
