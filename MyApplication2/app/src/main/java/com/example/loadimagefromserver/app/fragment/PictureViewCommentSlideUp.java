package com.example.loadimagefromserver.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loadimagefromserver.app.R;
import com.fortysevendeg.swipelistview.SwipeListView;

import java.util.ArrayList;
import java.util.List;

public class PictureViewCommentSlideUp extends android.app.Fragment {
    private SwipeListView swipeListView;
    private CommentAdapter commentAdapter;
    private List<CommentSlideUp> list = new ArrayList<CommentSlideUp>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.picture_view_comments, container, false);
        CommentSlideUp commentSlideUp = new CommentSlideUp(R.drawable.ken, "wang", "hello");
        swipeListView = (SwipeListView) root.findViewById(R.id.pictureViewComment);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        list.add(commentSlideUp);
        commentAdapter = new CommentAdapter(list);
        swipeListView.setAdapter(commentAdapter);

        return root;
    }

    class CommentAdapter extends ArrayAdapter {
        private List<CommentSlideUp> list;

        public CommentAdapter(List<CommentSlideUp> commentSlideUpList) {
            super(getActivity(), R.layout.comment_row, commentSlideUpList);
            list = commentSlideUpList;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.comment_row, parent, false);
            }
            ImageView imageView = (ImageView) row.findViewById(R.id.iconComment);
            imageView.setImageResource(list.get(position).getImage());
            TextView name = (TextView) row.findViewById(R.id.nameField);
            name.setText(list.get(position).getName());
            TextView comment = (TextView) row.findViewById(R.id.commentField);
            comment.setText(list.get(position).getComment());
            return row;
        }
    }
}