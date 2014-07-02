package com.example.loadimagefromserver.app.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.loadimagefromserver.app.NextPage;
import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.fragment.ImageFragment;
import com.example.loadimagefromserver.app.fragment.PictureViewCommentSlideUp;
import com.example.loadimagefromserver.app.http.listener.OnPicturesLoadListener;
import com.example.loadimagefromserver.app.listener.OnSwipeTouchListener;
import com.example.loadimagefromserver.app.model.Picture;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Collections;
import java.util.List;

public class PictureViewActivity extends Activity implements OnPicturesLoadListener {
    public static final String INDEX = "index";
    public static final String LIST_NAME = "list name";
    public static final String PAGE_COUNTER = "counter";
    int counter;
    int index;

    FragmentTransaction fragmentTransaction;
    List<Picture> imageArrayList;
    private SlidingUpPanelLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image_view);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setSlidingEnabled(false);


        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentContainer);
        Intent mIntent = getIntent();
        index = mIntent.getIntExtra(INDEX, -1);
        counter = mIntent.getIntExtra(PAGE_COUNTER, -1);
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
                if (index == imageArrayList.size() - 2) {
                    counter++;
                    new NextPage(counter, PictureViewActivity.this).execute();

                }
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
                Toast.makeText(PictureViewActivity.this, "" + index, Toast.LENGTH_SHORT).show();
            }
        });
        final Button expandButton = (Button) findViewById(R.id.comment);

        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLayout.isPanelExpanded()){
                    mLayout.collapsePanel();
                    expandButton.setText("comment");
                }else{
                    FrameLayout slideUpPart = (FrameLayout) findViewById(R.id.slideUpShow);
                    expandButton.setText("collapse");
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    PictureViewCommentSlideUp pictureViewCommentSlideUp = new PictureViewCommentSlideUp();
                    fragmentTransaction.replace(R.id.slideUpShow, pictureViewCommentSlideUp);
                    fragmentTransaction.commit();
                    mLayout.setSlidingEnabled(true);
                    mLayout.expandPanel(1.0f);
                }
            }
        });
    }

    @Override
    public void onPicturesLoad(Picture[] pictures) {
        Collections.addAll(imageArrayList, pictures);
    }


}
