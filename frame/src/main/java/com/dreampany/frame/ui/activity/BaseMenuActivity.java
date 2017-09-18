package com.dreampany.frame.ui.activity;

import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by nuc on 8/9/2016.
 */
public abstract class BaseMenuActivity extends BaseActivity {

    private final int defaultMenuResourceId = 0;
    private int menuResourceId = defaultMenuResourceId;

    public int getMenuResourceId() {
        return menuResourceId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuResourceId() <= defaultMenuResourceId) {
            menu.clear();
        } else {
            getMenuInflater().inflate(getMenuResourceId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
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


    public void optionMenu(int menuResourceId) {
        this.menuResourceId = menuResourceId;
        supportInvalidateOptionsMenu();
    }
}
