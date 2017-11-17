package com.huyingbao.dm.ui.checkdevice.store;

import com.huyingbao.dm.ui.checkdevice.model.CheckDevice;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.List;

/**
 * 验收模块
 * Created by liujunfeng on 2017/3/29.
 */
public class CheckDeviceStore extends BaseRxStore {
    private static CheckDeviceStore sInstance;
    private List<CheckDevice> mCheckDeviceList;

    private CheckDeviceStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static CheckDeviceStore getInstance(Dispatcher dispatcher) {
        if (sInstance == null) sInstance = new CheckDeviceStore(dispatcher);
        return sInstance;
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_CHECK_LIST_BY_USER:
                mCheckDeviceList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.REPAIR_APPROVE:
            case Actions.REPAIR_REJECT:

            case Actions.MAINTAIN_APPROVE:
            case Actions.MAINTAIN_REJECT:
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public List<CheckDevice> getCheckDeviceList() {
        return mCheckDeviceList;
    }
}
