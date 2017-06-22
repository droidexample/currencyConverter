package com.at.currencysell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.at.currencysell.R;
import com.at.currencysell.model.HomeListModel;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    public List<HomeListModel> listItems;



    public ChatAdapter(Activity activity, List<HomeListModel> listItems) {
        this.activity = activity;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int location) {
        return listItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.chat_list_item, null);


        final HomeListModel listModel = listItems.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.text_name);

        title.setText(listModel.getName());

        return convertView;
    }

}
