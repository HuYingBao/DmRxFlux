package com.huyingbao.dm.ui.common;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.dialogfragment.BaseDialogFragment;
import com.huyingbao.rxflux2.constant.ActionsKeys;

import butterknife.BindView;

/**
 * 确认对话框
 * Created by liujunfeng on 2017/5/9.
 */
public class ShowInfoFragment extends BaseDialogFragment {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_info)
    TextView mTvInfo;

    public static ShowInfoFragment newInstance(String title, String info) {
        Bundle bundle = new Bundle();
        bundle.putString(ActionsKeys.TITLE, title);
        bundle.putString(ActionsKeys.INFO, info);
        ShowInfoFragment fragment = new ShowInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_show_info;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mTvInfo.setText(getArguments().getString(ActionsKeys.INFO));
        mTvTitle.setText(getArguments().getString(ActionsKeys.TITLE));
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = getResources().getDimensionPixelSize(R.dimen.dp_300);
        window.setAttributes(layoutParams);
    }
}
