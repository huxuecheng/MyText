package com.atguigu.mytext.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mytext.R;
import com.atguigu.mytext.bean.HomeBean;
import com.atguigu.mytext.utils.Constancts;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 呼学成 on 09/03/2017.
 */

public class ChannelAdapter extends BaseAdapter {
    private final List<HomeBean.ResultBean.ChannelInfoBean> datas;
    private Context mcontext;

    public ChannelAdapter(Context mcontext, List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
        this.mcontext = mcontext;
        this.datas = channel_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_channel, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据位置获取相应数据
        HomeBean.ResultBean.ChannelInfoBean channelInfoBean = datas.get(position);
        Log.e("TAG", "ChannelAdapter getView()+获取位置成功");
        //设置名字
        viewHolder.tvChannel.setText(channelInfoBean.getChannel_name());
        //请求图片
        Glide.with(mcontext).load(Constancts.BASE_URL_IMAGE+channelInfoBean.getImage())
                .crossFade().into(viewHolder.ivChannel);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_channel)
        ImageView ivChannel;
        @InjectView(R.id.tv_channel)
        TextView tvChannel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
