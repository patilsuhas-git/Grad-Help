/**
 * LoginRegisterActivity.java - Container for LoginFragment and RegisterFragment.
 * @author  Suhas Patil
 */

package com.uta.gradhelp.Activities;

import android.os.Bundle;

import com.blunderer.materialdesignlibrary.activities.ViewPagerWithTabsActivity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.uta.gradhelp.Fragments.LoginFragment;
import com.uta.gradhelp.Fragments.RegisterFragment;

public class LoginRegisterActivity extends ViewPagerWithTabsActivity {

    ViewPagerHandler viewPagerHandler;

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
        viewPagerHandler = new ViewPagerHandler(this).addPage("Login", new LoginFragment()).addPage("Register", new RegisterFragment());
        return viewPagerHandler;
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }


}
