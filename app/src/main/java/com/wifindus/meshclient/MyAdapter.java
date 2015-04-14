package com.wifindus.meshclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


// We can create custom adapters
class MyAdapter extends ArrayAdapter<String> {

    public MyAdapter(Context context, String[] values)
    {

        super(context, R.layout.row_layout2, values);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_layout2, parent, false);

        String item = getItem(position);
        TextView theTextView = (TextView) theView.findViewById(R.id.textView1);
        theTextView.setText(item);

        ImageView theImageView = (ImageView) theView.findViewById(R.id.image_view1);

        //change the image view
        theImageView.setImageResource(R.mipmap.ic_action);

        return theView;
    }
}