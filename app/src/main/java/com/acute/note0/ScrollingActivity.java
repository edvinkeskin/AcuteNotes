package com.acute.note0;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import org.jetbrains.annotations.NotNull;

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
        toolbar.setTitle(id);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextNote();
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.prev) {
                    prevNote();
                } else if(id == R.id.next) {
                    nextNote();
                }
                return true;
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
            Log.v("test","test");
            prevNote();
            return true;
        } else if(id == R.id.next) {
            Log.v("test2","test2");
            nextNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prevNote() {
        EditText input = findViewById(R.id.input);
        note.setNote(input.getText().toString());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        note = note.getPreviousNote();
        input.setText(note.getNote());
        id = note.getId();
        toolbar.setTitle(id);
    }

    private void nextNote() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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