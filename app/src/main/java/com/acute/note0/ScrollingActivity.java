package com.acute.note0;

import android.os.Bundle;
import android.widget.EditText;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class ScrollingActivity extends AppCompatActivity {
    private static int notes = 1;
    private static String id;
    private static Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        id = "Note" + notes++;
        note = new Note(id);
        toolBarLayout.setTitle(id);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText input = findViewById(R.id.input);
                input.setText(note.getNote());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.prev) {
            prevNote();
            EditText input = findViewById(R.id.input);
            input.setText(note.getNote());
            return true;
        } else if(id == R.id.next) {
            nextNote();
            EditText input = findViewById(R.id.input);
            input.setText(note.getNote());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prevNote() {
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        note = note.getPreviousNote();
        id = note.getId();
        toolBarLayout.setTitle(id);
    }

    private void nextNote() {
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if(note.getNextNote() == null) {
            id = "Note" + notes++;
            note = new Note(id,note);
        } else {
            note = note.getNextNote();
            id = note.getId();
        }
        toolBarLayout.setTitle(id);
    }
}