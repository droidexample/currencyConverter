package com.at.currencysell.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.at.currencysell.R;
import com.at.currencysell.holder.HomeList;
import com.at.currencysell.model.HomeListModel;


import java.util.Vector;

public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<HomeListModel> all_Line_itemses = null;
    private LayoutInflater l_Inflater;
    private AQuery mAQuery;
    private int resIdwant;
    private int resIdpay;


    public HomeAdapter(Context context, Vector<HomeListModel> AllLineitemses) {
        this.mContext = context;
        this.all_Line_itemses = AllLineitemses;
        l_Inflater = LayoutInflater.from(context);
        mAQuery = new AQuery(mContext);
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
            holder.tv_end_date = (TextView) convertView.findViewById(R.id.tv_end_date);
            holder.tv_location = (TextView) convertView.findViewById(R.id.tv_location);
            holder.tv_want_currency = (TextView) convertView.findViewById(R.id.tv_want_currency);
            holder.tv_want_amount = (TextView) convertView.findViewById(R.id.tv_want_amount);
            holder.tv_pay_currency = (TextView) convertView.findViewById(R.id.tv_pay_currency);
            holder.tv_pay_amount = (TextView) convertView.findViewById(R.id.tv_pay_amount);
            holder.user_profile_photo = (ImageView) convertView.findViewById(R.id.user_profile_photo);
            holder.img_want_currency = (ImageView) convertView.findViewById(R.id.img_want_currency);
            holder.img_pay_currency = (ImageView) convertView.findViewById(R.id.img_pay_currency);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_user_name.setText(lineitems.getName());
        holder.tv_end_date.setText(lineitems.getEnd_date());
        holder.tv_location.setText(lineitems.getLocation());
        holder.tv_want_currency.setText(lineitems.getWant_currency());
        holder.tv_want_amount.setText(lineitems.getWant_amount());
        holder.tv_pay_currency.setText(lineitems.getPay_currency());
        holder.tv_pay_amount.setText(lineitems.getPay_amount());
        //mAQuery.id(holder.user_profile_photo).image(HomeList.getmAllPeopleHave(position).getImage_url(), true, true);
        resIdpay = mContext.getResources().getIdentifier(lineitems.getPay_currency().toLowerCase(), "drawable",mContext.getPackageName());
        holder.img_pay_currency.setImageResource(resIdpay);
        resIdwant = mContext.getResources().getIdentifier(lineitems.getWant_currency().toLowerCase(), "drawable",mContext.getPackageName());
        holder.img_want_currency.setImageResource(resIdwant);
        return convertView;

    }

    class ViewHolder {
        private TextView tv_user_name;
        private TextView tv_end_date;
        private TextView tv_location;
        private TextView tv_want_currency;
        private TextView tv_want_amount;
        private TextView tv_pay_currency;
        private TextView tv_pay_amount;
        private ImageView user_profile_photo;
        private ImageView img_want_currency;
        private ImageView img_pay_currency;

    }


}
