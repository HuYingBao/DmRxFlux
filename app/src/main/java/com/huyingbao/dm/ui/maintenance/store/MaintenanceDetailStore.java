package com.huyingbao.dm.ui.maintenance.store;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.ArrayList;
import java.util.List;

/**
 * 保养详情模块
 * Created by liujunfeng on 2017/3/29.
 */
public class MaintenanceDetailStore extends BaseRxStore {
    private static MaintenanceDetailStore sInstance;
    private Maintenance mMaintenance;
    private List<Maintenance> mMaintenanceList;
    private List<MultiItemEntity> mMaintainItemTitleList;
    private List<DevicePart> mDeviceResultPartList = new ArrayList<>();

    private MaintenanceDetailStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static MaintenanceDetailStore getInstance(Dispatcher dispatcher) {
        if (sInstance == null) sInstance = new MaintenanceDetailStore(dispatcher);
        return sInstance;
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_MAINTENANCE_DETAIL:
            case Actions.GET_MAINTENANCE_DETAIL_BY_DEVICE:
            case Actions.MAINTAIN_START:
            case Actions.MAINTAIN_RESTART:
            case Actions.MAINTAIN_PAUSE:
            case Actions.MAINTAIN_FINISH:
                mMaintenance = getRightResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_MAINTENANCE_LIST_BY_USER:
                mMaintenanceList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_DEVICE_PART_LIST_BY_MAINTAIN:
                mDeviceResultPartList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_MAINTAIN_ITEM_LIST_BY_MAINTAIN:
                mMaintainItemTitleList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.MAINTAIN_APPROVE:
            case Actions.MAINTAIN_REJECT:
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public Maintenance getMaintenance() {
        return mMaintenance;
    }

    public List<Maintenance> getMaintenanceList() {
        return mMaintenanceList;
    }

    public List<DevicePart> getDeviceResultPartList() {
        return mDeviceResultPartList;
    }

    public List<MultiItemEntity> getMaintainItemTitleList() {
        return mMaintainItemTitleList;
    }
}
