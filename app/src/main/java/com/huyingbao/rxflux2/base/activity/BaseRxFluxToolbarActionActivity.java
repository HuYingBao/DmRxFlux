package com.huyingbao.rxflux2.base.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huyingbao.dm.R;

import butterknife.BindView;


/**
 * 带有toolbar的Activity父类
 * Created by liujunfeng on 2017/1/1.
 */
public abstract class BaseRxFluxToolbarActionActivity extends BaseRxFluxToolbarActivity {
    @BindView(R.id.ll_bottom)
    protected LinearLayout mLlBottom;
    @BindView(R.id.ll_bottom_1)
    protected LinearLayout mLlBottom1;
    @BindView(R.id.tv_bottom_action_1)
    protected TextView mTvBottomAction1;
    @BindView(R.id.ll_bottom_2)
    protected LinearLayout mLlBottom2;
    @BindView(R.id.tv_bottom_action_2)
    protected TextView mTvBottomAction2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_action_base;
    }

    /**
     * 隐藏view
     */
    public void invisibilityBottomView() {
        mLlBottom.setVisibility(View.GONE);
    }

    /**
     * 初始化底部view
     *
     * @param action     需要发出的action
     * @param name       标题
     * @param img        图标
     * @param background
     * @param data
     */
    public void initBottomView1(@NonNull String action, @StringRes int name, @DrawableRes int img, @DrawableRes int background, @NonNull Object... data) {
        mLlBottom.setVisibility(View.VISIBLE);
        mLlBottom2.setVisibility(View.GONE);

        Drawable drawable = getResources().getDrawable(img);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvBottomAction1.setCompoundDrawables(drawable, null, null, null);
        }

        mTvBottomAction1.setText(getString(name));
        mLlBottom1.setBackgroundResource(background);
        mLlBottom1.setOnClickListener(v -> mActionCreator.postLocalAction(action, data));
    }

    /**
     * 初始化底部view
     *
     * @param action     需要发出的action
     * @param name       标题
     * @param img        图标
     * @param background
     */
    public void initBottomView2(@NonNull String action, @StringRes int name, @DrawableRes int img, @DrawableRes int background) {
        mLlBottom.setVisibility(View.VISIBLE);
        mLlBottom2.setVisibility(View.VISIBLE);

        Drawable drawable = getResources().getDrawable(img);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvBottomAction2.setCompoundDrawables(drawable, null, null, null);
        }

        mTvBottomAction2.setText(getString(name));
        mLlBottom2.setBackgroundResource(background);
        mLlBottom2.setOnClickListener(v -> mActionCreator.postLocalAction(action));
    }
}
