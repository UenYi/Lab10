package com.uyh.lab10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.zip.Inflater;

/**
 * Created by 1031002 on 11/9/2016.
 */
public class ContactAdapter extends BaseAdapter {

    private String[] names, phones;
    private Context context;
    private LayoutInflater inflater;

    public ContactAdapter(MainActivity mainActivity,String[] names, String[] phones){
        this.names=names;
        this.phones=phones;
        this.context=mainActivity;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return names[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = inflater.inflate();

        return null;
    }
}
