package com.kingsoft.zrefreshlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.kingsoft.zrefreshlayout.neteasy.NetEasyHeadView;

public class MainActivity extends AppCompatActivity {
    RelativeLayout rl_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        rl_main = (RelativeLayout) findViewById(R.id.activity_main);


        if (refreshLayout != null) {
            // 刷新状态的回调
            refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // 延迟3秒后刷新成功
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.refreshComplete();
                        }
                    }, 5000);
                }
            });
//            CommonHeadView headView = new CommonHeadView(this);//最普通的


            NetEasyHeadView headView = new NetEasyHeadView(this);
            rl_main.setBackgroundColor(getResources().getColor(R.color.neteasybg));// 设置背景 根据需求自己设定


            refreshLayout.setRefreshHeader(headView);
//            refreshLayout.autoRefresh();
        }
    }
}
