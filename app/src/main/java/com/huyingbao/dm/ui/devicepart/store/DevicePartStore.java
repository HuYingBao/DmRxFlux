package com.huyingbao.dm.ui.devicepart.store;

import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.List;

/**
 * 备品备件模块
 * Created by liujunfeng on 2017/3/29.
 */
public class DevicePartStore extends BaseRxStore {
    private List<DevicePart> mDevicePartList;

    public DevicePartStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_DEVICE_PART_LIST:
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
