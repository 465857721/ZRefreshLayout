package com.kingsoft.zrefreshlayout.neteasy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.kingsoft.zrefreshlayout.R;
import com.kingsoft.zrefreshlayout.Utils;


public class NetEasyBall extends View {


    private final float degree = 15;// 左右摇摆的角度   椭圆形
    private final float mStrokeWidth = 4f;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    //    private float radius_min;//外面椭圆的半径 最大和最小直接变化
//    private float radius_max;//半径从大到小变化  区间 为 半径-4 到半径-10
    private float radius_x_max;
    private float radius_y_max;
    private float radius_x_min;
    private float radius_y_min;
    private float radius_x_current;
    private float radius_y_current;
    //三个旋转的点的x坐标
    private float p1_x;
    private float p2_x;
    private float p3_x;
    private float p1_line;//远点滑动的长度
    private float p2_line;
    private float p3_line;


    //    private float ratem
    private Context mContext;
    private float degree_current = 0;
    private boolean isrefreshing = false;//  刷新中和下拉状态  动画不一样  所以 做个标记

    public NetEasyBall(Context context) {
        super(context);
        this.mContext = context;
    }

    public NetEasyBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }

    public NetEasyBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public boolean isrefreshing() {
        return isrefreshing;
    }

    public void setIsrefreshing(boolean isrefreshing) {
        this.isrefreshing = isrefreshing;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //  下拉的时候 根据下拉距离来动态变化
//        radius_min = mWidth / 2 - Utils.dp2px_f(mContext, 10);
//        radius_max = mWidth / 2 - Utils.dp2px_f(mContext, 4);

        radius_x_max = mWidth / 2 - 10;
        radius_x_min = mWidth / 2 - 10 - Utils.dp2px_f(mContext, 3);

        radius_y_max = mHeight / 4 - Utils.dp2px_f(mContext, 3);
        radius_y_min = mHeight / 4 - Utils.dp2px_f(mContext, 5);

        //初始状态
        radius_x_current = radius_x_min;
        radius_y_current = radius_y_min;
        init();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));    //设置画笔颜色
        mPaint.setAntiAlias(false);                       //设置画笔为无锯齿
        mPaint.setStrokeWidth(mStrokeWidth);              //线宽
        //空心效果
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        // 整个画布为 40 *40 dp
        if (isrefreshing) {
            mPaint.setStrokeWidth(mStrokeWidth);
            RectF rectF = new RectF(
                    mWidth / 2 - radius_x_max,
                    mHeight / 2 - radius_y_max,
                    mWidth / 2 + radius_x_max,
                    mHeight / 2 + radius_y_max);
            // 画上半个 椭圆
            canvas.save();
            canvas.rotate(degree_current, mWidth / 2, mHeight / 2);
            canvas.drawArc(rectF, 180, 180, false, mPaint);// 第一个180 是开始的角度   第二个参数在这个角度上旋转 多少度
            canvas.restore();

            //圆环
            canvas.drawCircle(mWidth / 2, mHeight / 2, radius_x_min - 10, mPaint);

            // 画实心 圆
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasybg));
            canvas.drawCircle(mWidth / 2, mHeight / 2, radius_x_min - 10 - mStrokeWidth / 2, mPaint);//圆  减去 3  为 线宽度的一半

            //画点
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));
            canvas.drawCircle(p1_x, mHeight / 2 - 25, 4, mPaint);// 第一个点的坐标 y为 四分之一

            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));
            canvas.drawCircle(p2_x, mHeight / 2, 5, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));
            canvas.drawCircle(p3_x, mHeight / 2 + 14, 6, mPaint);


            // 下半个椭圆
            mPaint.setStrokeWidth(mStrokeWidth);
            canvas.save();
            canvas.rotate(degree_current, mWidth / 2, mHeight / 2);
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(rectF, 0, 180, false, mPaint);
            canvas.restore();
        } else {
            RectF rectF = new RectF(
                    mWidth / 2 - radius_x_current,
                    mHeight / 2 - radius_y_current,
                    mWidth / 2 + radius_x_current,
                    mHeight / 2 + radius_y_current);
            // 画上半个 椭圆
            canvas.drawArc(rectF, 180, 180, false, mPaint);// 第一个180 是开始的角度   第二个参数在这个角度上旋转 多少度

            //圆环
            canvas.drawCircle(mWidth / 2, mHeight / 2, radius_x_min - 10, mPaint);

            // 画实心 圆
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasybg));
            canvas.drawCircle(mWidth / 2, mHeight / 2, radius_x_min - 10 - 3.0f, mPaint);//圆  减去 3  为 线宽度的一半

            //画点
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));
            canvas.drawCircle(mWidth / 2 - 10, mHeight / 2 - 25, 4, mPaint);// 第一个点的坐标 y为 四分之一

            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));
            canvas.drawCircle(mWidth / 2 + 30, mHeight / 2, 5, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));
            canvas.drawCircle(mWidth / 2 - 20, mHeight / 2 + 14, 6, mPaint);

            // 下半个椭圆
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.neteasy_head_textcolor));
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(rectF, 0, 180, false, mPaint);
        }
        super.onDraw(canvas);
    }

    public void setRatio(float ratio) {
        radius_x_current = radius_x_min + (radius_x_max - radius_x_min) * ratio;
        radius_y_current = radius_y_min + (radius_y_max - radius_y_min) * ratio;
        degree_current = degree - (degree*2 * ratio);


        invalidate();
    }

    public void setRatio_l(float ratio) {
        float p1_ratio = 0.4f + ratio;
        if (p1_ratio > 1)
            p1_ratio = p1_ratio - 1;
        p1_line = (float) Math.sqrt(46 * 46 - 25 * 25);
        p1_x = mWidth / 2 - p1_line + p1_line * 2 * p1_ratio;

        float p2_ratio = 0.7f + ratio;
        if (p2_ratio > 1)
            p2_ratio = p2_ratio - 1;

        p2_line = 46;
        p2_x = mWidth / 2 - p2_line + p2_line * 2 * p2_ratio;


        float p3_ratio = 0.2f + ratio;
        if (p3_ratio > 1)
            p3_ratio = p3_ratio - 1;
        p3_line = (float) Math.sqrt(46 * 46 - 20 * 20);
        p3_x = mWidth / 2 - p3_line + p3_line * 2 * p3_ratio;

        invalidate();
    }


    public float getRadius_x_max() {
        return radius_x_max;
    }

    public float getRadius_y_max() {
        return radius_y_max;
    }

    public float getRadius_x_min() {
        return radius_x_min;
    }

    public float getRadius_y_min() {
        return radius_y_min;
    }


    //    public float getRadius_min() {
//        return radius_min;
//    }
//
//    public float getRadius_max() {
//        return radius_max;
//    }


}
