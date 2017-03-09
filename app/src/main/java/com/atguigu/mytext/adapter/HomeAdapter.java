package com.atguigu.mytext.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.atguigu.mytext.R;
import com.atguigu.mytext.bean.HomeBean;
import com.atguigu.mytext.utils.Constancts;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 呼学成 on 09/03/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    public static final int BANNER = 0;//广告条 binner
    public static final int CHANNEL = 1;//频道 GridView
    private Context mcontext;
    private HomeBean.ResultBean result;
    private int currentType = BANNER;
    LayoutInflater inflater;

    public HomeAdapter(Context mcontext, HomeBean.ResultBean result) {
        this.mcontext = mcontext;
        this.result = result;
        inflater = LayoutInflater.from(mcontext);
    }

    //获取不同的itemview类型
    @Override
    public int getItemViewType(int position) {
        if (position == BANNER) {
            currentType = BANNER;
        }
        if (position == CHANNEL) {
            currentType = CHANNEL;
        }
        return currentType;
    }

    //创建不同的viewHoldr
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerviewHolder(inflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mcontext, inflater.inflate(R.layout.channel_item, null));
        }
        return null;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerviewHolder viewHodler = (BannerviewHolder) holder;
            //设置banner的数据
            viewHodler.setData(result.getBanner_info());

        } else if (getItemViewType(position) == CHANNEL) {
             ChannelViewHolder viewHodler = (ChannelViewHolder) holder;
            //设置gridView的数据
            viewHodler.setData(result.getChannel_info());
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    //banner-----------------------------------------------------------------------------
    class BannerviewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.banner)
        Banner banner;

        public BannerviewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setData(List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            List<String> imageUrls = new ArrayList<>();//存放图片url的集合
            for (int i = 0; i < banner_info.size(); i++) {
                imageUrls.add(Constancts.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }
            banner.setImages(imageUrls).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).crossFade().into(imageView);
                }
            }).start();
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private Context mcontext;
        @InjectView(R.id.gv_channel)
        GridView gvChannel;
        ChannelAdapter channelAdapter;
        public ChannelViewHolder(Context mcontext, View itemView) {
            super(itemView);
            this.mcontext = mcontext;
            ButterKnife.inject(this,itemView);
        }

        public void setData(final List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
            //设置适配器
            channelAdapter = new ChannelAdapter(mcontext,channel_info);
            gvChannel.setAdapter(channelAdapter);
            //设置点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mcontext, "我是"+channel_info.get(position).getChannel_name(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
