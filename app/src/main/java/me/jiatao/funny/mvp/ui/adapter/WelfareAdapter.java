package me.jiatao.funny.mvp.ui.adapter;

import android.view.View;

import java.util.List;

import me.jessyan.art.base.BaseHolder;
import me.jessyan.art.base.DefaultAdapter;
import me.jiatao.funny.R;
import me.jiatao.funny.mvp.model.entity.PhotoWelfareInfo;
import me.jiatao.funny.mvp.ui.holder.WelfareItemHolder;


/**
 * Created by JiaTao on 2017/3/28.
 */

public class WelfareAdapter extends DefaultAdapter<PhotoWelfareInfo> {
    public WelfareAdapter(List<PhotoWelfareInfo> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<PhotoWelfareInfo> getHolder(View v,int viewType) {
        return new WelfareItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.recycle_list;
    }
}
