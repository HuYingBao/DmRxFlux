package com.huyingbao.dm.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.WindowManager;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxActivity;
import com.huyingbao.dm.ui.login.store.LoginStore;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 登录
 * Created by liujunfeng on 2017/11/7.
 */
public class LoginActivity extends BaseRxFluxActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    LoginStore mLoginStore;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_base;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        // 隐藏布局文件中AppbarLayout
        findViewById(R.id.abl_top).setVisibility(View.GONE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //到登录页面
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, LoginFragment.newInstance(), LoginFragment.class.getName())
                .commit();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mLoginStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mLoginStore);
    }
}
