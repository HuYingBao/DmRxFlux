package com.huyingbao.dm.ui.devicelist.store;

import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.devicelist.model.DeviceType;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;
import com.huyingbao.rxflux2.util.AppUtils;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.LocalStorageUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 设备列表模块
 * Created by liujunfeng on 2017/3/29.
 */
public class DeviceListStore extends BaseRxStore {
    @Inject
    LocalStorageUtils mLocalStorageUtils;

    private List<Device> mDeviceList;
    private List<DeviceType> mDeviceTypeList;
    private List<DeviceType> mDeviceStatusList;

    public DeviceListStore(Dispatcher dispatcher) {
        super(dispatcher);
        AppUtils.getApplicationComponent().inject(this);
        mDeviceTypeList = mLocalStorageUtils.getList(ActionsKeys.ARRAY_TYPE, DeviceType.class);
        mDeviceStatusList = CommonUtils.getDeviceStatusList();
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_DEVICE_LIST:
                mDeviceList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public List<Device> getDeviceList() {
        return mDeviceList;
    }

    public List<DeviceType> getDeviceTypeList() {
        return mDeviceTypeList;
    }

    public List<DeviceType> getDeviceStatusList() {
        return mDeviceStatusList;
    }
}
