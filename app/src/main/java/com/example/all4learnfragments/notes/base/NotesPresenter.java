package com.example.all4learnfragments.notes.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.all4learnfragments.notes.Note;
import com.example.all4learnfragments.notes.maper.FirestoreCreate;
import com.example.all4learnfragments.notes.maper.NotesOperations;
import com.example.all4learnfragments.notes.utils.BasePresenter;
import com.google.firebase.firestore.ListenerRegistration;

import java.lang.ref.WeakReference;
import java.util.List;

public class NotesPresenter extends BasePresenter<NotesPresenter.Listener> implements NotesOperations.GetNotesListener {

    private final NotesOperations notesDao = FirestoreCreate.getInstance().getDao();

    private ListenerRegistration registration;

    public NotesPresenter(@NonNull Listener listener) {
        super(listener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void loadNotes() {
        registration = notesDao.listenNotes(new WeakReference<>(this));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopLoadNotes() {
        registration.remove();
    }

    @Override
    public void onNotesLoaded(List<Note> notes) {
        postOnMainThread(listener -> listener.onNotesLoaded(notes));
    }

    @Override
    public void onNotesLoadFailed() {

    }

    public interface Listener extends LifecycleOwner {

        void onNotesLoaded(List<Note> notes);
    }
}
