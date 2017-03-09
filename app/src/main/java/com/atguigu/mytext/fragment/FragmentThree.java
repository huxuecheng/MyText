package com.atguigu.mytext.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.mytext.base.BaseFragment;

/**
 * Created by 呼学成 on 09/03/2017.
 */

public class FragmentThree extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(mcontext);
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
       Log.e("TAG", "FragmentThree initData()");
        textView.setText("333");
    }
}
