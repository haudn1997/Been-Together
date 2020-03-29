package com.project.beentogether.model;

import java.io.Serializable;

public class NoteCalendarModel implements Serializable {
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
}
