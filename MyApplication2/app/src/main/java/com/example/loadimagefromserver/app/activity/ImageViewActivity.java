package com.example.loadimagefromserver.app.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.loadimagefromserver.app.fragment.ImageFragment;
import com.example.loadimagefromserver.app.listener.OnSwipeTouchListener;
import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.model.Picture;

import java.util.ArrayList;

public class ImageViewActivity extends Activity {
    public static final String INDEX = "index";
    public static final String LIST_NAME = "list name";
    int index;
    FragmentTransaction fragmentTransaction;
    ArrayList<Picture> imageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image_view);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentContainer);
        Intent mIntent = getIntent();
        index = mIntent.getIntExtra(INDEX, -1);
        imageArrayList = mIntent.getParcelableArrayListExtra(LIST_NAME);
        fragmentTransaction = getFragmentManager().beginTransaction();
        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ImageFragment.OBJECT, imageArrayList.get(index));
        imageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer, imageFragment);
        fragmentTransaction.commit();
        frameLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                index = index + 1;
                change(index, true);
            }

            @Override
            public void onSwipeRight() {
                index = index - 1;
                change(index, false);
            }

            private void change(int index, boolean b) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                if (b) {
                    fragmentTransaction.setCustomAnimations(
                            R.anim.slide_in_left, R.anim.slide_out_left);
                } else {
                    fragmentTransaction.setCustomAnimations(
                            R.anim.slide_in_right, R.anim.slide_out_right);
                }
                ImageFragment imageFragment = new ImageFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ImageFragment.OBJECT, imageArrayList.get(index));
                imageFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, imageFragment);
                fragmentTransaction.commit();
            }
        });
    }


}
