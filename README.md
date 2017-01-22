# ZRefreshLayout
自定义下拉刷新，支持自定义头部样式，还是学习为主。

# Usage
CommonHeadView为一个基本例子，可以自己实现IRefreshHeader接口来自定义自己的头部样式，以及动画。
根据回调自己实现相应的效果。


    /**
     * 下拉出头部的一瞬间调用
     */
    void pull();
    /**
     * 松手，头部隐藏后会回调这个方法
     */
    void reset();

    /**
     * 正在刷新的时候调用
     */
    void refreshing();

    /**
     * 头部滚动的时候持续调用
     *
     * @param currentPos target当前偏移高度
     * @param lastPos    target上一次的偏移高度
     * @param refreshPos 可以松手刷新的高度
     * @param isTouch    手指是否按下状态（通过scroll自动滚动时需要判断）
     * @param state      当前状态
     */
    void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, RefreshLayout.State state);

    /**
     * 刷新成功的时候调用
     */
    void complete();

# ScreenShots
普通样子
![] (https://github.com/465857721/ZRefreshLayout/blob/master/ScreenShots/common.gif)
网易新闻
![网易新闻] (https://github.com/465857721/ZRefreshLayout/blob/master/ScreenShots/neteasy.gif)
# TODO
写几个比较常用的样式



V0.0.1(2017/01/22)
- 实现网易新闻下拉刷新
V0.0.1(2017/01/19)
- 项目导入
