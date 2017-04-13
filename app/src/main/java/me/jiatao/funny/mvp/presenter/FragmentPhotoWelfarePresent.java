package me.jiatao.funny.mvp.presenter;

import android.os.Environment;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.jessyan.art.base.DefaultAdapter;
import me.jessyan.art.di.component.AppComponent;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.art.utils.PermissionUtil;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.jiatao.funny.mvp.model.PhotoRepository;
import me.jiatao.funny.mvp.model.entity.PhotoWelfareInfo;
import me.jiatao.funny.mvp.ui.adapter.WelfareAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JiaTao on 2017/4/13.
 */

public class FragmentPhotoWelfarePresent extends BasePresenter {
    private RxErrorHandler mErrorHandler;
    private List<PhotoWelfareInfo> mWelfarePhotos = new ArrayList<>();
    private PhotoRepository mModel;
    private DefaultAdapter<PhotoWelfareInfo> mAdapter;
    private int lastPage = 1;
    private boolean isFirst = true;
    private int preEndIndex;


    public FragmentPhotoWelfarePresent(AppComponent appComponent) {
        super();//记得这个super()必须加上,里面做一些初始化工作
        this.mErrorHandler = appComponent.rxErrorHandler();
        this.mModel = appComponent.repositoryManager().createRepository(PhotoRepository.class);
    }

    public void requestPhotoWelfare(final Message msg) {
        IView mRootView = msg.getTarget();
        final boolean pullToRefresh = (boolean) msg.objs[0];
        RxPermissions mRxPermissions = (RxPermissions) msg.objs[1];

        if (mAdapter == null) {
            mAdapter = new WelfareAdapter(mWelfarePhotos);
            msg.what = 0;
            msg.obj = mAdapter;
            msg.HandleMessageToTargetUnrecycle();
        }

        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(() -> {
            File cacheFile = new File(Environment.getExternalStorageDirectory() + "/1Postime");
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
        }, mRxPermissions, mRootView, mErrorHandler);

        //如果刷新从第一条数据获取
        if (pullToRefresh) {
            lastPage = 1;
        }

        boolean isEvictCache = pullToRefresh;//是否驱逐缓存,为ture即不使用缓存,每次上拉刷新即需要最新数据,则不使用缓存
        if (pullToRefresh && isFirst) {//默认在第一次上拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }

        addSubscrebe(mModel.getWelfarePhotoList(lastPage, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(() -> {
                    if (pullToRefresh)
                        msg.getTarget().showLoading();//显示上拉刷新的进度条
                    else {
                        //显示下拉加载更多的进度条
                        msg.what = 1;
                        msg.HandleMessageToTargetUnrecycle();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (pullToRefresh) {
                        msg.getTarget().hideLoading();//隐藏上拉刷新的进度条
                        //因为hideLoading,为默认方法,直接可以调用所以不需要发送消息给handleMessage()来处理,
                        //HandleMessageToTarget()的原理就是发送消息并回收消息
                        //调用默认方法后不需要调用HandleMessageToTarget(),但是如果后面对view没有其他操作了请调用message.recycle()回收消息
                        msg.recycle();
                    } else {
                        //隐藏下拉加载更多的进度条
                        msg.what = 2;
                        msg.HandleMessageToTarget();//方法最后必须调HandleMessageToTarget,将消息所有引用清空后回收进消息池
                    }
                })
                .subscribe(new ErrorHandleSubscriber<List<PhotoWelfareInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(List<PhotoWelfareInfo> photoBeautyInfos) {
                        ++lastPage;

                        if (pullToRefresh) mWelfarePhotos.clear();//如果是上拉刷新则清空列表

                        preEndIndex = mWelfarePhotos.size();//更新之前列表总长度,用于确定加载更多的起始位置
                        mWelfarePhotos.addAll(photoBeautyInfos);

                        if (pullToRefresh)
                            mAdapter.notifyDataSetChanged();
                        else
                            mAdapter.notifyItemRangeInserted(preEndIndex, photoBeautyInfos.size());
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mWelfarePhotos = null;
        this.mErrorHandler = null;
    }
}
