package com.huyingbao.dm.ui.device.store;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.ui.check.model.Check;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.dm.ui.inspection.model.Inspection;
import com.huyingbao.dm.ui.maintenance.model.MaintainItem;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;
import com.huyingbao.rxflux2.util.CommonUtils;

import java.util.Collections;
import java.util.List;

/**
 * 设备详情模块
 * Created by liujunfeng on 2017/3/29.
 */
public class DeviceDetailStore extends BaseRxStore {
    private static DeviceDetailStore sInstance;
    private Device mDevice;
    private List<Repair> mRepairList;
    private List<Maintenance> mMaintenanceList;
    private List<Inspection> mInspectionList;
    private List<Check> mCheckList;
    private List<MaintainItem> mMaintainItemList;
    private List<MultiItemEntity> mMaintainPlanList;
    private List<DevicePart> mDevicePartList;

    private DeviceDetailStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static DeviceDetailStore getInstance(Dispatcher dispatcher) {
        if (sInstance == null) sInstance = new DeviceDetailStore(dispatcher);
        return sInstance;
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_DEVICE_DETAIL:
                mDevice = getRightResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_REPAIR_LIST_BY_DEVICE:
                mRepairList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_MAINTENANCE_LIST_BY_DEVICE:
                mMaintenanceList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_MAINTAIN_PLAN_LIST_BY_DEVICE:
                mMaintainPlanList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_INSPECTION_LIST_BY_DEVICE:
                mInspectionList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_DEVICE_PART_LIST_BY_DEVICE:
                mDevicePartList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public Device getDevice() {
        return mDevice;
    }

    public List<Repair> getRepairList() {
        return mRepairList;
    }

    public List<Maintenance> getMaintenanceList() {
        return mMaintenanceList;
    }

    public List<Inspection> getInspectionList() {
        return mInspectionList;
    }

    public List<Check> getCheckList() {
        return mCheckList;
    }

    public List<MaintainItem> getMaintainItemList() {
        return mMaintainItemList;
    }

    public List<MultiItemEntity> getMaintainPlanList() {
        if (CommonUtils.isListAble(mMaintainPlanList))
            Collections.reverse(mMaintainPlanList);
        return mMaintainPlanList;
    }

    public List<DevicePart> getDevicePartList() {
        return mDevicePartList;
    }

}
