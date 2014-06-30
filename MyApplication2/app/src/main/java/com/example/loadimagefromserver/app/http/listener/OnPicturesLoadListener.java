package com.example.loadimagefromserver.app.http.listener;

import com.example.loadimagefromserver.app.model.Picture;

public interface OnPicturesLoadListener {
    void onPicturesLoad(Picture[] pictures);
}
