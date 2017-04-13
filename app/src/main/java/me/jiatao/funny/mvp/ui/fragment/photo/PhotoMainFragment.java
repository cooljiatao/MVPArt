package me.jiatao.funny.mvp.ui.fragment.photo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import me.jessyan.art.base.AdapterViewPager;
import me.jessyan.art.base.BaseFragment;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jiatao.funny.R;
import me.jiatao.funny.mvp.presenter.FragmentMainPresenter;

/**
 * Created by JiaTao on 2017/4/12.
 */

public class PhotoMainFragment extends BaseFragment<FragmentMainPresenter> implements IView {
    @BindView(R.id.fragment_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.fragment_view_pager)
    ViewPager mViewPager;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_photo_main, container, false);
    }

    @Override
    protected void initData() {
        mViewPager.setOffscreenPageLimit(3);
        mPresenter.setPhotoMainPresenter(Message.obtain(this));
    }

    @Override
    protected FragmentMainPresenter getPresenter() {
        return new FragmentMainPresenter(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleMessage(Message message) {
        if (message.what==1){
            mViewPager.setAdapter((AdapterViewPager)message.obj);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }
}
