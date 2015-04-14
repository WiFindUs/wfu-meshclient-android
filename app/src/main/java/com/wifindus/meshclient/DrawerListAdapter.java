package com.wifindus.meshclient;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.LinkedHashMap;
import java.util.Map;

public class DrawerListAdapter extends BaseAdapter
{
    public final Map<String, Adapter> sections = new LinkedHashMap<>();
    public final ArrayAdapter<String> headers;
    public final static int TYPE_SECTION_HEADER = 0;

    public DrawerListAdapter(Context context)
    {
        headers = new ArrayAdapter<>(context, R.layout.list_header);
    }

    public void addSection(String section, Adapter adapter)
    {
        this.headers.add(section);
        this.sections.put(section, adapter);
    }

    //==============================================================================================
    // Get Item
    //==============================================================================================
    public Object getItem(int position)
    {
        for (Object section : this.sections.keySet())
        {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return section;
            if (position < size) return adapter.getItem(position - 1);

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }
    //==============================================================================================
    // END :: Get Item
    //==============================================================================================


    //==============================================================================================
    // Get number of items in list including headers
    //==============================================================================================
    public int getCount()
    {
        int total = 0;
        for (Adapter adapter : this.sections.values())
            total += adapter.getCount() + 1;
        return total;
    }
    //==============================================================================================
    // END :: Get number of items in list including headers
    //==============================================================================================


    //==============================================================================================
    // Get number of items in list excluding headers (Count All Headers as 1)
    //==============================================================================================
    @Override
    public int getViewTypeCount()
    {
        int total = 1;
        for (Adapter adapter : this.sections.values())
            total += adapter.getViewTypeCount();
        return total;
    }
    //==============================================================================================
    // END :: Get number of items in list excluding headers (Count All Headers as 1)
    //==============================================================================================


    //==============================================================================================
    // Get type of an item
    //==============================================================================================
    @Override
    public int getItemViewType(int position)
    {
        int type = 1;
        for (Object section : this.sections.keySet())
        {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            //check for position in current section
            if (position == 0) return TYPE_SECTION_HEADER;
            if (position < size) return type + adapter.getItemViewType(position - 1);

            //go to next section if not found
            position -= size;
            type += adapter.getViewTypeCount();
        }
        return -1;
    }
    //==============================================================================================
    // END :: Get type of an item
    //==============================================================================================



    //==============================================================================================
    // Only items (Not Headers) are enabled
    //==============================================================================================
    @Override
    public boolean isEnabled(int position)
    {
        return (getItemViewType(position) != TYPE_SECTION_HEADER);
    }
    //==============================================================================================
    // END :: Only items (Not Headers) are enabled
    //==============================================================================================


    //==============================================================================================
    // Get View
    //==============================================================================================
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int sectionNum = 0;
        for (Object section : this.sections.keySet())
        {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            //check for position in current section
            if (position == 0) return headers.getView(sectionNum, convertView, parent);
            if (position < size) return adapter.getView(position - 1, convertView, parent);

            //go to next section if not found
            position -= size;
            sectionNum++;
        }
        return null;
    }
    //==============================================================================================
    // END :: Get View
    //==============================================================================================


    //==============================================================================================
    // Get Item ID
    //==============================================================================================
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    //==============================================================================================
    // END :: Get Item ID
    //==============================================================================================

}