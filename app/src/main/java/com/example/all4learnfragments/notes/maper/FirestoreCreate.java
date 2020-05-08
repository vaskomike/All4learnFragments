package com.example.all4learnfragments.notes.maper;

public class FirestoreCreate {
    private static FirestoreCreate instance;

    private NotesOperations dao;

    public static FirestoreCreate getInstance() {
        if (instance == null) {
            instance = new FirestoreCreate();
        }
        return instance;
    }

    public NotesOperations getDao() {
        if (dao == null) {
            dao = new NotesManagement();
        }
        return dao;
    }
}
