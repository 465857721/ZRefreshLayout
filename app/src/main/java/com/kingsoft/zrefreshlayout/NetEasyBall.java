package com.kingsoft.zrefreshlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class NetEasyBall extends View {


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

    //    private float ratem
    private Context mContext;

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

        radius_y_max = mHeight / 4- Utils.dp2px_f(mContext, 3);
        radius_y_min = mHeight / 4 - Utils.dp2px_f(mContext, 5);

        //初始状态
        radius_x_current = radius_x_min;
        radius_y_current = radius_y_min;
        init();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.neteasy_head_textcolor));    //设置画笔颜色
        mPaint.setAntiAlias(false);                       //设置画笔为无锯齿
        mPaint.setStrokeWidth((float) 6.0);              //线宽
        //空心效果
    }


    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawCircle(mWidth / 2, mHeight / 2, radius_current, mPaint);
        RectF rectF = new RectF(
                mWidth / 2 - radius_x_current,
                mHeight / 2 - radius_y_current,
                mWidth / 2 + radius_x_current,
                mHeight / 2 + radius_y_current);
        Log.d("zk", "radius_y_min=" + rectF.toString());
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(rectF, mPaint);//椭圆

        canvas.drawCircle(mWidth / 2, mHeight / 2, radius_x_min - 10, mPaint);//圆环

//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(mWidth / 2, mHeight / 2, radius_x_current - 6, mPaint);
        super.onDraw(canvas);
    }

    public void setRatio(float ratio) {
        radius_x_current = radius_x_min + (radius_x_max - radius_x_min) * ratio;
        radius_y_current = radius_y_min + (radius_y_max - radius_y_min) * ratio;

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
