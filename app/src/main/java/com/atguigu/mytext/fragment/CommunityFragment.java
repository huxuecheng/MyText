package com.atguigu.mytext.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.atguigu.mytext.MainActivity;
import com.atguigu.mytext.R;
import com.atguigu.mytext.base.BaseFragment;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 呼学成 on 09/03/2017.
 */

public class CommunityFragment extends BaseFragment {


    @InjectView(R.id.tl_1)
    SegmentTabLayout tl1;
    @InjectView(R.id.fl_type)
    FrameLayout flType;
    private String[] titles = {"11111","22222","33333"};

    private ArrayList<BaseFragment> fragments;
    //缓存的Fg
    private Fragment tempFragment;

    @Override
    public View initView() {
        View view = View.inflate(mcontext, R.layout.fragment_type, null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initListener();
        initFragment();
        switchFragment(fragments.get(0));
    }

    private void switchFragment(BaseFragment currentFragment) {
        //切换的不是同一个页面
        if(tempFragment != currentFragment){

            MainActivity activity = (MainActivity) mcontext;
            //得到FragmentMager
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            //如果没有添加就添加
            if(!currentFragment.isAdded()){
                //缓存的隐藏
                if(tempFragment != null){
                    ft.hide(tempFragment);
                }
                //添加
                ft.add(R.id.fl_type,currentFragment);
            }else{
                //缓存的隐藏
                if(tempFragment != null){
                    ft.hide(tempFragment);
                }
                //显示
                ft.show(currentFragment);
            }
            //事务提交
            ft.commit();
            //把当前的赋值成缓存的
            tempFragment = currentFragment;

        }
    }

    private void initListener() {
        tl1.setTabData(titles);
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragments.get(position));

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new FragmentOne());
        fragments.add(new FragmentTwo());
        fragments.add(new FragmentThree());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
