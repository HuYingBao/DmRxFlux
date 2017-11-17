package com.huyingbao.rxflux2.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.constant.Constants;

import butterknife.BindView;


/**
 * 带有toolbar的Activity父类
 * Created by liujunfeng on 2017/1/1.
 */
public abstract class BaseRxFluxToolbarActivity extends BaseRxFluxActivity {
    @BindView(R.id.tv_top_title)
    protected TextView mTvTopTitle;
    @BindView(R.id.tlb_top)
    protected Toolbar mToolbarTop;
    @BindView(R.id.abl_top)
    protected AppBarLayout mAppBarLayoutTop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_base;
    }

    @Override
    public void setTitle(CharSequence title) {
        // 设置标题
        mTvTopTitle.setText(title == null ? getTitle() : title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu mainImage = menu.addSubMenu("扫描").setIcon(R.drawable.ic_v_action_scan);
        mainImage.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mainImage.getItem().setOnMenuItemClickListener(item -> {
            new IntentIntegrator(this).initiateScan();
            return false;
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || requestCode != Constants.GET_DEVICE_DETAIL_BY_CODE)
            return;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result == null || TextUtils.isEmpty(result.getContents())) {
            showShortToast("取消扫描");
            return;
        }
        mActionCreator.getDeviceDetailByCode(mContext, result.getContents());
    }

    /**
     * 获取fragment事务,先隐藏已经存在的fragment
     *
     * @return
     */
    protected FragmentTransaction getFragmentTransaction(@IdRes int viewId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentById(viewId);
        if (fragment != null)
            fragmentTransaction.addToBackStack(fragment.getClass().getName()).hide(fragment);
        return fragmentTransaction;
    }

    /**
     * 设置toolbar
     *
     * @param backAble 是否有回退按钮
     */
    private void setToolbar(boolean backAble) {
        //取代原本的actionbar
        setSupportActionBar(mToolbarTop);
        //设置actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        //显示右侧返回图标
        if (backAble) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_v_action_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //不显示home图标
        actionBar.setDisplayShowHomeEnabled(false);
        //不显示标题
        actionBar.setDisplayShowTitleEnabled(false);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        //App Logo
        mToolbarTop.setLogo(R.mipmap.ic_logo);
        //主标题,默认为app_label的名字
        mToolbarTop.setTitle(null);
        //设置颜色
        mToolbarTop.setTitleTextColor(Color.YELLOW);
        //副标题
        mToolbarTop.setSubtitle(null);
        //设置颜色
        mToolbarTop.setSubtitleTextColor(Color.parseColor("#80FF0000"));
        //侧边栏的按钮
        mToolbarTop.setNavigationIcon(R.drawable.ic_v_action_back);
    }

    /**
     * @param title    actionbar 的title
     * @param backAble 是否显示可返回图标
     */
    public void initActionBar(String title, boolean backAble) {
        //设置标题
        setTitle(title);
        // 设置toolbar
        setToolbar(backAble);
    }

    /**
     * 默认有返回按钮
     *
     * @param title toolbar的title
     */
    public void initActionBar(String title) {
        this.initActionBar(title, true);
    }

    /**
     * 默认有返回按钮,默认使用manifest中label作为的title
     */
    public void initActionBar() {
        this.initActionBar(null, true);
    }
}
