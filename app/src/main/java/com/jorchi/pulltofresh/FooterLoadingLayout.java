package com.jorchi.pulltofresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FooterLoadingLayout extends LoadingLayout {
    private ProgressBar mProgressBar;
    private TextView mHintView;

    public FooterLoadingLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FooterLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mProgressBar = findViewById(R.id.pull_to_load_footer_progressbar);
        mHintView = findViewById(R.id.pull_to_load_footer_hint_textview);
        setState(State.RESET);
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer, null);
        return container;
    }

    @Override
    public int getContentSize() {
        View view = findViewById(R.id.pull_to_load_footer_content);
        if (null != null){
            return view.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density*40);
    }

    @Override
    protected void onStateChanged(State curState, State mPreState) {
        mProgressBar.setVisibility(GONE);
        mHintView.setVisibility(INVISIBLE);
        super.onStateChanged(curState, mPreState);
    }

    @Override
    protected void onReset() {
        mHintView.setText("正在加载中");
    }

    @Override
    protected void onPullToRefresh() {
        mHintView.setVisibility(VISIBLE);
        mHintView.setText("上拉可以刷新");
    }

    @Override
    protected void onReleaseToRefresh() {
        mHintView.setVisibility(VISIBLE);
        mHintView.setText("松开后刷新");
    }

    @Override
    protected void onRefreshing() {
        mHintView.setVisibility(VISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mHintView.setText("正在加载中");
    }

    @Override
    protected void onNoMoreData() {
        mHintView.setVisibility(VISIBLE);
        mHintView.setText("没有更多数据了");
    }
}




























