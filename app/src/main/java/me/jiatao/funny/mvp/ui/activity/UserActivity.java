package me.jiatao.funny.mvp.ui.activity;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paginate.Paginate;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import me.jiatao.funny.app.WEApplication;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.UiUtils;
import me.jiatao.funny.R;
import me.jiatao.funny.mvp.presenter.UserPresenter;


public class UserActivity extends BaseActivity<UserPresenter> implements IView, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private RxPermissions mRxPermissions;


    @Override
    protected int initView() {
        return R.layout.activity_user;
    }

    @Override
    protected void initData() {
        this.mRxPermissions = new RxPermissions(this);
        initRecycleView();
        mPresenter.requestUsers(Message.obtain(this, new Object[]{true, mRxPermissions}));//打开app时自动加载列表
    }

    @Override
    protected UserPresenter getPresenter() {
        return new UserPresenter(((WEApplication) getApplication()).getAppComponent());
    }


    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        UiUtils.SnackbarText(message);
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                mRecyclerView.setAdapter((RecyclerView.Adapter) message.obj);
                initPaginate();
                break;
            case 1:
                isLoadingMore = true;//开始加载更多
                break;
            case 2:
                isLoadingMore = false;//结束加载更多
                break;
        }
    }


    @Override
    public void onRefresh() {
        mPresenter.requestUsers(Message.obtain(this, new Object[]{true, mRxPermissions}));
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new GridLayoutManager(this, 2));
    }


    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.requestUsers(Message.obtain(UserActivity.this, new Object[]{false, mRxPermissions}));
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
        this.mPaginate = null;
    }
}
