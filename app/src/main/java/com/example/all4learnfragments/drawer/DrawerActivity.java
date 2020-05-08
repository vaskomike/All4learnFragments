package com.example.all4learnfragments.drawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.all4learnfragments.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity {
    private DrawerActivityNavigator navigator = new DrawerActivityNavigator(getSupportFragmentManager());

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
                    break;
                }
                case R.id.concentrateVector: {
                    navigator.showConcentration();
                    break;
                }
            }
            return true;
        });

        if (savedInstanceState == null) navigator.showNotes();
    }

}
