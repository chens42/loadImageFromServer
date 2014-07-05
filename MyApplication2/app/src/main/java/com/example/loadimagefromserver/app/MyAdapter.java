package com.example.loadimagefromserver.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loadimagefromserver.app.common.HttpConstant;
import com.example.loadimagefromserver.app.model.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private  List<Picture> pictures;
    private LayoutInflater inflater;
    private Context context;

    public MyAdapter(Context context,List<Picture> pictures) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.pictures=pictures;
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public Object getItem(int position) {
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.picture_view_activity_list_view_row_element, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.pictureViewActivityListViewImageRow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(String.format(HttpConstant.IMAGE_URL, pictures.get(position).getImage()), holder.image);
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        String headerText = pictures.get(position).getCountry();
        holder.text.setText(headerText);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return position;
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        ImageView image;
    }

}