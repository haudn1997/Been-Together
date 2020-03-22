package com.example.beentogether.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteCalendarModel implements Parcelable {
    private String contentNote;
    private Date dateCreated;
    private String imageUrl;

    public NoteCalendarModel() {
    }

    public NoteCalendarModel(String contentNote, Date dateCreated, String imageUrl) {
        this.contentNote = contentNote;
        this.dateCreated = dateCreated;
        this.imageUrl = imageUrl;
    }

    protected NoteCalendarModel(Parcel in) {
        contentNote = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<NoteCalendarModel> CREATOR = new Creator<NoteCalendarModel>() {
        @Override
        public NoteCalendarModel createFromParcel(Parcel in) {
            return new NoteCalendarModel(in);
        }

        @Override
        public NoteCalendarModel[] newArray(int size) {
            return new NoteCalendarModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contentNote);
        dest.writeString(imageUrl);
    }
}
