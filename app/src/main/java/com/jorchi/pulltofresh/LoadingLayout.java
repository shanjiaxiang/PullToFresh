package com.jorchi.pulltofresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {

    /**
     * 容器布局
     */
    private View mContainer;
    /**
     * 当前的状态
     */
    private State mCurState = State.NONE;
    /**
     * 前一个状态
     */
    private State mPreState = State.NONE;

    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 初始化，通过抽象方法createLoadingView获取要加载视图， 设定布局参数，添加到根布局中
     */
    protected void init(Context context, AttributeSet attrs) {
        mContainer = createLoadingView(context, attrs);
        if (null == mContainer) {
            throw new NullPointerException("Loading view can not be null.");
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mContainer, params);
    }

    public void show(boolean show) {
        if (show == (View.VISIBLE == getVisibility())) {
            return;
        }
        ViewGroup.LayoutParams params = mContainer.getLayoutParams();
        if (null != params) {
            if (show) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = 0;
            }
            setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }


    // 最后更新时间文字
    public void setLastUpdatedLabel(CharSequence label) {
    }

    // 刷新过程中显示图片
    public void setLoadingDrawable(Drawable drawable) {
    }

    // 设置拉动时文字
    public void setPullLabel(CharSequence pullLabel) {
    }

    // 刷新时显示的文字
    public void setRefreshingLabel(CharSequence refreshingLabel) {
    }

    // 设置提示松开文字
    public void setReleaseLabel(CharSequence releaseLabel) {
    }


    @Override
    public void setState(State curState) {
        if (mCurState != curState) {
            mPreState = mCurState;
            mCurState = curState;
            onStateChanged(curState, mPreState);
        }
    }

    @Override
    public State getState() {
        return mCurState;
    }

    @Override
    public void onPull(float scale) {

    }

    protected State getPreState() {
        return mPreState;
    }

    protected void onStateChanged(State curState, State mPreState) {
        switch (curState) {
            case RESET:
                onReset();
                break;
            case RELEASE_TO_REFRESH:
                onReleaseToRefresh();
                break;
            case PULL_TO_REFRESH:
                onPullToRefresh();
                break;
            case REFRESHING:
                onRefreshing();
                break;
            case NO_MORE_DATA:
                onNoMoreData();
            default:
                break;
        }
    }

    protected void onNoMoreData() {

    }

    protected void onRefreshing() {
    }

    protected void onPullToRefresh() {
    }

    protected void onReleaseToRefresh() {

    }

    protected void onReset() {
    }

    protected abstract View createLoadingView(Context context, AttributeSet attrs);

    public abstract int getContentSize();
}
