package com.example.loadimagefromserver.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.loadimagefromserver.app.NextPage;
import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.common.HttpConstant;
import com.example.loadimagefromserver.app.holder.ImageHolder;
import com.example.loadimagefromserver.app.http.listener.OnPicturesLoadListener;
import com.example.loadimagefromserver.app.model.Picture;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity implements OnPicturesLoadListener {

    private GridViewAdapter imageAdapter;
    private List<Picture> pictureList = new ArrayList<Picture>();
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new FIFOLimitedMemoryCache(6 * 1024 * 1024))
                .memoryCacheSize(6 * 1024 * 1024)
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);

        new getContent(counter).execute();
        GridView gridView = (GridView) findViewById(R.id.imageContainer);
        imageAdapter = new GridViewAdapter(pictureList);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,PictureViewActivity.class);
                intent.putExtra(PictureViewActivity.INDEX,position);
                intent.putExtra(PictureViewActivity.PAGE_COUNTER,counter);
                intent.putParcelableArrayListExtra(PictureViewActivity.LIST_NAME, (ArrayList<? extends android.os.Parcelable>) pictureList);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPicturesLoad(Picture[] pictures) {
        Collections.addAll(pictureList, pictures);
        imageAdapter.notifyDataSetChanged();
    }

    public class getContent extends NextPage {
        public getContent(int counter) {
            super(counter,MainActivity.this);
        }
        @Override
        protected void onPostExecute(Picture[] pictures) {
            imageAdapter.addPictures(pictures);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public class GridViewAdapter extends ArrayAdapter {
        private final List<Picture> pictures;

        public GridViewAdapter(List<Picture> pictures) {
            super(MainActivity.this, R.layout.image_cell, pictures);
            this.pictures = pictures;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == pictureList.size() - 1) {
                counter++;
                if (counter < 11) {
                    new getContent(counter).execute();
                }
            }
            View cell = convertView;
            ImageHolder holder;
            if (cell == null) {
                LayoutInflater inflater = getLayoutInflater();
                cell = inflater.inflate(R.layout.image_cell, parent, false);
                holder = new ImageHolder(cell);
                cell.setTag(holder);
            } else {
                holder = (ImageHolder) cell.getTag();
            }
            ImageLoader.getInstance().displayImage(String.format(HttpConstant.IMAGE_URL, pictures.get(position).getImage()), holder.getImageView());
            holder.getTextView().setText(pictures.get(position).getCountry());
            return cell;
        }

        public void addPictures(Picture[] pictures) {
            this.pictures.addAll(Arrays.asList(pictures));
            notifyDataSetChanged();
        }
    }
}
