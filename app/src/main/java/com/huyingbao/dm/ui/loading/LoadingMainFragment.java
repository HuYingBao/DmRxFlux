package com.huyingbao.dm.ui.loading;

import android.os.Bundle;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.util.AppUtils;
import com.huyingbao.rxflux2.util.push.BaiduPushBase;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


/**
 * 引导界面
 * Created by liujunfeng on 2017/1/1.
 */
public class LoadingMainFragment extends BaseFragment {
    public static LoadingMainFragment getInstance() {
        return new LoadingMainFragment();
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_loading_main;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        //开启百度推送
        BaiduPushBase.start(AppUtils.getApplication());
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> mActionCreator.postLocalAction(Actions.TO_LOADING_NEXT));
    }
}
