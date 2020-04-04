package com.project.beentogether.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteCalendarModel implements Parcelable {
    private String id;
    private String contentNote;
    private String dateCreated;
    private String imageUrl;

    public NoteCalendarModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentNote() {
        return contentNote;
    }

    public void setContentNote(String contentNote) {
        this.contentNote = contentNote;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public NoteCalendarModel(String contentNote, String dateCreated, String imageUrl) {
        this.setContentNote(contentNote);
        this.setDateCreated(dateCreated);
        this.setImageUrl(imageUrl);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.contentNote);
        dest.writeString(this.dateCreated);
        dest.writeString(this.imageUrl);
    }

    protected NoteCalendarModel(Parcel in) {
        this.id = in.readString();
        this.contentNote = in.readString();
        this.dateCreated = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<NoteCalendarModel> CREATOR = new Creator<NoteCalendarModel>() {
        @Override
        public NoteCalendarModel createFromParcel(Parcel source) {
            return new NoteCalendarModel(source);
        }

        @Override
        public NoteCalendarModel[] newArray(int size) {
            return new NoteCalendarModel[size];
        }
    };
}
