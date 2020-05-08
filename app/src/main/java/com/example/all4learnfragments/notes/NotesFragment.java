package com.example.all4learnfragments.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.all4learnfragments.R;
import com.example.all4learnfragments.notes.base.ActivityAddNote;
import com.example.all4learnfragments.notes.base.DeleteNote;
import com.example.all4learnfragments.notes.base.EditNoteActivity;
import com.example.all4learnfragments.notes.base.NotesAdapter;
import com.example.all4learnfragments.notes.base.NotesPresenter;
import com.example.all4learnfragments.notes.utils.OnItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesFragment extends Fragment implements NotesPresenter.Listener, OnItemClickListener<Note> {

    private NotesAdapter adapter;

    NotesPresenter loadNotes = new NotesPresenter(this);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new NotesAdapter(getLayoutInflater(), this);


        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerNotes);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton addNote = (FloatingActionButton) view.findViewById(R.id.addNote);
        addNote.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ActivityAddNote.class);
        });
        view.findViewById(R.id.addNote).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ActivityAddNote.class);
            startActivity(intent);
        });
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            new DeleteNote(adapter.getCurrentList().get(viewHolder.getAdapterPosition()));
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onNotesLoaded(List<Note> notes) {
        adapter.submitList(notes);
    }

    @Override
    public void onItemClicked(Note item) {
        startActivity(EditNoteActivity.createIntent(getActivity(), item));

    }

}


