package com.example.all4learnfragments.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.all4learnfragments.R;
import com.example.all4learnfragments.concentration.ConcentrationFragment;
import com.google.android.material.navigation.NavigationView;


public class DrawerActivity extends AppCompatActivity {
    private DrawerActivityNavigator navigator = new DrawerActivityNavigator(getSupportFragmentManager());

    SharedPref sharedPref;
    private Switch mySwitch;

    public static Intent createIntent(Context context) {
        return new Intent(context, DrawerActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.darkTheme);
        } else setTheme(R.style.lightTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open_menu,
                R.string.drawer_close_menu
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setItemIconTintList(null);


        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.notesVector: {
                    navigator.showNotes();
                    drawerLayout.closeDrawers();
                    ActionBar ac = getSupportActionBar();
                    ac.setTitle(R.string.mode_notes);
                    break;
                }
                case R.id.concentrateVector: {
                    navigator.showConcentration();
                    drawerLayout.closeDrawers();
                    ActionBar ac = getSupportActionBar();
                    ac.setTitle(R.string.mode_concentration);
                    break;
                }
                case R.id.theme: {
                    MenuItem menuItem = navigationView.getMenu().findItem(R.id.theme);// This is the menu item that contains your switch
                    Switch drawerSwitch = (Switch) menuItem.getActionView().findViewById(R.id.theme);
                    if (sharedPref.loadNightModeState()) {
                        drawerSwitch.setChecked(true);
                    }
                    drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                sharedPref.setNightModeState(true);
                                restartApp();
                            } else {
                                sharedPref.setNightModeState(false);
                                restartApp();
                            }
                        }
                    });
                }
                break;
            }
            return false;
        });


        if (savedInstanceState == null) navigator.showNotes();
        ActionBar ac = getSupportActionBar();
        ac.setTitle(R.string.mode_notes);
    }

    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
        startActivity(i);
        finish();
    }
}
