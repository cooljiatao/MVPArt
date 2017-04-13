package me.jiatao.funny.mvp.presenter;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import me.jessyan.art.base.AdapterViewPager;
import me.jessyan.art.mvp.BasePresenter;
import me.jessyan.art.mvp.Message;
import me.jiatao.funny.mvp.ui.fragment.photo.BeautyListFragment;
import me.jiatao.funny.mvp.ui.fragment.photo.WelfareFragment;

/**
 * Created by JiaTao on 2017/4/12.
 */

public class FragmentMainPresenter extends BasePresenter {
    private Fragment mainFragment;

    public FragmentMainPresenter(Fragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public void setPhotoMainPresenter(Message msg) {
        AdapterViewPager mPagerAdapter = new AdapterViewPager(mainFragment.getChildFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new BeautyListFragment());
        fragments.add(new WelfareFragment());
        fragments.add(new BeautyListFragment());
        mPagerAdapter.setItems(fragments, new String[]{"美女", "福利", "生活"});

        msg.what=1;
        msg.obj=mPagerAdapter;
        msg.HandleMessageToTarget();
    }
}
