package com.example.all4learnfragments.drawer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.all4learnfragments.R;
import com.example.all4learnfragments.concentration.ConcentrationFragment;
import com.example.all4learnfragments.notes.NotesFragment;

public class DrawerActivityNavigator  {

    private final FragmentManager manager;

    public DrawerActivityNavigator(FragmentManager manager) {
        this.manager = manager;
    }

    public void showNotes() {
        replaceRoot(new NotesFragment());
    }

    public void showConcentration() {
        replaceRoot(new ConcentrationFragment());
    }

    private void replaceRoot(Fragment fragment) {
        manager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .setPrimaryNavigationFragment(fragment)
                .commit();
    }
}
