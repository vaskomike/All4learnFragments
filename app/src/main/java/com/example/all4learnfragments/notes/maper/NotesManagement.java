package com.example.all4learnfragments.notes.maper;

import com.example.all4learnfragments.notes.Note;
import com.example.all4learnfragments.notes.utils.Schedulers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NotesManagement implements NotesOperations {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    public ListenerRegistration listenNotes(WeakReference<NotesOperations.GetNotesListener> listenerRef) {
        return getUserNotesQuery().orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(Schedulers.getIo(), (queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots != null) {
                        List<Note> notes = parseNotes(queryDocumentSnapshots.getDocuments());
                        NotesOperations.GetNotesListener listener = listenerRef.get();
                        if (listener != null) {
                            listener.onNotesLoaded(notes);
                        }
                    } else if (e != null) {
                        NotesOperations.GetNotesListener listener = listenerRef.get();
                        if (listener != null) {
                            listener.onNotesLoadFailed();
                        }
                    }
                });
    }

    @Override
    public void deleteNote(Note note) {
        firebaseFirestore.collection(NotesMapper.COLLECTION).document(note.getId()).delete();
    }


    private Query getUserNotesQuery() {
        return firebaseFirestore.collection(NotesMapper.COLLECTION)
                .whereEqualTo(NotesMapper.OWNER_ID, getUid());
    }


    private List<Note> parseNotes(List<DocumentSnapshot> documents) {
        List<Note> notes = new ArrayList<>(documents.size());
        for (DocumentSnapshot snapshot : documents) {
            notes.add(NotesMapper.fromDocument(snapshot));
        }
        return notes;
    }


    private String getUid() {
        return firebaseAuth.getCurrentUser().getUid();
    }
}