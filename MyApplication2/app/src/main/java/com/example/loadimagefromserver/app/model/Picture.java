package com.example.loadimagefromserver.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonProperty;

public class Picture implements Parcelable {
    @JsonProperty("country")
    private String country="";
    @JsonProperty("image")
    private String image="";

    public Picture(String image, String country) {
        this.image = image;
        this.country = country;

    }

    public String getCountry() {
        return country;
    }



    public String getImage() {
        return image;
    }

    public Picture() {
    }



    public static final Parcelable.Creator<Picture> CREATOR = new Creator<Picture>() {
        public Picture createFromParcel(Parcel source) {
            Picture pic = new Picture();
            pic.country = source.readString();
            pic.image = source.readString();
            return pic;
        }
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(image);
    }
}
