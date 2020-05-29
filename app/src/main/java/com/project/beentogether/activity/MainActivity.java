package com.project.beentogether.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.beentogether.R;
import com.project.beentogether.model.InformationMaleAndFemale;
import com.project.beentogether.util.FirebaseUtil;
import com.project.beentogether.util.SectionsPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import petrov.kristiyan.colorpicker.ColorPicker;


public class MainActivity extends AppCompatActivity {
    private TextView mTitle, mTxtMale, mTxtFemale;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private TabLayout mTabs;
    private ImageView mImageMale, mImageFemale, mIconMale, mIconFemale, mCalendar, mSettings, mImageLove;
    private static String menuInforMaleFemale;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 22;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReferenceColor, mDatabaseReferenceMale, mDatabaseReferenceFemale;

    //Save image to storage
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private InformationMaleAndFemale mInformationMaleAndFemale;

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

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mInformationMaleAndFemale = new InformationMaleAndFemale();

        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;

        FirebaseUtil.openFbReference("color_love");
        mDatabaseReferenceColor = FirebaseUtil.mDatabaseReference.child("color");

        FirebaseUtil.openFbReference("information_male");
        mDatabaseReferenceMale = FirebaseUtil.mDatabaseReference;

        FirebaseUtil.openFbReference("information_female");
        mDatabaseReferenceFemale = FirebaseUtil.mDatabaseReference;

        onClickItemActionBar();

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        mTabs.setupWithViewPager(viewPager);

        //Set Icon to Tab Menu
        mTabs.getTabAt(0).setIcon(R.drawable.tab_date);
        mTabs.getTabAt(1).setIcon(R.drawable.tab_album);
        mTabs.getTabAt(2).setIcon(R.drawable.tab_note);

        readDataFromFirebase();
    }

    private void readDataFromFirebase() {
        // Read data of color from the Firebase database
        mDatabaseReferenceColor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                mImageLove.setColorFilter(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        // Read data of image male from the Firebase database
        mDatabaseReferenceMale.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                InformationMaleAndFemale mInformationMaleAndFemale = dataSnapshot.getValue(InformationMaleAndFemale.class);
                Picasso.with(mImageMale.getContext()).load(mInformationMaleAndFemale.getImageAvatarUrl()).into(mImageMale);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        // Read data of image female from the Firebase database
        mDatabaseReferenceFemale.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                InformationMaleAndFemale mInformationMaleAndFemale = dataSnapshot.getValue(InformationMaleAndFemale.class);
                Picasso.with(mImageFemale.getContext()).load(mInformationMaleAndFemale.getImageAvatarUrl()).into(mImageFemale);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
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
                        mDatabaseReferenceColor.setValue(color);
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

    private void handleAvatarMenu(String menuInforMaleOrFemale) {
        switch (menuInforMaleOrFemale) {
            case "ImageMale":
            case "ImageFemale":
                chooseImage(menuInforMaleOrFemale);
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

    private void chooseImage(String menuInforMaleOrFemale) {
        menuInforMaleFemale = menuInforMaleOrFemale;
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
                if (menuInforMaleFemale == "ImageMale") {
                    Picasso.with(mImageMale.getContext()).load(filePath).into(mImageMale);
                }
                if (menuInforMaleFemale == "ImageFemale") {
                    Picasso.with(mImageFemale.getContext()).load(filePath).into(mImageFemale);
                }

                uploadImage(menuInforMaleFemale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadImage(final String menuInforMaleFemale) {
        if (filePath != null) {
            final StorageReference ref = storageReference.child("image_male_female/" + UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String imageName = taskSnapshot.getStorage().getPath();
                    mInformationMaleAndFemale.setImageAvatar(imageName);
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = String.valueOf(uri);
                            mInformationMaleAndFemale.setImageAvatarUrl(imageUrl);

                            //Write Image Male to Firebase
                            if (menuInforMaleFemale == "ImageMale") {
                                mDatabaseReferenceMale.child("imageAvatar").setValue(mInformationMaleAndFemale.getImageAvatar());
                                mDatabaseReferenceMale.child("imageAvatarUrl").setValue(mInformationMaleAndFemale.getImageAvatarUrl());
                            }

                            //Write Image Female to Firebase
                            if (menuInforMaleFemale == "ImageFemale") {
                                mDatabaseReferenceFemale.child("imageAvatar").setValue(mInformationMaleAndFemale.getImageAvatar());
                                mDatabaseReferenceFemale.child("imageAvatarUrl").setValue(mInformationMaleAndFemale.getImageAvatarUrl());
                            }
                        }
                    });

                }
            });
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_change_information_male_female, menu);
    }
}
