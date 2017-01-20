package com.kingsoft.zrefreshlayout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class NetEasyHeadView extends RelativeLayout implements IRefreshHeader {


    private Context mContext;
    private TextView tv_state;
    private View rl_head_before;
    private TextView tv_finish;
    private AnimatorSet set;// 刷新完成后 文字  缩放动画集合
    private NetEasyBall mNetEasyBall;

    private ObjectAnimator animatorCircle;// 外部圆环动画集合

    public NetEasyHeadView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public NetEasyHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }


    public NetEasyHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.neteasy_head, this);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        rl_head_before = findViewById(R.id.rl_head_before);
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        mNetEasyBall = (NetEasyBall) findViewById(R.id.myball);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tv_finish, View.SCALE_X, 1f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tv_finish, View.SCALE_Y, 1f, 1.2f, 1f);
        set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(scaleX, scaleY);
        set.setInterpolator(new AccelerateInterpolator());//加速器 两头快 中间慢

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void reset() {
        rl_head_before.setVisibility(View.VISIBLE);
        tv_state.setText("下拉推荐");
    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {
//        tv_state.setText("正在刷新");
        star();
    }

    private void star() {
        if (animatorCircle == null) {
            animatorCircle = ObjectAnimator.ofFloat(mNetEasyBall, "Ratio", 1, 0, 1);
            animatorCircle.setDuration(1000);
            animatorCircle.setRepeatCount(ValueAnimator.INFINITE);
        }

        animatorCircle.start();

    }


    /**
     * 头部滚动的时候持续调用
     *
     * @param currentPos target当前偏移高度
     * @param lastPos    target上一次的偏移高度
     * @param refreshPos 可以松手刷新的高度
     * @param isTouch    手指是否按下状态（通过scroll自动滚动时需要判断）
     * @param state      当前状态
     */
    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, RefreshLayout.State state) {
        float animStarPos = currentPos - 60;//下拉到一定程度才开始执行半径变化

        if (animStarPos > 0 && currentPos < refreshPos) {
            float ratio = (currentPos - 60) / (refreshPos - 60);//比例
//            float radius = mNetEasyBall.getRadius_min() +
//                    ((mNetEasyBall.getRadius_max() - mNetEasyBall.getRadius_min()) * ratio);
            mNetEasyBall.setRatio(ratio);
//            Log.d("zk", "currentPos=" + currentPos + "refreshPos=" + refreshPos);
//            Log.d("zk", "radius=" + radius);
        }
//        Log.d("zk", "currentPos=" + currentPos + "refreshPos=" + refreshPos);


        switch (state) {
            case COMPLETE:
                tv_state.setText("推荐完成");
                break;
            case LOADING:
                tv_state.setText("正在推荐");
                break;
            case RESET:
                tv_state.setText("下拉推荐");
                break;
            case PULL:
                if (currentPos > refreshPos)
                    tv_state.setText("松开推荐");
                else
                    tv_state.setText("下拉推荐");
                break;
        }
    }

    @Override
    public void complete() {
        tv_state.setText("推荐完成");
        animatorCircle.cancel();
        rl_head_before.setVisibility(View.GONE);
        set.start();
    }


}
