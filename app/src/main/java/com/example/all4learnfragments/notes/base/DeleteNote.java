package com.example.all4learnfragments.notes.base;

import com.example.all4learnfragments.notes.Note;
import com.example.all4learnfragments.notes.maper.NotesMapper;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteNote {
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    public DeleteNote(Note note) {

        fireStore.collection(NotesMapper.COLLECTION).document(note.getId()).delete();

    }
}
