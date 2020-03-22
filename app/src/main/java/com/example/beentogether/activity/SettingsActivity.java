package com.example.beentogether.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beentogether.R;

public class SettingsActivity extends AppCompatActivity {
    private ImageView mCalendar;
    private ImageView mSettings;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mCalendar = findViewById(R.id.calendar);
        mSettings = findViewById(R.id.setting);
        mTitle = findViewById(R.id.title);
        onClickItemActionBar();
    }

    private void onClickItemActionBar() {
        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, NoteCalendarActivity.class));
            }
        });
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
            }
        });
    }
}
