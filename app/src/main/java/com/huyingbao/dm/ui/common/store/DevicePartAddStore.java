package com.huyingbao.dm.ui.common.store;

import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.List;

/**
 * 维修详情模块
 * Created by liujunfeng on 2017/3/29.
 */
public class DevicePartAddStore extends BaseRxStore {
    private static DevicePartAddStore sInstance;
    private List<DevicePart> mDevicePartList;

    private DevicePartAddStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static DevicePartAddStore getInstance(Dispatcher dispatcher) {
        if (sInstance == null) sInstance = new DevicePartAddStore(dispatcher);
        return sInstance;
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_DEVICE_PART_LIST_BY_DEVICE_TYPE:
                mDevicePartList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public List<DevicePart> getDevicePartList() {
        return mDevicePartList;
    }
}
