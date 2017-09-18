package com.dreampany.frame.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.dreampany.frame.application.BaseApplication;
import com.dreampany.frame.data.callback.UiCallback;
import com.dreampany.frame.data.enums.Type;
import com.dreampany.frame.data.model.Base;
import com.dreampany.frame.data.model.Color;
import com.dreampany.frame.data.model.Task;
import com.dreampany.frame.data.util.AndroidUtil;
import com.dreampany.frame.data.util.BarUtil;
import com.dreampany.frame.data.util.ColorUtil;
import com.dreampany.frame.data.util.FragmentUtil;
import com.dreampany.frame.data.util.ViewUtil;
import com.dreampany.frame.ui.fragment.BaseFragment;
import com.dreampany.frame.ui.fragment.BaseFragmentCompat;

import java.util.List;


/**
 * Created by nuc on 3/12/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, UiCallback, PermissionListener, MultiplePermissionsListener {

    private final int defaultLayoutId = 0;
    private final int defaultToolbarId = 0;

    private Task<? extends Base, ? extends Type> currentTask;
    private BaseFragment currentFragment;
    private BaseFragmentCompat currentFragmentCompat;
    private Color color;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AndroidUtil.hasLollipop()) {
            requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        if (AndroidUtil.hasLollipop()) {
            getWindow().setEnterTransition(new Fade());
        }


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (jumpRequired()) {
            getApp().jumpIfNeeded(this);
        }
        // if (savedInstanceState == null) {
        startUi(false);
        // }

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    protected void onStop() {
        unregisterEventBus();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        BaseFragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            if (currentFragment.performBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  if (FirebaseManager.onInstance().isAuthenticated()) {
            hideProgressDialog();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        stopUi();
        super.onDestroy();
    }

    @Override
    public void onTask(Task task) {

    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

    }

    public Context getContext() {
        return this;
    }

    public Context getAppContext() {
        return getApplicationContext();
    }

    public Color getColor() {
        return color;
    }

    public void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    protected int getLayoutId() {
        return defaultLayoutId;
    }

    protected int getToolbarId() {
        return defaultToolbarId;
    }

    protected boolean enabledHomeUp() {
        return true;
    }

    protected boolean jumpRequired() {
        return true;
    }

    protected void startUi(boolean fullScreen) {

        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            BarUtil.hide(this);
/*            Toolbar toolbar = ViewUtil.getToolbar(this, getToolbarId());
            if (toolbar != null) {
                BarUtil.hideToolbar(toolbar);
                toolbar.setVisibility(View.GONE);
            }*/
        }

        int layoutId = getLayoutId();
        if (layoutId == defaultLayoutId) return;
        setColor(ColorUtil.getRandColor());
        DataBindingUtil.setContentView(this, layoutId);

        if (!fullScreen) {

            Toolbar toolbar = ViewUtil.getToolbar(this, getToolbarId());

            if (toolbar != null) {

                setSupportActionBar(toolbar);

                if (enabledHomeUp()) {
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                }

                BarUtil.setStatusColor(this, color);
                BarUtil.setActionBarColor(toolbar, color);
            }
        }
    }

    protected void stopUi() {
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {

    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

    }

    public Task<? extends Base, ? extends Type> getCurrentTask() {
        if (currentTask == null) {
            currentTask = getIntentValue(Task.class.getName());
        }
        return currentTask;
    }

    public void setCurrentTask(Task<? extends Base, ? extends Type> currentTask) {
        this.currentTask = currentTask;
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public BaseFragmentCompat getCurrentFragmentCompat() {
        return currentFragmentCompat;
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public void setCurrentFragmentCompat(BaseFragmentCompat currentFragmentCompat) {
        this.currentFragmentCompat = currentFragmentCompat;
    }

    protected boolean performBackPressed() {
        return false;
    }


    public void registerEventBus() {
        // EventManager.register(this);
    }


    public void unregisterEventBus() {
        // EventManager.unregister(this);
    }


    protected <T> T getIntentValue(String key) {
        Bundle bundle = getIntent().getExtras();
        T t = null;
        if (bundle != null) {
            t = (T) bundle.getParcelable(key);
        }

        if (bundle != null && t == null) {
            t = (T) bundle.getSerializable(key);
        }
        return t;
    }


    protected void setColor(Color color) {
        this.color = color;
    }


    protected void initFullView() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        BarUtil.hide(this);

        DataBindingUtil.setContentView(this, getLayoutId());
    }

    protected <T extends BaseActivity> void openActivity(Class<T> aClass) {
        startActivity(new Intent(this, aClass));
    }

    protected <T extends BaseActivity> void openActivity(Class<T> aClass, Task task) {
        Intent intent = new Intent(this, aClass);
        intent.putExtra(Task.class.getName(), (Parcelable) task);
        startActivity(intent);
    }

    protected <T extends BaseActivity> void openForResult(Class<T> aClass, Task task, int requestCode) {
        Intent intent = new Intent(this, aClass);
        startActivityForResult(intent, requestCode);
    }

    protected <T extends BaseFragment> BaseFragment commitFragment(final Class<T> fragmentClass, final int parentId) {
        BaseFragment currentFragment = FragmentUtil.commitFragment(this, fragmentClass, parentId);
        setCurrentFragment(currentFragment);
        return currentFragment;
    }

    protected <T extends BaseFragmentCompat> T commitFragmentCompat(final Class<T> fragmentClass, final int parentId) {
        T currentFragment = FragmentUtil.commitFragmentCompat(this, fragmentClass, parentId);
        setCurrentFragmentCompat(currentFragment);
        return currentFragment;
    }

    protected <T extends BaseFragmentCompat> T commitFragmentCompat(final Class<T> fragmentClass, final int parentId, Task<?, ?> task) {
        T currentFragment = FragmentUtil.commitFragmentCompat(this, fragmentClass, parentId, task);
        setCurrentFragmentCompat(currentFragment);
        return currentFragment;
    }

    public void showProgressDialog(String message) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(true);
        } else if (progressDialog.isShowing()) {
            return;
        }

        progressDialog.setMessage(message + "...");
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public BaseApplication getApp() {
        return (BaseApplication) getApplication();
    }
}
