package com.example.loadimagefromserver.app.fragment;

public class CommentSlideUp {
    private int image;
    private String name;
    private String comment;

    public CommentSlideUp(int image, String name, String comment) {
        this.image = image;
        this.name = name;
        this.comment = comment;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }
}
