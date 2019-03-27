package com.socketio.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.socketio.client.R;
import com.socketio.client.entity.Recommendation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends BaseAdapter {

    private Context context;
    private List<Recommendation> list = new ArrayList<>();

    public  MainAdapter(Context mContext, List<Recommendation> mList){
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

        holder.getrecommendationinterval.setText(list.get(position).getGetrecommendationinterval());
        holder.getrecommendationtime.setText(list.get(position).getGetrecommendationtime());
        holder.starttime.setText(list.get(position).getStarttime());
        holder.timeouttime.setText(list.get(position).getTimeouttime());
        holder.updatetime.setText(list.get(position).getUpdatetime());
        holder.cameragroup.setText(list.get(position).getCameragroup());

        return view;
    }

    public class ViewHolder{
        @BindView(R.id.timeouttime)
        TextView timeouttime;
        @BindView(R.id.getrecommendationtime)
        TextView getrecommendationtime;
        @BindView(R.id.getrecommendationinterval)
        TextView getrecommendationinterval;
        @BindView(R.id.updatetime)
        TextView updatetime;
        @BindView(R.id.starttime)
        TextView starttime;
        @BindView(R.id.cameragroup)
        TextView cameragroup;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
