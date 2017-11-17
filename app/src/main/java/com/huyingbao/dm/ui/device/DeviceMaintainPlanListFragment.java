package com.huyingbao.dm.ui.device;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.device.adapter.DeviceMaintainPlanAdapter;
import com.huyingbao.dm.ui.device.store.DeviceDetailStore;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.huyingbao.rxflux2.constant.Actions.GET_MAINTAIN_PLAN_LIST_BY_DEVICE;

/**
 * 保养计划列表
 * Created by liujunfeng on 2017/3/30.
 */
public class DeviceMaintainPlanListFragment extends BaseRxFluxListFragment<MultiItemEntity> {
    @Inject
    DeviceDetailStore mDeviceDetailStore;

    private Device mDevice;

    /**
     * @param device 设备
     * @return
     */
    public static DeviceMaintainPlanListFragment newInstance(Device device) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.DEVICE, device);
        DeviceMaintainPlanListFragment fragment = new DeviceMaintainPlanListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mDevice = getArguments().getParcelable(ActionsKeys.DEVICE);
        super.afterCreate(savedInstanceState);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new DeviceMaintainPlanAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        ViewUtils.setViewMargin(mRvContent, true, 10, 10, 10, 10);
    }

    @Override
    protected void getDataList(int index) {
        isRefresh = true;
        mActionCreator.getMaintainPlanListByDevice(mDevice.getId());
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case GET_MAINTAIN_PLAN_LIST_BY_DEVICE:
                showDataList(mDeviceDetailStore.getMaintainPlanList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDeviceDetailStore);
    }
}
