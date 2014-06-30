package com.example.loadimagefromserver.app;

import android.content.Context;
import android.os.AsyncTask;

import com.example.loadimagefromserver.app.http.listener.OnPicturesLoadListener;
import com.example.loadimagefromserver.app.model.Picture;

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

public class NextPage extends AsyncTask<Void, Void, Picture[]> {
    private static String URl_Fraction = "http://192.168.1.5:8080/assets?page=";
    private int counter;
    private OnPicturesLoadListener onPicturesLoadListener;

    private List<Picture> pictureList = new ArrayList<Picture>();

    public NextPage(int counter,Context context) {
        this.counter = counter;
        onPicturesLoadListener = (OnPicturesLoadListener) context;
    }
    public List<Picture> getPictureList() {
        return pictureList;
    }

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
        onPicturesLoadListener.onPicturesLoad(pictures);
    }
}