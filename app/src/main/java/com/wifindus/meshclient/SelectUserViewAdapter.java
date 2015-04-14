package com.wifindus.meshclient;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    // Create new views (invoked by the layout manager)
    @Override
    public SelectUserViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_user_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewTitle.setText(itemsData[position].getName());
        viewHolder.imgViewIcon.setImageResource(itemsData[position].getProfilePicUrl());



    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Circular Profile Picture
        /*ImageView imageViewRound;
        imageViewRound = (ImageView)findViewById(R.id.profile_picture);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.mitchell_templeton);
        imageViewRound.setImageBitmap(icon);*/

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.user_name);

            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.profile_picture);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}