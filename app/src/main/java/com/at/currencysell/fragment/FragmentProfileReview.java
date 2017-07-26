package com.at.currencysell.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.at.currencysell.adapter.ProfileReviewAdapter;
import com.at.currencysell.holder.PeopleHaveList;
import com.at.currencysell.holder.ProfileReviewList;
import com.at.currencysell.model.HomeListModel;
import com.at.currencysell.model.ProfileReviewModel;
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


public class FragmentProfileReview extends Fragment {


    private View view;
    private Context mContext;
    private ProfileReviewAdapter adapter;
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
        doWebRequestForProfileReview();

    }
    BusyDialog mBusyDialog;
    public void doWebRequestForProfileReview() {

        PeopleHaveList.removeAllPeopleHaveList();

        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }

        mBusyDialog = new BusyDialog(mContext, true, "Loading");
        mBusyDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.HttpUrl + "user-details", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.w("response", "are" + response);

                try {

                    JSONObject JsonObject = new JSONObject(response);
                    JSONArray JsonMain = JsonObject.getJSONArray("reviewDetails");
                    for (int i = 0; i < JsonMain.length(); i++) {
                        JSONObject m = JsonMain.getJSONObject(i);
                        ProfileReviewModel model = new ProfileReviewModel();
                        model.setReview_userName(m.getString("review_userName"));
                        model.setComment(m.getString("comment"));
                      /*  model.setLocation(m.getString("location"));
                        model.setWant_currency(m.getString("have_crr"));
                        model.setWant_amount(m.getString("have_amount"));
                        model.setPay_currency(m.getString("sell_crr"));
                        model.setPay_amount(m.getString("sell_amount"));
                        model.setImage_url(m.getString("image"));*/
                        ProfileReviewList.setPeopleHaveinfo(model);

                    }

                    adapter = new ProfileReviewAdapter(mContext,  ProfileReviewList.getAllPeopleHave());
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }


}
