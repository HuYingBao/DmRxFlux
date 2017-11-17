package com.huyingbao.dm.ui.devicelist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.dialogfragment.BaseDialogFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.dm.ui.device.adapter.DeviceTypeAdapter;
import com.huyingbao.dm.ui.devicelist.model.DeviceType;
import com.huyingbao.dm.ui.devicelist.store.DeviceListStore;
import com.huyingbao.rxflux2.util.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 筛选界面
 * Created by liujunfeng on 2017/6/6.
 */
public class DeviceFilterFragment extends BaseDialogFragment {
    @Inject
    DeviceListStore mDeviceListStore;

    @BindView(R.id.rv_device_filter_type)
    RecyclerView mRvDeviceFilterType;
    @BindView(R.id.rv_device_filter_status)
    RecyclerView mRvDeviceFilterStatus;

    private DeviceTypeAdapter mDeviceTypeAdapter;
    private DeviceTypeAdapter mDeviceStatusAdapter;

    public static DeviceFilterFragment newInstance() {
        DeviceFilterFragment fragment = new DeviceFilterFragment();
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_device_filter;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initAdapter();
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        window.setGravity(Gravity.RIGHT | Gravity.TOP);
        window.setWindowAnimations(R.style.RightAnimation);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.y = ((AppCompatActivity) getActivity()).getSupportActionBar().getHeight() + getResources().getDimensionPixelOffset(R.dimen.dp_40);
        layoutParams.width = getResources().getDimensionPixelSize(R.dimen.dp_300);
        window.setAttributes(layoutParams);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mDeviceTypeAdapter = new DeviceTypeAdapter(mDeviceListStore.getDeviceTypeList());
        mDeviceStatusAdapter = new DeviceTypeAdapter(mDeviceListStore.getDeviceStatusList());
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        mRvDeviceFilterType.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRvDeviceFilterType.setAdapter(mDeviceTypeAdapter);
        mRvDeviceFilterType.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                DeviceType deviceType = mDeviceListStore.getDeviceTypeList().get(position);
                deviceType.setSelected(!deviceType.isSelected());
                mDeviceTypeAdapter.notifyItemChanged(position);
            }
        });

        mRvDeviceFilterStatus.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRvDeviceFilterStatus.setAdapter(mDeviceStatusAdapter);
        mRvDeviceFilterStatus.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                DeviceType deviceType = mDeviceListStore.getDeviceStatusList().get(position);
                deviceType.setSelected(!deviceType.isSelected());
                mDeviceStatusAdapter.notifyItemChanged(position);
            }
        });
    }

    /**
     * 筛选重置
     */
    @OnClick(R.id.tv_device_filter_reset)
    public void filterReset() {
        if (CommonUtils.isListAble(mDeviceListStore.getDeviceStatusList()))
            for (DeviceType deviceType : mDeviceListStore.getDeviceStatusList())
                deviceType.setSelected(false);
        if (CommonUtils.isListAble(mDeviceListStore.getDeviceTypeList()))
            for (DeviceType deviceType : mDeviceListStore.getDeviceTypeList())
                deviceType.setSelected(false);
        mDeviceStatusAdapter.notifyDataSetChanged();
        mDeviceTypeAdapter.notifyDataSetChanged();
    }

    /**
     * 筛选确定
     */
    @OnClick(R.id.tv_device_filter_ok)
    public void filterOk() {
        mActionCreator.postLocalAction(Actions.TO_FILTER_DEVICE);
        dismiss();
    }
}
