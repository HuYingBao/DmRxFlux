package com.huyingbao.dm.ui.repair.store;

import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.ArrayList;
import java.util.List;

/**
 * 维修详情模块
 * Created by liujunfeng on 2017/3/29.
 */
public class RepairDetailStore extends BaseRxStore {
    private static RepairDetailStore sInstance;
    private Repair mRepair;
    private List<Repair> mRepairList;
    private List<DevicePart> mDeviceResultPartList = new ArrayList<>();

    private RepairDetailStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static RepairDetailStore getInstance(Dispatcher dispatcher) {
        if (sInstance == null) sInstance = new RepairDetailStore(dispatcher);
        return sInstance;
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_REPAIR_DETAIL:
            case Actions.GET_REPAIR_DETAIL_BY_DEVICE:
            case Actions.REPAIR_START:
            case Actions.REPAIR_RESTART:
            case Actions.REPAIR_PAUSE:
            case Actions.REPAIR_FINISH:
                mRepair = getRightResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_REPAIR_LIST_BY_USER:
                mRepairList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_DEVICE_PART_LIST_BY_REPAIR:
                mDeviceResultPartList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.REPAIR_APPROVE:
            case Actions.REPAIR_REJECT:
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public Repair getRepair() {
        return mRepair;
    }

    public List<Repair> getRepairList() {
        return mRepairList;
    }

    public List<DevicePart> getDeviceResultPartList() {
        return mDeviceResultPartList;
    }
}
