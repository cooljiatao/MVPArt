package me.jiatao.funny.mvp.model;

import java.util.List;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import me.jessyan.art.mvp.IModel;
import me.jessyan.art.mvp.IRepositoryManager;
import me.jessyan.art.mvp.RepositoryManager;
import me.jiatao.funny.mvp.model.api.cache.PhotoCache;
import me.jiatao.funny.mvp.model.api.service.PhotoService;
import me.jiatao.funny.mvp.model.entity.PhotoBeautyInfo;
import me.jiatao.funny.mvp.model.entity.PhotoWelfareInfo;
import rx.Observable;

/**
 * 必须实现IModel
 * 可以根据不同的业务逻辑划分多个Repository类,多个业务逻辑相近的页面可以使用同一个Repository类
 * 无需每个页面都创建一个独立的Repository
 * 通过{@link RepositoryManager#createRepository(Class)}获得的Repository实例,为单例对象
 * <p>
 * Created by jess on 9/4/16 10:56
 * Contact with jess.yan.effort@gmail.com
 */
public class PhotoRepository implements IModel {

    private IRepositoryManager mManager;

    private PhotoService photoService;
    private PhotoCache photoCache;

    /**
     * 必须含有一个接收IRepositoryManager接口的构造函数,否则会报错
     *
     * @param manager
     */
    public PhotoRepository(IRepositoryManager manager) {
        this.mManager = manager;
        photoService = mManager.CreateRetrofitService(PhotoService.class);
        photoCache = mManager.CreateCacheService(PhotoCache.class);
    }


    public Observable<List<PhotoBeautyInfo>> getBeautyPhotoList(int offset, boolean update) {
        Observable<List<PhotoBeautyInfo>> beautyPhotos = photoService.getBeautyPhoto(offset)
                .map(stringListMap -> stringListMap.get("美女"));

        return photoCache
                .getBeautyPhoto(beautyPhotos, new DynamicKey(offset), new EvictDynamicKey(update));
    }


    public Observable getWelfarePhotoList(int page, boolean update) {
        Observable<List<PhotoWelfareInfo>> photoWelfareObs = photoService.getWelfarePhoto(page)
                .map(photoWelfareList -> photoWelfareList.getResults());

        return photoCache.getWelfarePhoto(photoWelfareObs, new DynamicKey(page), new EvictDynamicKey(update));

    }


    @Override
    public void onDestory() {

    }
}
