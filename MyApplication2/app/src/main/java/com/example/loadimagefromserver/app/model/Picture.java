package com.example.loadimagefromserver.app.model;

import android.graphics.Bitmap;

import org.codehaus.jackson.annotate.JsonProperty;

public class Picture {
    @JsonProperty("country")
    private String country="";
    @JsonProperty("image")
    private String image="";
    private Bitmap bitmap;

    public Picture(String image, String country) {
        this.image = image;
        this.country = country;

    }

    public String getCountry() {
        return country;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImage() {
        return image;
    }

    public Picture() {
    }
}
