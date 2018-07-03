package com.veephealthoutloud.healthoutloud;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.veephealthoutloud.healthoutloud.Interfaces.IPost;

import java.text.DateFormat;
import java.util.Date;

import java.util.ArrayList;

/**
 * Adapter used to render Posts in the newsfeed.
 */

public class PostAdapter extends BaseAdapter {

    static class ViewHolder {
        TextView messageTextView;
        TextView dateTextView;
        TextView feelingsTextView;
        Button openMenuButton;
    }

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<IPost> mDataSource;
    private int mMenuLayout;

    public PostAdapter(Context context, ArrayList<IPost> posts, int menuLayout) {

        mContext = context;
        mDataSource = posts;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuLayout = menuLayout;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null){
            view = mInflater.inflate(R.layout.list_item_post, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.dateTextView = view.findViewById(R.id.post_date);
            viewHolder.messageTextView = view.findViewById(R.id.post_message);
            viewHolder.feelingsTextView = view.findViewById(R.id.post_feelings);
            viewHolder.openMenuButton = view.findViewById(R.id.open_post_menu_button);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        IPost post = (IPost) getItem(i);

        viewHolder.dateTextView.setText(DateFormat.getDateTimeInstance().format(post.GetDate()));
        viewHolder.messageTextView.setText(post.GetMessage());
        viewHolder.feelingsTextView.setText(GetFeelingsString(post.GetFeelings()));
        viewHolder.openMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(mMenuLayout, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        view.setBackgroundColor(Color.WHITE);

        return view;
    }

    private String GetFeelingsString(ArrayList<String> feelings){
        StringBuilder result = new StringBuilder();
        for (String feeling : feelings){
            result.append(String.format("#%s ", feeling));
        }
        return result.toString();
    }

    public void updateResults(ArrayList<IPost> newPosts){
        mDataSource = newPosts;
        notifyDataSetChanged();
    }
}
