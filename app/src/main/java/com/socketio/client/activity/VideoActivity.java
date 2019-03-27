package com.socketio.client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.socketio.client.R;

import java.io.File;

public class VideoActivity extends Activity {
    private String filename = null;
//    private Button startCard = null;
//    private Button startUri = null;
//    private TextView fileName = null;
    private VideoView video = null;
    private MediaController media = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        filename = Environment.getExternalStorageDirectory() + "/videos/video1.mp4";

//        startCard = (Button) findViewById(R.id.startCard);
//        startUri = (Button) findViewById(R.id.startUri);
//        fileName = (TextView) findViewById(R.id.fileName);
        video = findViewById(R.id.videoView);
        media = new MediaController(VideoActivity.this);

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
        playVideoFromFile();
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
