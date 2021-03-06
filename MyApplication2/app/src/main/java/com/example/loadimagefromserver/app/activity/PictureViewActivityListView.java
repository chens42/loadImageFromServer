package com.example.loadimagefromserver.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.loadimagefromserver.app.MyAdapter;
import com.example.loadimagefromserver.app.NextPage;
import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.common.HttpConstant;
import com.example.loadimagefromserver.app.holder.ListViewImageHolder;
import com.example.loadimagefromserver.app.http.listener.OnPicturesLoadListener;
import com.example.loadimagefromserver.app.model.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class PictureViewActivityListView extends Activity implements OnPicturesLoadListener {

    private List<Picture> list=new ArrayList<Picture>();
    private MyAdapter listViewAdapter;
    private StickyListHeadersListView stickyListHeadersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_view_activity_list_view);
        new NextPage(1,this).execute();
        listViewAdapter=new MyAdapter(getApplicationContext(),list);
        stickyListHeadersListView = (StickyListHeadersListView) findViewById(R.id.pictureViewActivityListView);
        stickyListHeadersListView.setAdapter(listViewAdapter);
        stickyListHeadersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PictureViewActivityListView.this,FancyCoverFlowActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPicturesLoad(Picture[] pictures) {
        Collections.addAll(list, pictures);
        listViewAdapter.notifyDataSetChanged();
    }

    class ListViewAdapter extends ArrayAdapter implements StickyListHeadersAdapter {
        private List<Picture> pictures;
        private LayoutInflater inflater;
        public ListViewAdapter(List<Picture> pictures) {
            super(PictureViewActivityListView.this, R.layout.picture_view_activity_list_view_row_element, pictures);
            inflater = LayoutInflater.from(PictureViewActivityListView.this);
            this.pictures = pictures;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ListViewImageHolder holder;
            if(row==null){
                row = inflater.inflate(R.layout.picture_view_activity_list_view_row_element, parent, false);
                holder = new ListViewImageHolder(row);
                row.setTag(holder);
            }else{
                holder = (ListViewImageHolder) row.getTag();
            }
            ImageLoader.getInstance().displayImage(String.format(HttpConstant.IMAGE_URL, pictures.get(position).getImage()), holder.getImageView());

            return row;
        }

        @Override
        public View getHeaderView(int i, View view, ViewGroup viewGroup) {
            TextView text = null;
            if (view == null) {
                view = inflater.inflate(R.layout.listview_row_header, viewGroup, false);
                text = (TextView) view.findViewById(R.id.countryText);
            }
            text.setText(pictures.get(i).getCountry());
            return view;
        }

        @Override
        public long getHeaderId(int i) {

            return  pictures.get(i).getCountry().subSequence(0, 1).charAt(0);
        }
    }

}
