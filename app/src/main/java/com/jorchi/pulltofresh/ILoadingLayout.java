package com.jorchi.pulltofresh;

/**
 * 下拉刷新和上拉加载更多的界面接口
 *
 *
 */

public interface ILoadingLayout {
    enum State{
        NONE,
        RESET,
        PULL_TO_REFRESH,
        RELEASE_TO_REFRESH,
        REFRESHING,
        LOADING,
        NO_MORE_DATA,
    }

    void setState(State state);

    State getState();

    int getContentSize();

    void onPull(float scale);
}
