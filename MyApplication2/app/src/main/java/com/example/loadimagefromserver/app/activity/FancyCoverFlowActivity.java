package com.example.loadimagefromserver.app.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.loadimagefromserver.app.FancyCoverFlow.FancyCoverFlow;
import com.example.loadimagefromserver.app.FancyCoverFlow.FancyCoverFlowAdapter;
import com.example.loadimagefromserver.app.NextPage;
import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.common.HttpConstant;
import com.example.loadimagefromserver.app.http.listener.OnPicturesLoadListener;
import com.example.loadimagefromserver.app.model.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FancyCoverFlowActivity extends Activity implements OnPicturesLoadListener {
    private List<Picture> list= new ArrayList<Picture>();
    private ViewGroupExampleAdapter viewGroupExampleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fancy_cover_flow);
        new NextPage(1,this).execute();
        FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        viewGroupExampleAdapter=new ViewGroupExampleAdapter(list);
        fancyCoverFlow.setAdapter(viewGroupExampleAdapter);
    }

    @Override
    public void onPicturesLoad(Picture[] pictures) {
        Collections.addAll(list, pictures);
        viewGroupExampleAdapter.notifyDataSetChanged();
    }

    private static class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

        // =============================================================================
        // Private members
        // =============================================================================

        private List<Picture> list;
        public ViewGroupExampleAdapter(List<Picture> list) {
            this.list =list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Picture getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
            CustomViewGroup customViewGroup = null;

            if (reuseableView != null) {
                customViewGroup = (CustomViewGroup) reuseableView;
            } else {
                customViewGroup = new CustomViewGroup(viewGroup.getContext());
                customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 600));
            }
           ImageLoader.getInstance().displayImage(String.format(HttpConstant.IMAGE_URL, list.get(i).getImage()), customViewGroup.getImageView());


            return customViewGroup;
        }
    }

    private static class CustomViewGroup extends LinearLayout {


        private ImageView imageView;

        private CustomViewGroup(Context context) {
            super(context);

            this.setOrientation(VERTICAL);
            this.setWeightSum(5);

            this.imageView = new ImageView(context);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            this.imageView.setLayoutParams(layoutParams);

            this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            this.imageView.setAdjustViewBounds(true);


            this.addView(this.imageView);
        }

        private ImageView getImageView() {
            return imageView;
        }
    }
}
