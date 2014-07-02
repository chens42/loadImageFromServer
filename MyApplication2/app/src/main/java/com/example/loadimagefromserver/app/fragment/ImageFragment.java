package com.example.loadimagefromserver.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.common.HttpConstant;
import com.example.loadimagefromserver.app.model.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageFragment extends android.app.Fragment {
    public static final String OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.image_viewer, container, false);
        Bundle bundle = getArguments();
        Picture picture = bundle.getParcelable(OBJECT);
        ImageLoader.getInstance().displayImage(String.format(HttpConstant.IMAGE_URL, picture.getImage()), (android.widget.ImageView) root.findViewById(R.id.image));
        return root;
    }
}