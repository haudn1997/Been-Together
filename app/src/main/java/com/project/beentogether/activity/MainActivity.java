package com.project.beentogether.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.beentogether.R;
import com.project.beentogether.util.SectionsPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;


public class MainActivity extends AppCompatActivity {
    private TextView mTitle, mTxtMale, mTxtFemale;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private TabLayout mTabs;
    private ImageView mImageMale, mImageFemale, mIconMale, mIconFemale, mCalendar, mSettings, mImageLove;
    private String menuInforMaleFemale;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 22;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalendar = findViewById(R.id.calendar);
        mSettings = findViewById(R.id.setting);
        mTitle = findViewById(R.id.title);
        viewPager = findViewById(R.id.view_pager);
        mTabs = findViewById(R.id.tabs);
        mImageMale = findViewById(R.id.imageMale);
        mImageFemale = findViewById(R.id.imageFemale);
        mImageLove = findViewById(R.id.imageLove);
        mIconMale = findViewById(R.id.iconMale);
        mIconFemale = findViewById(R.id.iconFemale);
        mTxtMale = findViewById(R.id.txtMale);
        mTxtFemale = findViewById(R.id.txtFemale);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("color_love").child("color");

        onClickItemActionBar();

        // Read from the database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                mImageLove.setColorFilter(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        mTabs.setupWithViewPager(viewPager);

        //Set Icon to Tab Menu
        mTabs.getTabAt(0).setIcon(R.drawable.tab_date);
        mTabs.getTabAt(1).setIcon(R.drawable.tab_album);
        mTabs.getTabAt(2).setIcon(R.drawable.tab_note);
    }

    private void onClickItemActionBar() {
        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NoteCalendarActivity.class));
            }
        });
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mImageMale.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuInforMaleFemale = "ImageMale";
                registerForContextMenu(mImageMale);
                return false;
            }
        });
        mImageFemale.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuInforMaleFemale = "ImageFemale";
                registerForContextMenu(mImageFemale);
                return false;
            }
        });
        mIconMale.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuInforMaleFemale = "IconMale";
                registerForContextMenu(mIconMale);
                return false;
            }
        });
        mIconFemale.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuInforMaleFemale = "IconFemale";
                registerForContextMenu(mIconFemale);
                return false;
            }
        });
        mTxtMale.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuInforMaleFemale = "MaleName";
                registerForContextMenu(mTxtMale);
                return false;
            }
        });
        mTxtFemale.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuInforMaleFemale = "FemaleName";
                registerForContextMenu(mTxtFemale);
                return false;
            }
        });
        mImageLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseColorForIconLove();
            }
        });
    }

    private void chooseColorForIconLove() {
        final ColorPicker colorPicker = new ColorPicker(MainActivity.this);
        ArrayList<String> colors = createListColor();

        colorPicker.setDefaultColorButton(Color.parseColor("#f84c44"))
                .setTitle("Select a Color")
                .setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        mImageLove.setColorFilter(color);
                        mDatabaseReference.setValue(color);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

    private ArrayList<String> createListColor() {
        ArrayList<String> colors = new ArrayList<>();

        colors.add("#000000");
        colors.add("#F44336");
        colors.add("#E91E63");
        colors.add("#FF2C93");
        colors.add("#9C27B0");
        colors.add("#673AB7");
        colors.add("#3F51B5");
        colors.add("#2196F3");
        colors.add("#03A9F4");
        colors.add("#00BCD4");
        colors.add("#009688");
        colors.add("#4CAF50");
        colors.add("#8BC34A");
        colors.add("#CDDC39");
        colors.add("#FFEB3B");
        colors.add("#FFC107");
        colors.add("#FF9800");
        colors.add("#795548");
        colors.add("#607D8B");
        colors.add("#9E9E9E");

        return colors;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.avatar:
                handleAvatarMenu(menuInforMaleFemale);
                return true;
            case R.id.avatarShape:

                return true;
            case R.id.displayName:

                return true;
            case R.id.birthday:

                return true;
            case R.id.sex:

                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void handleAvatarMenu(String menuInforMaleFemale) {
        switch (menuInforMaleFemale) {
            case "ImageMale":
                chooseImage();
                break;
            case "ImageFemale":
                break;
            case "IconMale":
                break;
            case "IconFemale":
                break;
            case "MaleName":
                break;
            case "FemaleName":

        }
    }

    private void chooseImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
                // Get the Uri of data
                filePath = data.getData();
                // Setting image on image view using Bitmap
                Picasso.with(mImageMale.getContext()).load(filePath).into(mImageMale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_change_information_male_female, menu);
    }
}
