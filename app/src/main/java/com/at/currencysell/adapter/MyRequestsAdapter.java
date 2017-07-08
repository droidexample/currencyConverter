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
import com.at.currencysell.model.MyReqestActiveMoel;

import java.util.Vector;

public class MyRequestsAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<MyReqestActiveMoel> all_Line_itemses = null;
    private LayoutInflater l_Inflater;
//    private AQuery mAQuery;


    public MyRequestsAdapter(Context context, Vector<MyReqestActiveMoel> AllLineitemses) {
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
        final MyReqestActiveMoel lineitems = all_Line_itemses.get(position);

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.my_request_list_item, null);
            holder = new ViewHolder();
            holder.tv_usd = (TextView) convertView.findViewById(R.id.tv_usd);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_usd.setText(lineitems.getCurrency_name());


//        mAQuery.id(holder.im_eventImage).image(lineitems.getUser_img(), true, true, 0, R.drawable.noimage);



        return convertView;

    }

    class ViewHolder {
        private TextView tv_usd;
        private ImageView im_eventImage;

    }


}
