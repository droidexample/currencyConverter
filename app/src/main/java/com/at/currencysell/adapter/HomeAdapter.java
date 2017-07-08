package com.at.currencysell.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.at.currencysell.R;
import com.at.currencysell.model.HomeListModel;


import java.util.Vector;

public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<HomeListModel> all_Line_itemses = null;
    private LayoutInflater l_Inflater;
//    private AQuery mAQuery;


    public HomeAdapter(Context context, Vector<HomeListModel> AllLineitemses) {
        this.mContext = context;
        this.all_Line_itemses = AllLineitemses;
        l_Inflater = LayoutInflater.from(context);
//        mAQuery = new AQuery(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return all_Line_itemses.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return all_Line_itemses.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final HomeListModel lineitems = all_Line_itemses.get(position);

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.row_item_home, null);
            holder = new ViewHolder();
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_user_name.setText(lineitems.getName());


//        mAQuery.id(holder.im_eventImage).image(lineitems.getUser_img(), true, true, 0, R.drawable.noimage);



        return convertView;

    }

    class ViewHolder {
        private TextView tv_user_name;
        private ImageView im_eventImage;

    }


}
