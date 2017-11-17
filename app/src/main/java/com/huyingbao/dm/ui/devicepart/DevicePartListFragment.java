package com.huyingbao.dm.ui.devicepart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.devicepart.adapter.DevicePartRecordAdapter;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.dm.ui.devicepart.store.DevicePartStore;
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
    DevicePartStore mDevicePartStore;

    private int mId;

    /**
     * @param id
     * @param httpAction
     * @return
     */
    public static DevicePartListFragment newInstance(int id, @NonNull String httpAction) {
        Bundle bundle = new Bundle();
        bundle.putInt(ActionsKeys.ID, id);
        bundle.putString(ActionsKeys.BASE_ACTION, httpAction);
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
        mId = getArguments().getInt(ActionsKeys.ID);
        initActionBar("备品备件使用记录");
        super.afterCreate(savedInstanceState);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new DevicePartRecordAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        ViewUtils.setViewMargin(mRvContent, true, 10, 10, 10, 10);
    }

    @Override
    protected void getDataList(int index) {
        String actionType = getArguments().getString(ActionsKeys.BASE_ACTION);
        if (TextUtils.isEmpty(actionType)) return;
        mActionCreator.getDevicePartList(actionType, mId, index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_DEVICE_PART_LIST:
                showDataList(mDevicePartStore.getDevicePartList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDevicePartStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mDevicePartStore);
    }
}
