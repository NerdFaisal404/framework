package com.dreampany.frame.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

/**
 * Created by nuc on 2/13/2017.
 */

public abstract class BaseBottomNavigationActivity extends BaseMenuActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected int currentNavId;

    @Override
    protected void startUi(boolean fullScreen) {
        super.startUi(fullScreen);

        final BottomNavigationView bottomNavigationView = getBottomNavigationView();
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int targetNavId = item.getItemId();
        if (targetNavId != currentNavId) {
            onNavigationItem(targetNavId);
            currentNavId = targetNavId;
            return true;
        }
        return false;
    }

    protected int getBottomNavigationViewId() {
        return 0;
    }

    protected BottomNavigationView getBottomNavigationView() {
        return (BottomNavigationView) findViewById(getBottomNavigationViewId());
    }

    protected void onNavigationItem(int navItemId) {
    }
}
