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
import com.at.currencysell.model.HomeListModel;
import com.at.currencysell.model.ProfileReviewModel;

import java.util.Vector;

public class ProfileReviewAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<ProfileReviewModel> all_Line_itemses = null;
    private LayoutInflater l_Inflater;
    private AQuery mAQuery;


    public ProfileReviewAdapter(Context context, Vector<ProfileReviewModel> AllLineitemses) {
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
        final ProfileReviewModel lineitems = all_Line_itemses.get(position);

        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.row_item_profile, null);
            holder = new ViewHolder();
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.im_eventImage = (ImageView) convertView.findViewById(R.id.review_user_picture);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_user_name.setText(lineitems.getReview_userName());
        holder.tv_comment.setText(lineitems.getComment());


        mAQuery.id(holder.im_eventImage).image(lineitems.getReview_user_picture(), true, true, 0, R.drawable.profile);



        return convertView;

    }

    class ViewHolder {
        private TextView tv_user_name;
        private TextView tv_comment;
        private ImageView im_eventImage;

    }


}
