package com.uyh.lab10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 1031002 on 11/9/2016.
 */
public class ContactAdapter extends BaseAdapter {

    private List<String> names, phones;
    private Context context;
    private LayoutInflater inflater;
    private EditText inputText;

    public ContactAdapter(MainActivity mainActivity, List<String> names, List<String> phones, EditText inputText) {
        this.names = names;
        this.phones = phones;
        this.context = mainActivity;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.inputText = inputText;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View rowView = inflater.inflate(R.layout.contact_list, null);
        TextView nameTv = (TextView) rowView.findViewById(R.id.nameOutput);
        final TextView phoneTv = (TextView) rowView.findViewById(R.id.phoneOutput);

        nameTv.setText(names.get(position));
        phoneTv.setText(phones.get(position));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText.setText(phones.get(position));
            }
        });

        return rowView;
    }
}
