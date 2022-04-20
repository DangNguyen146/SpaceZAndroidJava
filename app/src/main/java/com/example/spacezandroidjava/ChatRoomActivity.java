package com.example.spacezandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spacezandroidjava.Model.Message;
import com.paypal.android.sdk.ed;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatRoomActivity extends AppCompatActivity {
//    RecyclerView rvChat;
    ListView rvChat;
    List<Message> listMessage;
    MessageAdapter arrayAdapter;
    private Context context;
    private  MessageAdapter messageAdapter;
    private Emitter.Listener sendMessageToClient= new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    String message;
                    String username;
                    try {
                        message=data.getString("message");
                        username=data.getString("username");
//                        rvChat=(RecyclerView) findViewById(R.id.lv_chat);

//                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplication());

//                        rvChat.setLayoutManager(linearLayoutManager);
                        listMessage.add(new Message(username,message));
                        arrayAdapter.notifyDataSetChanged();




//                        messageAdapter=new MessageAdapter(listMessage,getApplication());
//                        rvChat.setAdapter(messageAdapter);
//                        messageAdapter.setMessage(listMessage);








                    }
                    catch (JSONException e) {
                        return;
                    }
                }
            });




        }
    };


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.137.1:3000/");

        } catch (URISyntaxException e) {
            Log.i("loi", "instance initializer: ");
        }
    }
    EditText edChat;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        final SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String userName=pref.getString("userName","user");
        ImageButton sendBtn=(ImageButton) findViewById(R.id.send);
        TextView tvUserChat=(TextView) findViewById(R.id.chat_username);

        listMessage=new ArrayList<Message>();
        rvChat=(ListView) findViewById(R.id.lv_chat);
        arrayAdapter=new MessageAdapter(this,R.layout.activity_chat_room,listMessage);
        rvChat.setAdapter(arrayAdapter);
        mSocket.connect();
        Intent i=getIntent();
        edChat=(EditText) findViewById(R.id.chat_box);
         int roomId=i.getIntExtra("roomId",-1);
        String username=i.getStringExtra("username");
        tvUserChat.setText(username);
        if(roomId!= -1)
            mSocket.emit("join_room",roomId);
        mSocket.on("send_message_to_client",sendMessageToClient);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSocket.emit("send_message_to_server", userName,edChat.getText().toString());
                edChat.setText("");
            }
        });
//        edChat.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                 view.performClick();
//                final int DRAWABLE_LEFT = 0;
//                final int DRAWABLE_TOP = 1;
//                final int DRAWABLE_RIGHT = 2;
//                final int DRAWABLE_BOTTOM = 3;
//                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    if(motionEvent.getRawX() >= (edChat.getRight() - edChat.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        // your action here
//                        mSocket.emit("send_message_to_server",edChat.getText().toString());
//                        edChat.setText("");
//                        return true;
//                    }
//                }
//                 return false;
//
//            }
//        });


        }


    }
