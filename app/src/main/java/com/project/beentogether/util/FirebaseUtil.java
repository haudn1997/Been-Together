package com.project.beentogether.util;

import com.project.beentogether.model.NoteCalendarModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseUtil;
    public static FirebaseStorage mStorage;
    public static StorageReference mStorageReference;
    public static ArrayList<NoteCalendarModel> mNotes;
//    private static ListActivity caller;

    private FirebaseUtil() {
    }

    public static void openFbReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            connectStorage();
        }
        mNotes = new ArrayList<>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    public static void connectStorage() {
        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference().child("deals_picture");
    }
}
