package me.jiatao.funny.mvp.ui.adapter;

import android.view.View;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;
import me.jiatao.funny.R;
import me.jiatao.funny.mvp.model.entity.PhotoBeautyInfo;
import me.jiatao.funny.mvp.ui.holder.BeautyItemHolder;


/**
 * Created by JiaTao on 2017/3/28.
 */

public class BeautyAdapter extends DefaultAdapter<PhotoBeautyInfo> {
    public BeautyAdapter(List<PhotoBeautyInfo> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<PhotoBeautyInfo> getHolder(View v,int viewType) {
        return new BeautyItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycle_list;
    }
}
