package me.jessyan.art.di.component;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import me.jessyan.art.base.AppManager;
import me.jessyan.art.base.BaseApplication;
import me.jessyan.art.di.module.AppModule;
import me.jessyan.art.di.module.ClientModule;
import me.jessyan.art.di.module.GlobeConfigModule;
import me.jessyan.art.di.module.ImageModule;
import me.jessyan.art.mvp.IRepositoryManager;
import me.jessyan.art.widget.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;

/**
 * Created by jess on 8/4/16.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, ImageModule.class, GlobeConfigModule.class})
public interface AppComponent {
    Application Application();

    //Rxjava错误处理管理类
    RxErrorHandler rxErrorHandler();


    OkHttpClient okHttpClient();

    //图片管理器,用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    ImageLoader imageLoader();

    //gson
    Gson gson();

    //用于管理所有activity
    AppManager appManager();

    //用于管理所有仓库,网络请求层,以及数据缓存层
    IRepositoryManager repositoryManager();

    void inject(BaseApplication application);
}
