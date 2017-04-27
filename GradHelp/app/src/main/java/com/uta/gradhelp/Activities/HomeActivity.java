/**
 * HomeActivity.java - Container for HomeFragment and QueueFragment.
 */

package com.uta.gradhelp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.blunderer.materialdesignlibrary.activities.ViewPagerWithTabsActivity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.uta.gradhelp.Fragments.AppointmentFragment;
import com.uta.gradhelp.Fragments.HomeFragment;
import com.uta.gradhelp.Fragments.QueueFragment;

public class HomeActivity extends ViewPagerWithTabsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean expandTabs() {
        return true;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return null;
    }

    @Override
    public ViewPagerHandler getViewPagerHandler() {
        return new ViewPagerHandler(this)
                .addPage("Home", new HomeFragment())
                .addPage("Queue", new QueueFragment())
                .addPage("Queue Status", new AppointmentFragment());
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener userOnPageChangeListener) {
        super.setOnPageChangeListener(userOnPageChangeListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add("Sign Out").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(HomeActivity.this, LoginRegisterActivity.class));
                finish();
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuItem item = menu.getItem(0);
        SpannableString s = new SpannableString("Sign out");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        item.setTitle(s);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }
}
