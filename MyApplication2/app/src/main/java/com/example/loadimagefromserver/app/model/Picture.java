package com.example.loadimagefromserver.app.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Picture {
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
}
