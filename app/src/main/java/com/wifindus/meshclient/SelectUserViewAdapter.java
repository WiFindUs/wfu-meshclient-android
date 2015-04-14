package com.wifindus.meshclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectUserViewAdapter extends RecyclerView.Adapter<SelectUserViewAdapter.ViewHolder> {
    private SelectUserData[] itemsData;

    public SelectUserViewAdapter(SelectUserData[] itemsData) {
        this.itemsData = itemsData;
    }

    @Override
    public SelectUserViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_user_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        viewHolder.txtViewTitle.setText(itemsData[position].getName());
        viewHolder.imgViewIcon.setImageResource(itemsData[position].getProfilePicUrl());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.user_name);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.profile_picture);
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}