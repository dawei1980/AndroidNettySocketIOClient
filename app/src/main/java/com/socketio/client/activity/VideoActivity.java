package com.socketio.client.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.socketio.client.R;

import java.io.File;

public class VideoActivity extends AppCompatActivity {

//    private Button startCard = null;
//    private Button startUri = null;
//    private TextView fileName = null;
    private VideoView video = null;
    private String filename = null;
    private MediaController media = null;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

//        startCard = (Button) findViewById(R.id.startCard);
//        startUri = (Button) findViewById(R.id.startUri);
//        fileName = (TextView) findViewById(R.id.fileName);

//        startCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playVideoFromFile();
//            }
//        });
//
//        startUri.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openVideoFromUri();
//            }
//        });

        video = findViewById(R.id.videoView);
        media = new MediaController(VideoActivity.this);
        filename = Environment.getExternalStorageDirectory() + "/videos/video1.mp4";

        verifyStoragePermissions(VideoActivity.this);

        playVideoFromFile();
    }

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

    private void playVideoFromFile() {

        File file = new File(filename);
        if (file.exists()) {
            //将VideoView与MediaController进行关联
            video.setVideoPath(file.getAbsolutePath());
            video.setMediaController(media);
            media.setMediaPlayer(video);
            //让VideoView获取焦点
            video.requestFocus();
            video.start();

//        if (startCard.getText().toString().equals("PlayCard")) {
//            File file = new File(filename);
//            if (file.exists()) {
//                //将VideoView与MediaController进行关联
//                video.setVideoPath(file.getAbsolutePath());
//                video.setMediaController(media);
//                media.setMediaPlayer(video);
//                //让VideoView获取焦点
//                video.requestFocus();
//                video.start();
//                startCard.setText(R.string.pauseCard);
//                fileName.setText(filename);
//            }
        } else {
            video.pause();
//            startCard.setText(R.string.startCard);
        }
    }


//    private void openVideoFromUri() {
//        if (startUri.getText().toString().equals("PlayUri")) {
//            Uri uri = Uri.parse(filename);
//            video.setVideoURI(uri);
//            video.setMediaController(media);
//            media.setMediaPlayer(video);
//            //同上
//            video.requestFocus();
//            video.start();
//            startUri.setText(R.string.pauseUri);
//            fileName.setText(filename);
//        } else {
//            video.pause();
//            startUri.setText(R.string.startUri);
//        }
//    }
}
