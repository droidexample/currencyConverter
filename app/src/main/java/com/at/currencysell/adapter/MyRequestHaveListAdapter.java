package com.at.currencysell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.at.currencysell.R;
import com.at.currencysell.model.MyReqestActiveMoel;

import java.util.List;

public class MyRequestHaveListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    public List<MyReqestActiveMoel> listItems;


    public MyRequestHaveListAdapter(Activity activity, List<MyReqestActiveMoel> listItems) {
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
            convertView = inflater.inflate(R.layout.my_request_have_list_item, null);


        final MyReqestActiveMoel listModel = listItems.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.currency_name);
//        TextView full_name = (TextView) convertView.findViewById(R.id.currency_full_name);
        ImageView image = (ImageView) convertView.findViewById(R.id.currency_image);
//        ImageView imagechecke = (ImageView) convertView.findViewById(R.id.image_checked);
//
//        boolean click = PersistentUser.isClicked(activity);
//        if (click) {
//            imagechecke.setVisibility(View.VISIBLE);
//        } else {
//            imagechecke.setVisibility(View.GONE);
//        }

        title.setText(listModel.getCurrency_name());
//        full_name.setText(listModel.getCurrency_full_name());
        image.setImageResource(listModel.getImage());

        return convertView;
    }

}
