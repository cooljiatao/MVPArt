package me.jiatao.funny.mvp.model.api.service;

import java.util.List;
import java.util.Map;

import me.jiatao.funny.mvp.model.entity.PhotoBeautyInfo;
import me.jiatao.funny.mvp.model.entity.PhotoWelfareList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by JiaTao on 2017/4/12.
 */

public interface PhotoService {
    /**
     * 获取美女图片，这个API不完整，省略了好多参数
     * eg: http://c.3g.163.com/recommend/getChanListNews?channel=T1456112189138&size=20&offset=0
     *
     * @param offset 起始页码
     * @return
     */
    @GET("http://c.3g.163.com/recommend/getChanListNews?channel=T1456112189138&size=20")
    Observable<Map<String, List<PhotoBeautyInfo>>> getBeautyPhoto(@Query("offset") int offset);


    /**
     * 获取福利图片
     * eg: http://gank.io/api/data/福利/10/1
     *
     * @param page 页码
     * @return
     */
    @GET("http://gank.io/api/data/福利/10/{page}")
    Observable<PhotoWelfareList> getWelfarePhoto(@Path("page") int page);


}
