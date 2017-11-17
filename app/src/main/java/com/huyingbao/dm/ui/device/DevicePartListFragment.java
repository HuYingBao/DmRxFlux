package com.huyingbao.dm.ui.device;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.device.adapter.DevicePartAdapter;
import com.huyingbao.dm.ui.device.store.DeviceDetailStore;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.dm.ui.maintenance.MaintenanceDetailActivity;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.repair.RepairDetailActivity;
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 备品备件使用记录列表
 * (1:设备中,2:保养记录中,3:维修记录中)
 * Created by liujunfeng on 2017/3/30.
 */
public class DevicePartListFragment extends BaseRxFluxListFragment<DevicePart> {
    @Inject
    DeviceDetailStore mDevicePartStore;

    private Device mDevice;

    /**
     * @param device 传入设备
     * @return
     */
    public static DevicePartListFragment newInstance(Device device) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.DEVICE, device);
        DevicePartListFragment fragment = new DevicePartListFragment();
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
        mAdapter = new DevicePartAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRvContent.addItemDecoration(ViewUtils.getItemDecoration(mContext));
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                DevicePart devicePart = mDataList.get(position);
                if (devicePart.isRepair()) {
                    Repair repair = new Repair();
                    repair.setId(devicePart.getTargetId());
                    repair.setMachineCode(mDevice.getCode());
                    repair.setMachineName(mDevice.getName());
                    startActivity(RepairDetailActivity.newIntent(mContext, repair, Actions.TO_REPAIR_DETAIL));
                } else {
                    Maintenance maintenance = new Maintenance();
                    maintenance.setId(devicePart.getTargetId());
                    maintenance.setMachineCode(mDevice.getCode());
                    maintenance.setMachineName(mDevice.getName());
                    startActivity(MaintenanceDetailActivity.newIntent(mContext, maintenance, Actions.TO_MAINTENANCE_DETAIL));
                }
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getDevicePartListByDevice(mDevice.getId(), index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_DEVICE_PART_LIST_BY_DEVICE:
                showDataList(mDevicePartStore.getDevicePartList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDevicePartStore);
    }
}
