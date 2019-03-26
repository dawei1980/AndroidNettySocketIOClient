package com.socketio.client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.socketio.client.R;
import com.socketio.client.adapter.MainAdapter;
import com.socketio.client.entity.Recommendation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIOActivity extends Activity {

    @BindView(R.id.socketio_listview)
    ListView socketio_listview;
    private MainAdapter mAdapter;
    private List<Recommendation> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socketio);

        ButterKnife.bind(this);

        initView();
        socketConnection();
    }

    private void initView(){
        mAdapter = new MainAdapter(SocketIOActivity.this,mList);
        socketio_listview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void socketConnection(){
        String url ="http://192.168.0.186:8081";
        try{
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.reconnectionAttempts = 2;
            options.reconnectionDelay = 1000;//失败重连的时间间隔
            options.timeout = 500;//连接超时时间(ms)
            //par1 是任意参数
            final Socket socket = IO.socket(url+"?par1=1234", options);

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                public void call(Object... args) {
                    socket.send("hello");
                }
            });

            //自定义事件
            /**接收服务器广播发来的数据*/
            socket.on("borcast", new Emitter.Listener() {
                public void call(Object... objects) {
                    System.out.println("receive borcast data:" + objects[0].toString());
                }
            });

            socket.on("connected", new Emitter.Listener() {
                public void call(Object... objects) {
                    System.out.println("receive connected data:" + objects[0].toString());
                }
            });

            socket.connect();
            //循环发送数据
//            while (true){
//                socket.emit("client_info"," 客户端在发送数据");
//                Thread.sleep(2000);
//            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
