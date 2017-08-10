package com.at.currencysell.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.at.currencysell.R;
import com.at.currencysell.model.MyReqestActiveMoel;

import java.util.Vector;

public class MyRequestsAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<MyReqestActiveMoel> all_Line_itemses = null;
    private LayoutInflater l_Inflater;
    private int resIdhhave;
    private int resIdneed;
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
            holder.tv_amount_have = (TextView) convertView.findViewById(R.id.tv_amount_have);
            holder.tv_end_date = (TextView) convertView.findViewById(R.id.tv_end_date);
            holder.tv_currency_need = (TextView) convertView.findViewById(R.id.tv_currency_need);
            holder.tv_amount_need = (TextView) convertView.findViewById(R.id.tv_amount_need);
            holder.img_usd = (ImageView) convertView.findViewById(R.id.img_usd);
            holder.img_currency_need = (ImageView) convertView.findViewById(R.id.img_currency_need);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_usd.setText(lineitems.getCurrency_have());
        holder.tv_amount_have.setText(lineitems.getAmount_have());
        holder.tv_end_date.setText(lineitems.getEnd_date());
        holder.tv_currency_need.setText(lineitems.getCurrency_need());
        holder.tv_amount_need.setText(lineitems.getAmount_need());
        resIdhhave = mContext.getResources().getIdentifier(lineitems.getCurrency_have().toLowerCase(), "drawable",mContext.getPackageName());
        holder.img_usd.setImageResource(resIdhhave);
        resIdneed = mContext.getResources().getIdentifier(lineitems.getCurrency_need().toLowerCase(), "drawable",mContext.getPackageName());
        holder.img_currency_need.setImageResource(resIdneed);

//        mAQuery.id(holder.im_eventImage).image(lineitems.getUser_img(), true, true, 0, R.drawable.noimage);



        return convertView;

    }

    class ViewHolder {
        private TextView tv_usd;
        private TextView tv_amount_have;
        private TextView tv_end_date;
        private TextView tv_currency_need;
        private TextView tv_amount_need;
        private ImageView im_eventImage;
        private ImageView img_usd;
        private ImageView img_currency_need;

    }


}
