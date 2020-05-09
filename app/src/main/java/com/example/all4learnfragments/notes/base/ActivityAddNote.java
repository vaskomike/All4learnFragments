package com.example.all4learnfragments.notes.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learnfragments.R;
import com.example.all4learnfragments.drawer.SharedPref;
import com.example.all4learnfragments.notes.Note;
import com.example.all4learnfragments.notes.maper.NotesMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityAddNote extends AppCompatActivity {

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    SharedPref sharedPref;

    private static TextView dateNote;

    private TextInputEditText titleInputEditText, textInputEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.darkTheme);
        } else setTheme(R.style.lightTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);

        titleInputEditText = findViewById(R.id.title);
        textInputEditText = findViewById(R.id.text);
        dateNote = findViewById(R.id.dateNote);
        dateNote.setText(noteDate);

    }

    private String getUid() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    Calendar calendar = Calendar.getInstance();
    static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");

    private String noteDate = format.format(calendar.getTime());

    public Note saveNote(String title, String text, String date) {

        title = titleInputEditText.getText().toString();
        text = textInputEditText.getText().toString();

        if (!title.equals("") || !text.equals("")) {
            DocumentReference reference = fireStore.collection(NotesMapper.COLLECTION).document();
            reference.set(NotesMapper.newNote(getUid(), title, text, noteDate));
            return new Note(reference.getId(), getUid(), title, text, date);
        } else Toast.makeText(ActivityAddNote.this, "Empty note", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveNote(titleInputEditText.toString(), textInputEditText.toString(), noteDate);
        setResult(Activity.RESULT_OK);
        finish();
    }
}