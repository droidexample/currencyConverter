package com.at.currencysell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import android.support.annotation.Nullable;

import com.at.currencysell.adapter.ChatRecyclerAdapter;
import com.at.currencysell.core.chat.ChatContract;
import com.at.currencysell.core.chat.ChatPresenter;
import com.at.currencysell.model.PushNotificationEvent;
import com.at.currencysell.model.Chat;
import com.at.currencysell.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class ChatDetailsActivity extends AppCompatActivity implements ChatContract.View {

    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;
    private ChatPresenter mChatPresenter;
    private ChatRecyclerAdapter mChatRecyclerAdapter;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        init();


    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void init() {

        mRecyclerViewChat = (RecyclerView) findViewById(R.id.recycler_view_chat);
        mETxtMessage = (EditText) findViewById(R.id.edit_text_message);

        mChatPresenter = new ChatPresenter(this);
        mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID));


    }

    public void send(View view) {
        sendMessage();
    }



    private void sendMessage() {
        String message = mETxtMessage.getText().toString();
        String receiver = getIntent().getExtras().getString(Constants.ARG_RECEIVER);
        String receiverUid = getIntent().getExtras().getString(Constants.ARG_RECEIVER_UID);
        String sender = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverFirebaseToken = getIntent().getExtras().getString(Constants.ARG_FIREBASE_TOKEN);

        Chat chat = new Chat(sender,
                receiver,
                senderUid,
                receiverUid,
                message,
                System.currentTimeMillis());
        mChatPresenter.sendMessage(ChatDetailsActivity.this,
                chat,
                receiverFirebaseToken);


    }


    @Override
    public void onSendMessageSuccess() {
        mETxtMessage.setText("");
        Toast.makeText(ChatDetailsActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendMessageFailure(String message) {
        Toast.makeText(ChatDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMessagesSuccess(Chat chat) {

        if (mChatRecyclerAdapter == null) {

            mChatRecyclerAdapter = new ChatRecyclerAdapter(new ArrayList<Chat>());

            mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);
            mChatRecyclerAdapter.notifyDataSetChanged();
        }
        mChatRecyclerAdapter.add(chat);
        mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
    }

    @Override
    public void onGetMessagesFailure(String message) {
        Toast.makeText(ChatDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        if (mChatRecyclerAdapter == null || mChatRecyclerAdapter.getItemCount() == 0) {
            mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pushNotificationEvent.getUid());
        }
    }


}
