package com.at.currencysell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.at.currencysell.R;
import com.at.currencysell.holder.AllCurrencyList;
import com.at.currencysell.model.Currency_Names;

import java.util.Vector;

public class CurrencyNameAdapter extends ArrayAdapter<Currency_Names> {

	private Vector<Currency_Names> originalList;
	private Vector<Currency_Names> chatList;
	private CityFilter filter;
	int resId;
	public Activity activity;


	public CurrencyNameAdapter(Activity a, int textViewResourceId, Vector<Currency_Names> cityLists) {
		super(a, textViewResourceId, cityLists);
		this.chatList = new Vector<Currency_Names>();
		this.originalList = new Vector<Currency_Names>();
		this.chatList.addAll(cityLists);
		this.originalList.addAll(cityLists);
		this.activity	 =	a;

	}


	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new CityFilter();
		}
		return filter;
	}



	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		final Currency_Names listModel = AllCurrencyList.getDolorList(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_item_currency_name, null);
			holder = new ViewHolder();

			holder.short_title= (TextView)convertView.findViewById(R.id.title);
			holder.long_title= (TextView)convertView.findViewById(R.id.description);
			holder.imageView= (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.short_title.setText(listModel.getShort_name());
		holder.long_title.setText(listModel.getAbrivation());
		resId = activity.getResources().getIdentifier(listModel.getShort_name().toLowerCase(), "drawable",activity.getPackageName());


		if(listModel.getShort_name().contains("TRY"))
		{
			resId = activity.getResources().getIdentifier("tnd", "drawable",activity.getPackageName());
			holder.imageView.setImageResource(resId);
		}else if(resId==0)
		{
			resId = activity.getResources().getIdentifier("xdr", "drawable",activity.getPackageName());

			holder.imageView.setImageResource(resId);
		}
		holder.imageView.setImageResource(resId);

		return convertView;

	}
	static class ViewHolder
	{

		public TextView short_title;
		public TextView long_title;
		public ImageView imageView;

		
	}

	private class CityFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {

			constraint = constraint.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0) {
				Vector<Currency_Names> filteredItems = new Vector<Currency_Names>();

				for (int i = 0, l = originalList.size(); i < l; i++) {
					Currency_Names country = originalList.get(i);
					if (country.getShort_name().toString().toLowerCase().contains(constraint)) {

						filteredItems.add(country);
					}
				}
				result.count = filteredItems.size();
				result.values = filteredItems;
			} else {
				synchronized (this) {
					result.values = originalList;
					result.count = originalList.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
									  FilterResults results) {
			chatList = (Vector<Currency_Names>) results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0, l = chatList.size(); i < l; i++)

				add(chatList.get(i));
			notifyDataSetInvalidated();
		}
	}
	
}
