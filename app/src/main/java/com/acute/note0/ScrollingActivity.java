package com.acute.note0;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ScrollingActivity extends AppCompatActivity {
    private static int noteCount;
    private static String id;
    private static Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);

        noteCount = readNoteCount();
        //if previous notepad exists load it else create new note/notepad
        if(noteCount > 1) {
            loadNotepad(noteCount);
        } else {
            id = "Note" + noteCount++;
            note = new Note(id);

        }
        toolBarLayout.setTitle(id);
        toolbar.setTitle(id);

        //add note fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> nextNote());

        //bottom tab control
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.prev) {
                prevNote();
            } else if(id == R.id.next) {
                nextNote();
            }
            return true;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeNote(id);
        writeNoteCount();
    }

    private void prevNote() {
        EditText input = findViewById(R.id.input);
        note.setNote(input.getText().toString());
        writeNote(id);
        Toolbar toolbar = findViewById(R.id.toolbar);
        note = note.getPreviousNote();
        id = note.getId();

        input.setText(note.getNote());
        toolbar.setTitle(id);
    }

    private void nextNote() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        EditText input = findViewById(R.id.input);
        note.setNote(input.getText().toString());
        writeNote(id);
        if(note.getNextNote() == null) {
            id = "Note" + noteCount++;
            note = new Note(id,note);
        } else {
            note = note.getNextNote();
            id = note.getId();
        }

        input.setText(note.getNote());
        toolbar.setTitle(id);
    }

    private void writeNote(String filename) {
        //WRITE
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Log.v("test", note.getNote());
        editor.putString(filename, note.getNote());
        editor.apply();

    }

    private void writeNoteCount() {
        //WRITE
        Log.v("test","count" + noteCount);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("noteCount", noteCount);
        editor.apply();

    }

    private void readNote(String filename) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        Log.v("test", sharedPref.getString(filename, "invalid"));
        note.setNote(sharedPref.getString(filename, "invalid"));
    }

    private int readNoteCount() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt("noteCount", 1);
    }

    private void loadNotepad(int noteCount) {
        Log.v("test","count" + noteCount);
        id = "Note" + (noteCount-1);
        note = new Note(id);
        for(int i = noteCount-2; i >= 1; i--) {
            id = "Note" + i;
            note = new Note(note,id);
            readNote(id);
        }
    }
}