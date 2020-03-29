package com.project.beentogether.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.beentogether.R;
import com.project.beentogether.model.NoteCalendarModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class CreateNoteCalendarActivity extends AppCompatActivity {
    private Calendar calendar;
    private int year, month, day;
    private Button mBtnSaveNote, mDateView;
    private ImageView mImageView;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    private NoteCalendarModel mNote;
    private EditText mTxtContentNote;

    //Save data to firebase db
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    //Save image to storage
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private boolean isCheckImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note_calendar);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("notes");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mNote = new NoteCalendarModel();

        mDateView = findViewById(R.id.btnDateCreated);
        mBtnSaveNote = findViewById(R.id.btnSaveNote);
        mTxtContentNote = findViewById(R.id.txtContentNote);
        mImageView = findViewById(R.id.imageNote);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        isCheckImage = false;

        mTxtContentNote.requestFocus();

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        mBtnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTxtContentNote.getText().toString().trim().length() == 0 || mDateView.getText().toString() == getString(R.string.created_date) || !isCheckImage) {
                    startActivity(new Intent(CreateNoteCalendarActivity.this, NoteCalendarActivity.class));
                    Toast.makeText(CreateNoteCalendarActivity.this, "Không đủ thông tin để lưu", Toast.LENGTH_SHORT).show();
                } else {
                    saveNoteCalendar();
                }

            }
        });
    }

    private void chooseImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("been_together_picture/" + UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                    if (downloadUri.isSuccessful()) {
                        String generatedFilePath = downloadUri.getResult().toString();
                        mNote.setImageUrl(generatedFilePath);
                    }
                    mDatabaseReference.push().setValue(mNote);
                    startActivity(new Intent(CreateNoteCalendarActivity.this, NoteCalendarActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(CreateNoteCalendarActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

    private void saveNoteCalendar() {
        String contentNote = mTxtContentNote.getText().toString();
        String dateCreated = mDateView.getText().toString();
        mNote.setContentNote(contentNote);
        mNote.setDateCreated(dateCreated);
        uploadImage();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        mDateView.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
                // Get the Uri of data
                filePath = data.getData();
                // Setting image on image view using Bitmap
                Picasso.with(mImageView.getContext()).load(filePath).into(mImageView);
                isCheckImage = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
