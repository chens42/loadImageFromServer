package com.example.loadimagefromserver.app.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loadimagefromserver.app.R;

public class ImageHolder {
    private ImageView imageView;
    private TextView textView;

    public ImageHolder(View view) {
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textView = (TextView) view.findViewById(R.id.countryTextView);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }
}
