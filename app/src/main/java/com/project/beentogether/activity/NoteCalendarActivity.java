package com.project.beentogether.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.beentogether.R;
import com.project.beentogether.util.NoteAdapter;


public class NoteCalendarActivity extends AppCompatActivity {
    private ImageView mCalendar;
    private ImageView mSettings;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_calendar);

        ImageButton imageButton = findViewById(R.id.noteCalendar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateNoteCalendarActivity.class);
                startActivity(intent);
            }
        });

        mCalendar = findViewById(R.id.calendar);
        mSettings = findViewById(R.id.setting);
        mTitle = findViewById(R.id.title);
        onClickItemActionBar();
    }

    private void onClickItemActionBar() {
        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteCalendarActivity.this, NoteCalendarActivity.class));
            }
        });
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteCalendarActivity.this, MainActivity.class));
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteCalendarActivity.this, SettingsActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rvNotes = findViewById(R.id.listNoteCalendar);
        final NoteAdapter adapter = new NoteAdapter();
        rvNotes.setAdapter(adapter);
        LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNotes.setLayoutManager(notesLayoutManager);
    }
}
