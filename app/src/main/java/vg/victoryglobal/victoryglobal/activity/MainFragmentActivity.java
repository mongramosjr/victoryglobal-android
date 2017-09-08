/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/9/17 7:01 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/9/17 6:59 AM
 */

package vg.victoryglobal.victoryglobal.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import vg.victoryglobal.victoryglobal.R;
import vg.victoryglobal.victoryglobal.fragment.ActivateCodeFragment;
import vg.victoryglobal.victoryglobal.fragment.HomeFragment;
import vg.victoryglobal.victoryglobal.fragment.RegisterAccountFragment;
import vg.victoryglobal.victoryglobal.fragment.UpgradeAccountFragment;

public class MainFragmentActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();

            Fragment fragment = null;
            Class fragmentClass = null;

            Bundle bundle = new Bundle();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentClass = HomeFragment.class;

                    //set arguments
                    //bundle.putSparseParcelableArray();
                    break;
                case R.id.navigation_register:
                    fragmentClass = RegisterAccountFragment.class;

                    //set arguments
                    //bundle.putSparseParcelableArray();
                    break;
                case R.id.navigation_activate_code:
                    fragmentClass = ActivateCodeFragment.class;

                    //set arguments
                    //bundle.putSparseParcelableArray();
                    break;
                case R.id.navigation_upgrade:
                    fragmentClass = UpgradeAccountFragment.class;

                    //set arguments
                    //bundle.putSparseParcelableArray();
                    break;
            }
            //-- using fragment
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_fragment_frame_layout, fragment).commit();


            // Highlight the selected item has been done by NavigationView
            item.setChecked(true);
            // Set action bar title
            setTitle(item.getTitle());
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_activity);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
