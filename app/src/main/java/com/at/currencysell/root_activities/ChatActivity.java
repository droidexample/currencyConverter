package com.at.currencysell.root_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.at.currencysell.ChatDetailsActivity;
import com.at.currencysell.R;
import com.at.currencysell.adapter.UserListingRecyclerAdapter;
import com.at.currencysell.core.users.getall.GetUsersContract;
import com.at.currencysell.core.users.getall.GetUsersPresenter;
import com.at.currencysell.model.User;
import com.at.currencysell.utils.Constants;
import com.at.currencysell.utils.ItemClickSupport;

import java.util.List;

public class ChatActivity extends BaseActivity implements GetUsersContract.View, ItemClickSupport.OnItemClickListener {

    private GetUsersPresenter mGetUsersPresenter;
    private RecyclerView mRecyclerViewAllUserListing;

    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_chat, frameLayout);
        mContext = this;
        selecteddeselectedTab(3);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        initUI();
    }


    private void initUI() {

        mGetUsersPresenter = new GetUsersPresenter(this);
        getUsers();
        mRecyclerViewAllUserListing = (RecyclerView) findViewById(R.id.recycler_view_all_user_listing);

        ItemClickSupport.addTo(mRecyclerViewAllUserListing)
                .setOnItemClickListener(this);

    }


    private void getUsers() {

       /* if (TextUtils.equals((ARG_TYPE), TYPE_CHATS)) {

        } else if (TextUtils.equals((ARG_TYPE), TYPE_ALL)) {*/
        mGetUsersPresenter.getAllUsers();
        //}

    }


    @Override
    public void onGetAllUsersSuccess(List<User> users) {

        mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mUserListingRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onGetAllUsersFailure(String message) {
        Toast.makeText(mContext, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetChatUsersSuccess(List<User> users) {

    }

    @Override
    public void onGetChatUsersFailure(String message) {

    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(mContext, ChatDetailsActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER, mUserListingRecyclerAdapter.getUser(position).getEmail());
        intent.putExtra(Constants.ARG_RECEIVER_UID, mUserListingRecyclerAdapter.getUser(position).getUid());
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, mUserListingRecyclerAdapter.getUser(position).getFirebaseToken());

        startActivity(intent);

    }
}
