package com.example.all4learnfragments.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learnfragments.R;
import com.example.all4learnfragments.drawer.DrawerActivity;
import com.example.all4learnfragments.notes.utils.Schedulers;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class SplashAuth extends AppCompatActivity {

    private static final long DELAY_MS = 1000;

    private static final int RC_SIGN_IN = 1;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private Handler main = Schedulers.getHandler();


    private Runnable exitRunnable = () -> {
        if (firebaseAuth.getCurrentUser() != null) {
            startNotesActivity();
        } else {
            startAuth();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        main.postDelayed(exitRunnable, DELAY_MS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case RC_SIGN_IN: {
                if (resultCode == Activity.RESULT_OK) {
                    startNotesActivity();

                } else {
                    finish();
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startAuth() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN
        );
    }

    private void startNotesActivity() {
        startActivity(DrawerActivity.createIntent(this));
        finish();
    }
}