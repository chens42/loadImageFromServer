package com.example.loadimagefromserver.app.holder;


import android.view.View;
import android.widget.ImageView;

import com.example.loadimagefromserver.app.R;

public class ListViewImageHolder {
    private ImageView imageView;

    public ListViewImageHolder(View view) {
        imageView = (ImageView) view.findViewById(R.id.pictureViewActivityListViewImageRow);
    }

    public ImageView getImageView() {
        return imageView;
    }

}
