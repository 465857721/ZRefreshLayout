package com.kingsoft.zrefreshlayout.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingsoft.zrefreshlayout.IRefreshHeader;
import com.kingsoft.zrefreshlayout.R;
import com.kingsoft.zrefreshlayout.RefreshLayout;


public class CommonHeadView extends RelativeLayout implements IRefreshHeader {


    private Context mContext;
    private TextView tv_state;

    public CommonHeadView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CommonHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }


    public CommonHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.common_head, this);
        tv_state = (TextView) view.findViewById(R.id.tv_state);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void reset() {
        tv_state.setText("下拉刷新");
    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {
//        tv_state.setText("正在刷新");
        star();
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, RefreshLayout.State state) {

        switch (state) {
            case COMPLETE:
                tv_state.setText("刷新完成");
                break;
            case LOADING:
                tv_state.setText("正在刷新");
                break;
            case RESET:
                tv_state.setText("下拉刷新");
                break;
            case PULL:
                if (currentPos > refreshPos)
                    tv_state.setText("松开刷新");
                else
                    tv_state.setText("下拉刷新");
                break;
        }
    }

    @Override
    public void complete() {
        tv_state.setText("刷新完成");
        stop();
    }

    public void star() {
        //可以执行动画
    }

    public void stop() {

    }

}
