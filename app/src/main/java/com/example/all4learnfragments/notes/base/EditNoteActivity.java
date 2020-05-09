package com.example.all4learnfragments.notes.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learnfragments.R;
import com.example.all4learnfragments.drawer.SharedPref;
import com.example.all4learnfragments.notes.Note;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.all4learnfragments.notes.maper.NotesMapper.COLLECTION;
import static com.example.all4learnfragments.notes.maper.NotesMapper.DATE;
import static com.example.all4learnfragments.notes.maper.NotesMapper.TEXT;
import static com.example.all4learnfragments.notes.maper.NotesMapper.TITLE;

public class EditNoteActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "note";

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    private TextView dateNote;

    SharedPref sharedPref;

    private TextInputEditText titleInputEditText, textInputEditText;

    Calendar calendar = Calendar.getInstance();
    static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
    private String noteDate = format.format(calendar.getTime());

    public static Intent createIntent(Context context, Note item) {
        Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(EXTRA_NOTE, item);
        return intent;
    }

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

        Note note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note == null) {
            finish();
            return;
        }
        titleInputEditText.setText(note.getTitle());
        textInputEditText.setText(note.getText());
        dateNote.setText(note.getDate());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Note note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note == null) {
            finish();
            return;
        }

        DocumentReference edit = fireStore.collection(COLLECTION).document(note.getId());
        Map<String, Object> data = new HashMap<>();

        if (!note.getTitle().equals(titleInputEditText.getText().toString())) {
            data.put(TITLE, titleInputEditText.getText().toString());
        }

        if (!note.getText().equals(textInputEditText.getText().toString())) {
            data.put(TEXT, textInputEditText.getText().toString());
        }
        data.put(DATE, dateNote);

        if (note.getTitle().equals(titleInputEditText.getText().toString()) && note.getText().equals(textInputEditText.getText().toString())) {
            data.put(DATE, note.getDate());
        }

        data.put(DATE, noteDate);
        edit.set(
                data,
                SetOptions.merge()
        );
        setResult(Activity.RESULT_OK);
        finish();
    }

}
