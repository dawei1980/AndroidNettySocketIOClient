package com.socketio.client.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.socketio.client.R;
import com.socketio.client.adapter.MainAdapter;
import com.socketio.client.entity.Recommendation;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_listview)
    ListView main_listview;
    private MainAdapter mAdapter;

    private List<Recommendation> mList = new ArrayList<>();

    //创建socket连接
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.0.186:8081");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        socketConn();
        mSocket.on("borcast", onLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("borcast", onLogin);

        //取消连接Server
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
    }

    //连接到Server
    private void socketConn() {
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.connect();
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
            final String data = (String) objects[0];

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        Recommendation recommendation = new Recommendation();

                        recommendation.setGetrecommendationinterval(jsonObject.getString("getrecommendationinterval"));
                        recommendation.setGetrecommendationtime(jsonObject.getString("getrecommendationtime"));
                        recommendation.setStarttime(jsonObject.getString("starttime"));
                        recommendation.setTimeouttime(jsonObject.getString("timeouttime"));
                        recommendation.setUpdatetime(jsonObject.getString("updatetime"));
                        recommendation.setCameragroup(jsonObject.getString("cameragroup"));

                        mList.add(recommendation);
                        mAdapter = new MainAdapter(MainActivity.this,mList);
                        main_listview.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        if(mList.size() == 10){
                            mList.clear();
                            main_listview.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Failed to connected...", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    @OnClick({R.id.main_clear_btn})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.main_clear_btn:
                Intent intent = new Intent(MainActivity.this,VideoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
