package com.jorchi.pulltofresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HeaderLoadingLayout extends LoadingLayout {

    private static final int ROTATE_ANIM_DURATION = 150;
    private RelativeLayout mHeaderContainer;
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private TextView mHeaderTimeView;
    private TextView mHeaderTimeViewTitle;
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    public HeaderLoadingLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public HeaderLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mHeaderContainer = findViewById(R.id.pull_to_refresh_header_content);
        mArrowImageView = findViewById(R.id.pull_to_refresh_header_arrow);
        mHintTextView = findViewById(R.id.pull_to_refresh_header_hint_textview);
        mProgressBar = findViewById(R.id.pull_to_refresh_header_progressbar);
        mHeaderTimeView = findViewById(R.id.pull_to_refresh_header_time);
        mHeaderTimeViewTitle = findViewById(R.id.pull_to_refresh_last_update_time_text);
        float pivotValue = 0.5f;
        float toDegree = -180f;
        // 初始化旋转动画
        mRotateUpAnim = new RotateAnimation(0.0f, toDegree,
                Animation.RELATIVE_TO_SELF, pivotValue, Animation.RELATIVE_TO_SELF, pivotValue);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(toDegree, 0.0f, Animation.RELATIVE_TO_SELF,
                pivotValue, Animation.RELATIVE_TO_SELF, pivotValue);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, null);
        return container;
    }

    @Override
    public int getContentSize() {
        if (null != mHeaderContainer) {
            return mHeaderContainer.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * 60);
    }

    @Override
    protected void onReset() {
        mArrowImageView.clearAnimation();
        mHintTextView.setText("下拉可以刷新");
    }

    @Override
    protected void onPullToRefresh() {
        if (State.RELEASE_TO_REFRESH == getPreState()){
            mArrowImageView.clearAnimation();
            mArrowImageView.startAnimation(mRotateDownAnim);
        }
    }

    @Override
    protected void onReleaseToRefresh() {
        mArrowImageView.clearAnimation();
        mArrowImageView.startAnimation(mRotateUpAnim);
        mHintTextView.setText("松开后刷新");
    }

    @Override
    protected void onRefreshing() {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(INVISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mHintTextView.setText("正在加载中");
    }


    @Override
    public void setLastUpdatedLabel(CharSequence label) {
        mHeaderTimeViewTitle.setVisibility(TextUtils.isEmpty(label) ? View.INVISIBLE : View.VISIBLE);
        mHeaderTimeView.setText(label);
    }

    @Override
    protected void onStateChanged(State curState, State mPreState) {
        mArrowImageView.setVisibility(VISIBLE);
        mProgressBar.setVisibility(INVISIBLE);
        super.onStateChanged(curState, mPreState);
    }
}

















