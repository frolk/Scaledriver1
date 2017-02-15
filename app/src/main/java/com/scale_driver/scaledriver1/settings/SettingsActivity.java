package com.scale_driver.scaledriver1.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.scale_driver.scaledriver1.R;

import java.util.List;


public class SettingsActivity extends PreferenceActivity {

    private AppCompatDelegate mDelegate;

    private AppCompatDelegate getDelegate(){
        if(mDelegate == null){
            mDelegate = AppCompatDelegate.create(this, null);
        } else {
            Toast.makeText(this, "ivan", Toast.LENGTH_SHORT).show();
            return null;
        }
        return mDelegate;
    }



    protected void onCreate(Bundle savedInstanceState) {

//        android.support.v7.app.ActionBar actionBar = getDelegate().getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return GeneralSettingsFragment.class.getName().equals(fragmentName)
                || ArchiveSettingsFragment.class.getName().equals(fragmentName)
                || NotifySettingsFragment.class.getName().equals(fragmentName);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.settings_headers, target);
    }

    public static class GeneralSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_general);
        }
    }

    public static class ArchiveSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_archive);
        }
    }

    public static class NotifySettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setttings_notify);
        }
    }

}
