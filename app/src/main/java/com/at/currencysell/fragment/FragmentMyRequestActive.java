package com.at.currencysell.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.at.currencysell.R;
import com.at.currencysell.adapter.MyRequestsAdapter;
import com.at.currencysell.holder.MyRequestsActiveList;
import com.at.currencysell.model.MyReqestActiveMoel;
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

public class FragmentMyRequestActive extends Fragment {


    private View view;
    private Context mContext;
    private MyRequestsAdapter adapter;
    private ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fagment_my_request_active, container, false);
        mContext = getActivity().getApplicationContext();
        initUI(view);
        return view;
    }

    private void initUI(View view) {

        listview = (ListView) view.findViewById(R.id.list_home);
        doWebRequestForRequestActive();

    }

    BusyDialog mBusyDialog;

    public void doWebRequestForRequestActive() {

         MyRequestsActiveList.removeAllReqestActiveList();

        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }

        mBusyDialog = new BusyDialog(mContext, true, "Loading");
        mBusyDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.HttpUrl + "my-request-active", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.w("response", "are" + response);

                try {

                    JSONObject JsonObject = new JSONObject(response);
                    int success = JsonObject.getInt("success");
                    if (success==1){


                    JSONArray JsonMain = JsonObject.getJSONArray("result");
                    for (int i = 0; i < JsonMain.length(); i++) {
                        JSONObject m = JsonMain.getJSONObject(i);
                        MyReqestActiveMoel model = new MyReqestActiveMoel();
                        model.setCurrency_have(m.getString("currency_have"));
                        model.setAmount_have(m.getString("amount_have"));
                        model.setCurrency_need(m.getString("currency_need"));
                        model.setAmount_need(m.getString("amount_need"));
                        model.setEnd_date(m.getString("end_date"));

                        MyRequestsActiveList.setReqestActiveinfo(model);

                    }

                    adapter = new MyRequestsAdapter(mContext,  MyRequestsActiveList.getAllReqestActive());
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mBusyDialog.dismis();
                    }else {
                        Toast.makeText(mContext, "" + JsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                    }

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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }


}
