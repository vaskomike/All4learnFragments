package com.example.all4learnfragments.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

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


    private Switch mySwitch;

    public static Intent createIntent(Context context) {
        return new Intent(context, DrawerActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mySwitch = findViewById(R.id.theme);

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
                case R.id.theme:{
                    Toast.makeText( this, "switched!", Toast.LENGTH_SHORT).show();
//                    AppCompatDelegate.setDefaultNightMode(
//                            AppCompatDelegate.MODE_NIGHT_YES);
                }

            }
            return false;
        });

        if (savedInstanceState == null) navigator.showNotes();
        ActionBar ac = getSupportActionBar();
        ac.setTitle(R.string.mode_notes);
    }

}
