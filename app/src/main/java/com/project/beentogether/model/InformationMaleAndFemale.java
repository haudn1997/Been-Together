package com.project.beentogether.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InformationMaleAndFemale implements Parcelable {
    private String displayName;
    private String imageAvatarUrl;
    private String imageAvatar;
    private String imageSex;
    private String imageSexUrl;

    public InformationMaleAndFemale() {

    }

    public InformationMaleAndFemale(String displayName, String imageAvatarUrl, String imageAvatar, String imageSex, String imageSexUrl) {
        this.displayName = displayName;
        this.imageAvatarUrl = imageAvatarUrl;
        this.imageAvatar = imageAvatar;
        this.imageSex = imageSex;
        this.imageSexUrl = imageSexUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImageAvatarUrl() {
        return imageAvatarUrl;
    }

    public void setImageAvatarUrl(String imageAvatarUrl) {
        this.imageAvatarUrl = imageAvatarUrl;
    }

    public String getImageAvatar() {
        return imageAvatar;
    }

    public void setImageAvatar(String imageAvatar) {
        this.imageAvatar = imageAvatar;
    }

    public String getImageSex() {
        return imageSex;
    }

    public void setImageSex(String imageSex) {
        this.imageSex = imageSex;
    }

    public String getImageSexUrl() {
        return imageSexUrl;
    }

    public void setImageSexUrl(String imageSexUrl) {
        this.imageSexUrl = imageSexUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeString(this.imageAvatarUrl);
        dest.writeString(this.imageAvatar);
        dest.writeString(this.imageSex);
        dest.writeString(this.imageSexUrl);
    }

    protected InformationMaleAndFemale(Parcel in) {
        this.displayName = in.readString();
        this.imageAvatarUrl = in.readString();
        this.imageAvatar = in.readString();
        this.imageSex = in.readString();
        this.imageSexUrl = in.readString();
    }

    public static final Creator<InformationMaleAndFemale> CREATOR = new Creator<InformationMaleAndFemale>() {
        @Override
        public InformationMaleAndFemale createFromParcel(Parcel source) {
            return new InformationMaleAndFemale(source);
        }

        @Override
        public InformationMaleAndFemale[] newArray(int size) {
            return new InformationMaleAndFemale[size];
        }
    };
}
