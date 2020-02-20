package com.socketio.client.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.socketio.client.R;
import com.socketio.client.entity.VideoInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends BaseAdapter {

    private Context context;
    private List<VideoInfo> list = new ArrayList<>();

    public  MainAdapter(Context mContext, List<VideoInfo> mList){
        this.context = mContext;
        this.list = mList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.stateView.setText(list.get(position).getState()+"");
        holder.videoUrl.setText(list.get(position).getVideoUrl());
        holder.playDuration.setText(list.get(position).getPlayDuration()+"");
        holder.switchEffect.setText(list.get(position).getSwitchEffect()+"");
        holder.isLoop.setText(list.get(position).getIsLoop()+"");
        return view;
    }

    public class ViewHolder{
        @BindView(R.id.state_view)
        TextView stateView;
        @BindView(R.id.video_url)
        TextView videoUrl;
        @BindView(R.id.play_duration)
        TextView playDuration;
        @BindView(R.id.switch_effect)
        TextView switchEffect;
        @BindView(R.id.is_loop)
        TextView isLoop;
        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
