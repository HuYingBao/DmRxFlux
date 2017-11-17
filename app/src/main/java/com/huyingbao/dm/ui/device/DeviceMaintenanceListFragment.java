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
import com.huyingbao.dm.ui.device.adapter.DeviceMaintenanceAdapter;
import com.huyingbao.dm.ui.device.store.DeviceDetailStore;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.maintenance.MaintenanceDetailActivity;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.huyingbao.rxflux2.constant.Actions.GET_MAINTENANCE_LIST_BY_DEVICE;

/**
 * 保养记录列表
 * Created by liujunfeng on 2017/3/30.
 */
public class DeviceMaintenanceListFragment extends BaseRxFluxListFragment<Maintenance> {
    @Inject
    DeviceDetailStore mDeviceDetailStore;

    private Device mDevice;

    /**
     * @param device
     * @return
     */
    public static DeviceMaintenanceListFragment newInstance(Device device) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.DEVICE, device);
        DeviceMaintenanceListFragment fragment = new DeviceMaintenanceListFragment();
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
        mAdapter = new DeviceMaintenanceAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRvContent.addItemDecoration(ViewUtils.getItemDecoration(mContext));
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Maintenance maintenance = mDataList.get(position);
                maintenance.setMachineCode(mDevice.getCode());
                maintenance.setMachineName(mDevice.getName());
                startActivity(MaintenanceDetailActivity.newIntent(mContext, maintenance, Actions.TO_MAINTENANCE_DETAIL));
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getMaintenanceListByDevice(mDevice.getId(), index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case GET_MAINTENANCE_LIST_BY_DEVICE:
                showDataList(mDeviceDetailStore.getMaintenanceList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDeviceDetailStore);
    }
}
