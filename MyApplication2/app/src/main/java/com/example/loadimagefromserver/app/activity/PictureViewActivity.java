package com.example.loadimagefromserver.app.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.loadimagefromserver.app.NextPage;
import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.fragment.ImageFragment;
import com.example.loadimagefromserver.app.http.listener.OnPicturesLoadListener;
import com.example.loadimagefromserver.app.listener.OnSwipeTouchListener;
import com.example.loadimagefromserver.app.model.Picture;

import java.util.Collections;
import java.util.List;

public class PictureViewActivity extends Activity implements OnPicturesLoadListener {
    public static final String INDEX = "index";
    public static final String LIST_NAME = "list name";
    public static final String PAGE_COUNTER="counter";
    int counter;
    int index;

    FragmentTransaction fragmentTransaction;
    List<Picture> imageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image_view);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentContainer);
        Intent mIntent = getIntent();
        index = mIntent.getIntExtra(INDEX, -1);
        counter = mIntent.getIntExtra(PAGE_COUNTER,-1);
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
                if(index==imageArrayList.size()-2){
                    counter++;
                    new NextPage(counter,PictureViewActivity.this).execute();

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
                Toast.makeText(PictureViewActivity.this,""+index,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPicturesLoad(Picture[] pictures) {
        Collections.addAll(imageArrayList, pictures);
    }

/*    class NextPageInImageViewActivity extends NextPage{

        public NextPageInImageViewActivity(int counter) {
            super(counter);
        }

        @Override
        protected void onPostExecute(Picture[] pictures) {
            imageArrayList.addAll(Arrays.asList(pictures));
        }
    }*/

}
