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
import com.huyingbao.dm.ui.device.adapter.DeviceInspectionAdapter;
import com.huyingbao.dm.ui.device.store.DeviceDetailStore;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.inspection.InspectionDetailActivity;
import com.huyingbao.dm.ui.inspection.model.Inspection;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 巡检列表
 * Created by liujunfeng on 2017/3/30.
 */
public class DeviceInspectionListFragment extends BaseRxFluxListFragment<Inspection> {
    @Inject
    DeviceDetailStore mDeviceDetailStore;

    private Device mDevice;

    /**
     * @param device
     * @return
     */
    public static DeviceInspectionListFragment newInstance(Device device) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.DEVICE, device);
        DeviceInspectionListFragment fragment = new DeviceInspectionListFragment();
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
        mAdapter = new DeviceInspectionAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRvContent.addItemDecoration(ViewUtils.getItemDecoration(mContext));
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Inspection inspection = mDataList.get(position);
                inspection.setMachineCode(mDevice.getCode());
                inspection.setMachineName(mDevice.getName());
                startActivity(InspectionDetailActivity.newIntent(mContext, inspection));
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getInspectionListByDevice(mDevice.getId(), index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_INSPECTION_LIST_BY_DEVICE:
                showDataList(mDeviceDetailStore.getInspectionList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDeviceDetailStore);
    }
}
