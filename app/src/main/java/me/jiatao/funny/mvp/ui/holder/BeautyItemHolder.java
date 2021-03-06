package me.jiatao.funny.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.widget.imageloader.ImageLoader;
import me.jessyan.art.widget.imageloader.glide.GlideImageConfig;
import me.jiatao.funny.R;
import me.jiatao.funny.app.WEApplication;
import me.jiatao.funny.mvp.model.entity.PhotoBeautyInfo;

/**
 * Created by JiaTao on 2017/3/28.
 */

public class BeautyItemHolder extends BaseHolder<PhotoBeautyInfo> {

    @Nullable
    @BindView(R.id.iv_avatar)
    ImageView mAvater;
    @Nullable
    @BindView(R.id.tv_name)
    TextView mName;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private final WEApplication mApplication;

    public BeautyItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (WEApplication) itemView.getContext().getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public void setData(PhotoBeautyInfo data, int position) {

        mName.setText(data.getTitle());

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .url(data.getImgsrc())
                .imageView(mAvater)
                .build());
    }


    @Override
    protected void onRelease() {
        mImageLoader.clear(mApplication,GlideImageConfig.builder()
                .imageViews(mAvater)
                .build());
    }
}