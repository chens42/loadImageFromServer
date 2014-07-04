package com.example.loadimagefromserver.app.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
        ImageView imageView = (ImageView) root.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(String.format(HttpConstant.IMAGE_URL, picture.getImage()), imageView);

        WindowManager wm = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        TextView textView= (TextView) root.findViewById(R.id.countryTextView);
        FrameLayout.LayoutParams llp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 3*screenWidth*108/(192*2), 0, 0); // llp.setMargins(left, top, right, bottom);
        textView.setLayoutParams(llp);
        textView.setText(picture.getCountry());
        return root;
    }
}