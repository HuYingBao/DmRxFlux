package com.huyingbao.dm.ui.loading;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActivity;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.service.DmService;
import com.huyingbao.dm.ui.login.LoginActivity;
import com.huyingbao.dm.ui.main.MainActivity;
import com.huyingbao.rxflux2.util.ServiceUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.List;

/**
 * 引导界面
 * Created by liujunfeng on 2017/1/1.
 */
public class LoadingActivity extends BaseRxFluxToolbarActivity {
    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mAppBarLayoutTop.setVisibility(View.GONE);
        toLoadingMain();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.TO_LOADING_NEXT:
                //TODO 隐藏引导页面
                if (mLocalStorageUtils.getBoolean(ActionsKeys.IS_FIRST, false)) {
                    toLoadingGuide();
                    break;
                }
                //没有登录或者没有设置自动登录,跳转登录页面
                if (!mLocalStorageUtils.getBoolean(ActionsKeys.IS_LOGIN, false) || !mLocalStorageUtils.getBoolean(ActionsKeys.IS_LOGIN_AUTO, false)) {
                    startActivity(LoginActivity.newIntent(mContext));
                    finish();
                    break;
                }
                //若是服务未启动,开启定时器
                if (!ServiceUtils.isServiceRun(mContext, DmService.class.getName()))
                    ServiceUtils.startTimerCheck(mContext);
                //跳转到主页面
                startActivity(MainActivity.newIntent(mContext));
                finish();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }

    /**
     * 到引导主页面
     */
    private void toLoadingMain() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, LoadingMainFragment.getInstance()).commit();
    }

    /**
     * 到引导信息页面
     */
    private void toLoadingGuide() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, LoadingGuideFragment.getInstance()).commit();
    }
}
