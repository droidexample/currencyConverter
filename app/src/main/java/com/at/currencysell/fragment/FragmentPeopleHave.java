package com.at.currencysell.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.at.currencysell.R;
import com.at.currencysell.adapter.HomeAdapter;
import com.at.currencysell.holder.HomeList;
import com.at.currencysell.holder.PeopleHaveList;
import com.at.currencysell.model.HomeListModel;
import com.at.currencysell.utils.AlertMessage;
import com.at.currencysell.utils.BaseUrl;
import com.at.currencysell.utils.BusyDialog;
import com.at.currencysell.utils.NetInfo;
import com.at.currencysell.utils.PersistentUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 05/07/2017.
 */

public class FragmentPeopleHave extends Fragment {


    private View view;
    private Context mContext;
    private HomeAdapter adapter;
    private ListView listview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fagment_people_have, container, false);
        mContext = getActivity().getApplicationContext();
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        listview = (ListView) view.findViewById(R.id.list);
        doWebRequestForCurrencyHave();

    }
    BusyDialog mBusyDialog;
    public void doWebRequestForCurrencyHave() {

        PeopleHaveList.removeAllPeopleHaveList();

        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }

        mBusyDialog = new BusyDialog(mContext, true, "Loading");
        mBusyDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.HttpUrl + "currency-have-list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.w("response", "are" + response);

                try {



                    JSONObject JsonObject = new JSONObject(response);
                    JSONArray JsonMain = JsonObject.getJSONArray("result");
                    for (int i = 0; i < JsonMain.length(); i++) {
                        JSONObject m = JsonMain.getJSONObject(i);
                        HomeListModel model = new HomeListModel();
                        model.setName(m.getString("user_name"));
                        model.setEnd_date(m.getString("end_date"));
                        model.setLocation(m.getString("location"));
                        model.setWant_currency(m.getString("have_crr"));
                        model.setWant_amount(m.getString("have_amount"));
                        model.setPay_currency(m.getString("sell_crr"));
                        model.setPay_amount(m.getString("sell_amount"));
                        model.setImage_url(m.getString("image"));
                        PeopleHaveList.setPeopleHaveinfo(model);

                    }

                    adapter = new HomeAdapter(mContext,  PeopleHaveList.getAllPeopleHave());
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mBusyDialog.dismis();


                } catch (JSONException e) {
                    e.printStackTrace();
                    mBusyDialog.dismis();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("response", "are" + error);
                mBusyDialog.dismis();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", BaseUrl.Api_key);
                params.put("user_id", PersistentUser.getUserID(mContext));
                params.put("curr",  "BDT");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }




}
