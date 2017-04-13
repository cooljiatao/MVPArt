package me.jiatao.funny.mvp.model.api.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import me.jiatao.funny.mvp.model.entity.PhotoBeautyInfo;
import me.jiatao.funny.mvp.model.entity.PhotoWelfareInfo;
import rx.Observable;

/**
 * Created by JiaTao on 2017/4/12.
 */

public interface PhotoCache {
    /**
     * 美女图片
     *
     * @param beautyPhoto
     * @param idLastUserQueried
     * @param evictProvider
     * @return
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<List<PhotoBeautyInfo>> getBeautyPhoto(Observable<List<PhotoBeautyInfo>> beautyPhoto
            , DynamicKey idLastUserQueried, EvictProvider evictProvider);


    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<List<PhotoWelfareInfo>> getWelfarePhoto(Observable<List<PhotoWelfareInfo>> photoWelfare
                                ,DynamicKey lastPage, EvictProvider evictProvider);
}
