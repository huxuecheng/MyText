package com.atguigu.mytext;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atguigu.mytext.fragment.CommunityFragment;
import com.atguigu.mytext.fragment.HomeFragment;
import com.atguigu.mytext.fragment.ShoppingcartFragment;
import com.atguigu.mytext.fragment.TypeFragment;
import com.atguigu.mytext.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    RadioButton rbUser;
    @InjectView(R.id.frameLayout)
    FrameLayout frameLayout;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    //装fg的集合
    private ArrayList<Fragment> fragments;
    //fg的位置
    private int position;
    //被缓存的fg
    private Fragment tempfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //初始化视图
        initFragment();
        //根据位置切换不同的fg
        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }
                //根据位置切换到不同的fg
                Fragment currentfg = fragments.get(position);
                switchFragment(currentfg);
            }
        });
        rgMain.check(R.id.rb_home);
    }

    //实现fg的选择
    private void switchFragment(Fragment currentfg) {
        if (tempfragment != currentfg) {
            FragmentManager fm = getSupportFragmentManager();
            //开启事务
            FragmentTransaction ft = fm.beginTransaction();
            if (!currentfg.isAdded()) {
                if (tempfragment != null) {
                    ft.hide(tempfragment);
                }
                ft.add(R.id.frameLayout, currentfg);
            } else {//添加过
                if (tempfragment != null) {//有缓存
                    ft.hide(tempfragment);
                }
                //显示
                ft.show(currentfg);
            }
            //事务提交
            ft.commit();
            //把当前的赋值成缓存的
            tempfragment = currentfg;

        }


    }

    //将5个fg加入道集合中 方便取位置进行切换
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingcartFragment());
        fragments.add(new UserFragment());

    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        int id = intent.getIntExtra("checkedid",R.id.rb_home);
//        switch (id){
//            case R.id.rb_home:
//                rgMain.check(R.id.rb_home);
//                break;
//            case R.id.rb_cart:
//                rgMain.check(R.id.rb_cart);
//                break;
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ButterKnife.;
//    }
}
