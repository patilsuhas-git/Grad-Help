/**
 * AdvisorHomeActivity.java - Container for AdvisorHomeFragment and AppointmentsFragments.
 */

package com.uta.gradhelp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.blunderer.materialdesignlibrary.activities.ViewPagerWithTabsActivity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.uta.gradhelp.Fragments.AdvisorHomeFragment;
import com.uta.gradhelp.Fragments.AppointmentsFragment;

public class AdvisorHomeActivity extends ViewPagerWithTabsActivity {

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
        return new ViewPagerHandler(this).addPage("Home", new AdvisorHomeFragment()).addPage("Appointments", new AppointmentsFragment());
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //This is for the signout option menu in action bar.
        menu.add("Sign Out").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(AdvisorHomeActivity.this, LoginRegisterActivity.class));
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
}
