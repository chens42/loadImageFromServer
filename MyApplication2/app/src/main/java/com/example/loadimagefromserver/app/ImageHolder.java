package com.example.loadimagefromserver.app;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageHolder {
    private ImageView imageView;
    private TextView textView;

    public ImageHolder(View view) {
        imageView = (ImageView) view.findViewById(R.id.imageField);
        textView = (TextView) view.findViewById(R.id.country);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }
}
