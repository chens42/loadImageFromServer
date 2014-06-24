package com.example.loadimagefromserver.app.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.loadimagefromserver.app.R;
import com.example.loadimagefromserver.app.model.Picture;
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
import java.util.List;


public class MainActivity extends Activity {
    private static String url = "http://192.168.1.5:8080/assets?page=2";
    private final String IMAGE_URL = "http://192.168.1.5:8080/images/";
    String jsonStr;
    GridView gridView;
    GridViewAdapter imageAdapter;
    List<Picture> pictureList = new ArrayList<Picture>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new getContent().execute();
        gridView = (GridView) findViewById(R.id.imageContainer);
        imageAdapter = new GridViewAdapter(pictureList);
        gridView.setAdapter(imageAdapter);

    }

    public class getContent extends AsyncTask<Void, Void, Picture[]> {

        @Override
        protected Picture[] doInBackground(Void... params) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse;
            HttpEntity httpEntity;
            jsonStr = null;
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
                    return objectMapper.readValue(jsonStr, Picture[].class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Picture[] pictures) {
            for (Picture picture : pictures) {
                pictureList.add(picture);
            }
            imageAdapter.notifyDataSetChanged();
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
        ImageLoader imageLoader;

        public GridViewAdapter(List<Picture> pictures) {
            super(MainActivity.this, R.layout.image_cell, pictures);
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(MainActivity.this));

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View cell = convertView;
            if (cell == null) {
                LayoutInflater inflater = getLayoutInflater();
                cell = inflater.inflate(R.layout.image_cell, parent, false);
            }
            Picture picture = pictureList.get(position);
            imageLoader.displayImage(IMAGE_URL + (picture.getImage()), (android.widget.ImageView) cell.findViewById(R.id.imageField));
            TextView textView = (TextView) cell.findViewById(R.id.country);
            textView.setText(picture.getCountry());
            return cell;
        }
    }
}
