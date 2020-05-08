package com.example.all4learnfragments.notes.maper;

import com.example.all4learnfragments.notes.Note;

import java.lang.ref.WeakReference;
import java.util.List;
import com.google.firebase.firestore.ListenerRegistration;

public interface NotesOperations {
    ListenerRegistration listenNotes(WeakReference<GetNotesListener> listener);

    void deleteNote(Note note);


    interface GetNotesListener {

        void onNotesLoaded(List<Note> notes);

        void onNotesLoadFailed();
    }
}
