package com.socketio.client.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.socketio.client.R;
import com.socketio.client.adapter.MainAdapter;
import com.socketio.client.entity.VideoInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_listview)
    ListView main_listview;
    private MainAdapter mAdapter;

    @BindView(R.id.videoView)
    VideoView video;
    private MediaController media = null;
    private String filename = null;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private List<VideoInfo> mList = new ArrayList<>();

    //创建socket连接
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.104:8081");
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

        media = new MediaController(MainActivity.this);
        filename = Environment.getExternalStorageDirectory() + "/videos/video1.mp4";

        verifyStoragePermissions(MainActivity.this);
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

            try {
                JSONObject jsonObject = new JSONObject(data);

                int id = jsonObject.getInt("id");
                String state = jsonObject.getString("state");

                System.out.println(id);
                System.out.println(state);

                final VideoInfo videoInfo = new VideoInfo();

                videoInfo.setState(jsonObject.getInt("state"));
                videoInfo.setVideoUrl(jsonObject.getString("video_url"));
                videoInfo.setSwitchEffect(jsonObject.getInt("switch_effect"));
                videoInfo.setPlayDuration(jsonObject.getInt("play_duration"));
                videoInfo.setIsLoop(jsonObject.getInt("is_loop"));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mList.add(videoInfo);
                        mAdapter = new MainAdapter(MainActivity.this,mList);
                        main_listview.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        if(mList.size() == 10){
                            mList.clear();
                            main_listview.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
