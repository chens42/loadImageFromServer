package com.example.loadimagefromserver.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.loadimagefromserver.app.holder.ImageHolder;
import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.model.Picture;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private static String URl_Fraction = "http://192.168.1.5:8080/assets?page=";
    private final String IMAGE_URL = "http://192.168.1.5:8080/images/%s";
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

        new getContent().execute();
        GridView gridView = (GridView) findViewById(R.id.imageContainer);
        imageAdapter = new GridViewAdapter(pictureList);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ImageViewActivity.class);
                intent.putExtra(ImageViewActivity.INDEX,position);
                intent.putParcelableArrayListExtra(ImageViewActivity.LIST_NAME, (ArrayList<? extends android.os.Parcelable>) pictureList);
                startActivity(intent);
            }
        });
    }

    public class getContent extends AsyncTask<Void, Void, Picture[]> {

        @Override
        protected Picture[] doInBackground(Void... params) {
            String URl = URl_Fraction + counter;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URl);
            HttpResponse httpResponse;
            HttpEntity httpEntity;
            Picture[] pictures = new Picture[0];
            String jsonStr;
            try {
                httpResponse = httpClient.execute(httpGet);
                httpEntity = httpResponse.getEntity();
                jsonStr = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (jsonStr != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                try {
                    pictures = objectMapper.readValue(jsonStr, Picture[].class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return pictures;
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

    class GridViewAdapter extends ArrayAdapter {
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
                    new getContent().execute();
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
            ImageLoader.getInstance().displayImage(String.format(IMAGE_URL, pictures.get(position).getImage()), holder.getImageView());
            holder.getTextView().setText(pictures.get(position).getCountry());
            return cell;
        }

        public void addPictures(Picture[] pictures) {
            this.pictures.addAll(Arrays.asList(pictures));
            notifyDataSetChanged();
        }
    }
}
