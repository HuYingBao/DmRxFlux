package com.huyingbao.dm.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActivity;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.login.LoginActivity;
import com.huyingbao.dm.ui.main.store.MainStore;
import com.huyingbao.dm.ui.message.MessageDataActivity;
import com.huyingbao.rxflux2.RxFlux;
import com.huyingbao.rxflux2.action.RxError;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;
import com.taobao.sophix.SophixManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * 主页面
 * Created by liujunfeng on 2017/1/1.
 */
public class MainActivity extends BaseRxFluxToolbarActivity implements View.OnClickListener {
    @Inject
    RxFlux mRxFlux;
    @Inject
    MainStore mMainStore;
    @Inject
    RxPermissions mRxPermissions;

    @BindView(R.id.nav_main)
    NavigationView mNavMain;
    @BindView(R.id.drl_main)
    DrawerLayout mDrlMain;

    private TextView mTvMenuUserName;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        //初始化标题
        initActionBar(getString(R.string.title_main), false);
        initDrawerLayout();
        initNavView();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, MainFragment.newInstance())
                .commit();
        //版本更新
        checkUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNavData();
        //设置扫描返回
        IntentIntegrator.REQUEST_CODE = Constants.GET_DEVICE_DETAIL_BY_CODE;
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //解除版本更新注册
//        //PgyUpdateManager.unregister();
//    }

    @Override
    public void onBackPressed() {
        //回退键先关闭左侧导航view
        if (mDrlMain.isDrawerOpen(GravityCompat.START)) {
            mDrlMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK)
            return super.onKeyDown(keyCode, event);
        //回退键先关闭左侧导航view
        if (mDrlMain.isDrawerOpen(GravityCompat.START)) {
            mDrlMain.closeDrawer(GravityCompat.START);
            return true;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        return true;
    }


    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.LOGOUT:
                startActivity(LoginActivity.newIntent(mContext));
                finish();
                break;
        }
    }

    @Override
    public void onRxError(@NonNull RxError error) {
        super.onRxError(error);
        switch (error.getAction().getType()) {
            case Actions.LOGOUT:
                mMainStore.handleLogout();
                startActivity(LoginActivity.newIntent(mContext));
                finish();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMainStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mMainStore);
    }

    /**
     * 初始化侧滑控件
     */
    private void initDrawerLayout() {
        //侧滑控件
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrlMain, mToolbarTop, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrlMain.addDrawerListener(toggle);
        toggle.syncState();
        //自定义NavigationIcon，没有旋转特效
        //toggle.setDrawerIndicatorEnabled(false);
        //mToolbarTop.setNavigationIcon(R.drawable.ic_login_name);
        //mToolbarTop.setNavigationOnClickListener(v -> mDrlMain.openDrawer(GravityCompat.START));
    }

    /**
     * 初始化左侧导航显示数据
     */
    private void initNavView() {
        View headerView = mNavMain.getHeaderView(mNavMain.getHeaderCount() - 1);
        mTvMenuUserName = headerView.findViewById(R.id.tv_menu_user_name);
        headerView.findViewById(R.id.tv_menu_message).setOnClickListener(this);
        headerView.findViewById(R.id.tv_menu_exit).setOnClickListener(this);
        headerView.findViewById(R.id.tv_menu_logout).setOnClickListener(this);
    }

    /**
     * 初始化右侧菜单展示数据
     */
    private void initNavData() {
        String alias = mLocalStorageUtils.getUser().getAlias();
        String name = mLocalStorageUtils.getUser().getName();
        mTvMenuUserName.setText(TextUtils.isEmpty(alias) ? name : alias);
    }

    /**
     * 检测更新
     */
    private void checkUpdate() {
        //请求存储权限
        mRxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_WIFI_STATE)
                .subscribe(granted -> {
                    if (granted) {
                        //查询补丁包
                        SophixManager.getInstance().queryAndLoadNewPatch();
                        //检测更新
                        //PgyUpdateManager.register(this, Constants.PROVIDER_NAME);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        mDrlMain.closeDrawer(GravityCompat.START);
        switch (v.getId()) {
            case R.id.tv_menu_message:
                startActivity(MessageDataActivity.newIntent(mContext));
                break;
            case R.id.tv_menu_exit:
                mRxFlux.finishAllActivity();
                break;
            case R.id.tv_menu_logout:
                mActionCreator.logout(mContext);
                break;
        }
    }
}
