package com.maxwellsport.maxwellsportapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import com.maxwellsport.maxwellsportapp.fragments.AboutFragment;
import com.maxwellsport.maxwellsportapp.fragments.AtlasExerciseGroupFragment;
import com.maxwellsport.maxwellsportapp.fragments.CardioFragment;
import com.maxwellsport.maxwellsportapp.fragments.ProfileFragment;
import com.maxwellsport.maxwellsportapp.fragments.SettingsFragment;
import com.maxwellsport.maxwellsportapp.fragments.TrainingDayFragment;
import com.maxwellsport.maxwellsportapp.fragments.TrainingFragment;
import com.maxwellsport.maxwellsportapp.services.DataConversionService;
import com.maxwellsport.maxwellsportapp.services.SharedPreferencesService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private Fragment mFragment;
    private MenuItem mPrevMenuItem;
    private int mItemID;
    private TypedArray mNavigationColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Wczytanie motywu aplikacji. Domyślny motyw CyanAccentColorTheme */
        int style = SharedPreferencesService.getInt(this, SharedPreferencesService.settings_theme_key, 7);
        setTheme(DataConversionService.convertTheme(style));

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationColors = getResources().obtainTypedArray(R.array.nav_bar_item_colors);
        /* Wczytanie zapisanych wartosci po wznowieniu aplikacji */
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mFragment");
            mItemID = savedInstanceState.getInt("mItemID");
        } else {
            /* Domyslne wartosci dla uruchomienia plikacji. Pierwszy fragment to profile fragment, oraz zakladka z nim zwiazana */
            int tab = SharedPreferencesService.getInt(this, SharedPreferencesService.settings_default_tab_key, 0);
            switch (tab) {
                case 0:
                    mFragment = new ProfileFragment();
                    break;
                case 1:
                    mFragment = new TrainingFragment();
                    break;
                case 2:
                    mFragment = new CardioFragment();
                    break;
                case 3:
                    mFragment = new AtlasExerciseGroupFragment();
                    break;
            }
            replaceFragment(mFragment);
            mItemID = tab;
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

        /* Usuniecie domyslnego stylu kolorowania ikon */
        mNavigationView.setItemIconTintList(null);

        /* Kolorowanie ikon w Navigation Drawer */
        for (int i = 0; i < 7; i++) {
            mNavigationView.getMenu().getItem(i).getIcon().mutate().setColorFilter(mNavigationColors.getColor(i, 0), PorterDuff.Mode.SRC_ATOP);
        }
        mPrevMenuItem = mNavigationView.getMenu().getItem(mItemID);

        /* Naprawienie bugu, gdy po przekreceniu ekranu byly aktywne dwa elementy z róznych grup */
        if (mPrevMenuItem.getGroupId() == R.id.nav_group_top) {
            mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_bottom, false, true);
            mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_top, true, true);
        } else if (mPrevMenuItem.getGroupId() == R.id.nav_group_bottom) {
            mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_top, false, true);
            mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_bottom, true, true);
        }
        /* Pokolorowanie aktywnego elementu po obróceniu i po uruchomieniu aplikacji */
        tintMenuItem(mPrevMenuItem, mItemID);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("mItemID", mItemID);
        getSupportFragmentManager().putFragment(savedInstanceState, "mFragment", mFragment);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item != mPrevMenuItem) {
            if (mFragment instanceof TrainingDayFragment && !((TrainingDayFragment) mFragment).status.equals("stopped")) {
                /* Jeżeli fragment Training działa, wyświetlenie komunikatu */
                new AlertDialog.Builder(this)
                        .setTitle(R.string.alert_title)
                        .setMessage(R.string.alert_msg_training_running)
                        .setPositiveButton(R.string.alert_positive_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            } else if (mFragment instanceof CardioFragment && !((CardioFragment) mFragment).status.equals("stopped")) {
                /* Jeżeli fragment Cardio działa, wyświetlenie komunikatu */
                new AlertDialog.Builder(this)
                        .setTitle(R.string.alert_title)
                        .setMessage(R.string.alert_msg_cardio_running)
                        .setPositiveButton(R.string.alert_positive_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            } else {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        mItemID = 0;
                        fragment = new ProfileFragment();
                        break;
                    case R.id.nav_training:
                        mItemID = 1;
                        fragment = new TrainingFragment();
                        break;
                    case R.id.nav_cardio:
                        mItemID = 2;
                        fragment = new CardioFragment();
                        break;
                    case R.id.nav_atlas:
                        mItemID = 3;
                        fragment = new AtlasExerciseGroupFragment();
                        break;
                    case R.id.nav_settings:
                        mItemID = 4;
                        fragment = new SettingsFragment();
                        break;
                    case R.id.nav_logout:
                        mItemID = 5;
                        SharedPreferencesService.remove(getApplication(), SharedPreferencesService.app_user_id_key);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_about:
                        mItemID = 6;
                        fragment = new AboutFragment();
                        break;
                }

                if (fragment != null) {
                    if (item.getGroupId() == R.id.nav_group_top) {
                        mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_bottom, false, true);
                        mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_top, true, true);
                    } else if (item.getGroupId() == R.id.nav_group_bottom) {
                        mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_top, false, true);
                        mNavigationView.getMenu().setGroupCheckable(R.id.nav_group_bottom, true, true);
                    }
                    /* Pokolorowanie elementu */
                    tintMenuItem(item, mItemID);
                    mPrevMenuItem = item;
                    replaceFragment(fragment);
                }
            }
        }
        /* Zamkniecie navigation drawer po wykonaniu akcji */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void tintMenuItem(MenuItem item, int itemID) {
        setTitle(item.getTitle().toString());

        /* Pozbawienie koloru poprzedniego elementu menu */
        if (mPrevMenuItem != null)
            mPrevMenuItem.setTitle(mPrevMenuItem.getTitle().toString());

        /* Dodanie koloru aktywnemu elementowi */
        SpannableString tintTitle = new SpannableString(item.getTitle());
        tintTitle.setSpan(new ForegroundColorSpan(mNavigationColors.getColor(itemID, 0)), 0, tintTitle.length(), 0);
        item.setTitle(tintTitle);
        item.setChecked(true);
    }

    /* Metody do zarządzania fragmentami */
    public void replaceFragment(Fragment fragment) {
        mFragment = fragment;
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void addFragment(Fragment fragment) {
        //TODO: naprawic animacje po odwiezenieu
        //TODO: naprawic wysweitlanie tytulu zakladki po odwiezeniu
        mFragment = fragment;
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.none, R.anim.slide_out_left, R.anim.none, R.anim.slide_out_right).addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
    }
}