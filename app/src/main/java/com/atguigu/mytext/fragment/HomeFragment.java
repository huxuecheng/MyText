package com.atguigu.mytext.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.mytext.R;
import com.atguigu.mytext.adapter.HomeAdapter;
import com.atguigu.mytext.base.BaseFragment;
import com.atguigu.mytext.bean.HomeBean;
import com.atguigu.mytext.utils.Constancts;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by 呼学成 on 09/03/2017.
 */

public class HomeFragment extends BaseFragment {



    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.ll_main_scan)
    LinearLayout llMainScan;
    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.ib_top)
    ImageButton ibTop;
    private HomeBean.ResultBean result;

    @Override
    public View initView() {
        View view = View.inflate(mcontext, R.layout.fragment_home, null);
        ButterKnife.inject(HomeFragment.this, view);
        return view;

    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();//请求数据
    }

    private void getDataFromNet() {
        String url = Constancts.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "主页联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "主页联网成功");
                        processData(response);//解析数据
                    }
                });
    }
    //
    private void processData(String response) {
        HomeBean homeBean = paraseJson(response);
        result = homeBean.getResult();//获取解析之后的数据,设置适配器
        if(result!=null){
            HomeAdapter adapter = new HomeAdapter(mcontext,result);
            rvHome.setAdapter(adapter);
            //设置布局管理器
            GridLayoutManager manager = new GridLayoutManager(mcontext,1);
            //设置监听跨度
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position <=3){
                        //隐藏按钮
                        ibTop.setVisibility(View.GONE);
                    }else{
                        //显示按钮
                        ibTop.setVisibility(View.VISIBLE);
                    }
                    return 1;//这个只能是1
                }
            });
            rvHome.setLayoutManager(manager);


        }else{
            Toast.makeText(mcontext, "没有请求到数据", Toast.LENGTH_SHORT).show();
        }
    }

    private HomeBean paraseJson(String response) {
        return JSON.parseObject(response, HomeBean.class);//将解析到的数据放到HomeBean中

    }


    @OnClick({R.id.tv_search, R.id.ll_main_scan, R.id.ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                Toast.makeText(mcontext, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_main_scan:
                Toast.makeText(mcontext, "扫一扫", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_top:
                rvHome.scrollToPosition(0);
                break;
        }
    }

}
