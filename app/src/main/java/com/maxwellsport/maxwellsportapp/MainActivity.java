package com.maxwellsport.maxwellsportapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maxwellsport.maxwellsportapp.fragments.AboutFragment;
import com.maxwellsport.maxwellsportapp.fragments.AtlasFragment;
import com.maxwellsport.maxwellsportapp.fragments.CardioFragment;
import com.maxwellsport.maxwellsportapp.fragments.ProfileFragment;
import com.maxwellsport.maxwellsportapp.fragments.SettingsFragment;
import com.maxwellsport.maxwellsportapp.fragments.TrainingFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private Fragment mFragment;
    private MenuItem mPrevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            mFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
            setTitle(getResources().getString(R.string.nav_profile));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        (mNavigationView.getMenu().getItem(0)).getIcon().setColorFilter(getResources().getColor(R.color.nav_profile_color), PorterDuff.Mode.SRC_ATOP);
        (mNavigationView.getMenu().getItem(1)).getIcon().setColorFilter(getResources().getColor(R.color.nav_training_color), PorterDuff.Mode.SRC_ATOP);
        (mNavigationView.getMenu().getItem(2)).getIcon().setColorFilter(getResources().getColor(R.color.nav_cardio_color), PorterDuff.Mode.SRC_ATOP);
        (mNavigationView.getMenu().getItem(3)).getIcon().setColorFilter(getResources().getColor(R.color.nav_atlas_color), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("title", getTitle().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setTitle(savedInstanceState.getString("title"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item != mPrevMenuItem) {
            if (mFragment instanceof CardioFragment && !((CardioFragment) mFragment).status.equals("stopped")) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.alert_dialog_title)
                        .setMessage(R.string.alert_dialog_message)
                        .setPositiveButton(R.string.alert_dialog_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            } else {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        mFragment = new ProfileFragment();
                        break;
                    case R.id.nav_training:
                        mFragment = new TrainingFragment();
                        break;
                    case R.id.nav_cardio:
                        mFragment = new CardioFragment();
                        break;
                    case R.id.nav_atlas:
                        mFragment = new AtlasFragment();
                        break;
                    case R.id.nav_settings:
                        mFragment = new SettingsFragment();
                        break;
                    case R.id.nav_logout:
                        SharedPreferences.Editor editor = getSharedPreferences("maxwellsport", MODE_PRIVATE).edit();
                        editor.putString("userID", null);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_about:
                        mFragment = new AboutFragment();
                        break;
                }

                if (mFragment != null) {
                    if (item.getGroupId() == R.id.nav_group_top) {
                        mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_bottom, false, true);
                        mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_top, true, true);
                    } else if (item.getGroupId() == R.id.nav_group_bottom) {
                        mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_top, false, true);
                        mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_bottom, true, true);
                    }
                    mPrevMenuItem = item;
                    item.setChecked(true);
                    setTitle(item.getTitle());
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();
                }
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
