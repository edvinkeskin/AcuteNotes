package com.acute.note0;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ScrollingActivity extends AppCompatActivity {
    private static int notes = 1;
    private static String id;
    private static Note note;
    //note status true if cached, false if it has to be fetched
    private static HashMap<String,Boolean> noteStatus;
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

        noteStatus = new HashMap<>();
        String notepad[] = readNotepad();

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

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void prevNote() {
        EditText input = findViewById(R.id.input);
        note.setNote(input.getText().toString());
        writeNote(id);
        Toolbar toolbar = findViewById(R.id.toolbar);
        note = note.getPreviousNote();
        id = note.getId();
        readNote(id);

        input.setText(note.getNote());
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
            writeNote(id);
            note = note.getNextNote();
            id = note.getId();
            readNote(id);
        }

        input.setText(note.getNote());
        toolbar.setTitle(id);
    }

    private void writeNote(String filename) {
        //WRITE
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(note.getNote().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readNote(String filename) {
        //READ
        FileInputStream fis = null;
        try {
            fis = openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(fis);
        scanner.useDelimiter("\\Z");
        String content = scanner.next();
        scanner.close();
    }

    private String[] readNotepad() {
        FileInputStream fis = null;
        try {
            fis = openFileInput("notepad");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(fis);
        scanner.useDelimiter("\\Z");
        String content = scanner.next();
        scanner.close();
    }
}