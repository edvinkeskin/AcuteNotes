package com.acute.note0;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ScrollingActivity extends AppCompatActivity {
    private static int notes = 1;
    private static String id;
    private static Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);

        id = "Note" + notes++;
        note = new Note(id);
        toolBarLayout.setTitle(id);
        toolbar.setTitle(id);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> nextNote());

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

    private void prevNote() {
        EditText input = findViewById(R.id.input);
        note.setNote(input.getText().toString());
        Toolbar toolbar = findViewById(R.id.toolbar);
        note = note.getPreviousNote();
        input.setText(note.getNote());
        id = note.getId();
        toolbar.setTitle(id);
    }

    private void nextNote() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        EditText input = findViewById(R.id.input);
        note.setNote(input.getText().toString());
        if(note.getNextNote() == null) {
            id = "Note" + notes++;
            note = new Note(id,note);
        } else {
            note = note.getNextNote();
            id = note.getId();
        }
        input.setText(note.getNote());
        toolbar.setTitle(id);
    }
}