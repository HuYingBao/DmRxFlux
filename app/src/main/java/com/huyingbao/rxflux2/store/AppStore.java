package com.huyingbao.rxflux2.store;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.device.DeviceDetailActivity;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.image.ShowImgActivity;
import com.huyingbao.dm.ui.inspection.InspectionDetailActivity;
import com.huyingbao.dm.ui.login.LoginActivity;
import com.huyingbao.dm.ui.maintenance.MaintenanceDetailActivity;
import com.huyingbao.dm.ui.repair.RepairDetailActivity;
import com.huyingbao.rxflux2.util.AppUtils;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.orhanobut.logger.Logger;

/**
 * 存在于 BaseApplication 的 mApplicationComponent 中 全局
 * Created by liujunfeng on 2017/1/1.
 */
public class AppStore extends BaseRxStore {
    private static AppStore sInstance;
    private boolean isApplyRepair = false;

    private AppStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static AppStore getInstance(Dispatcher dispatcher) {
        if (sInstance == null) sInstance = new AppStore(dispatcher);
        return sInstance;
    }

    /**
     * This callback will get all the rxActions, each store must react on the types he want and do
     * some logic with the model, for example add it to the list to cache it, modify
     * fields etc.. all the logic for the models should go here and then call postChange so the
     * view request the new data
     * 这个回调接收所有的actions(RxAction),每个store都必须根据action的type做出反应,,例如将其添加到列表缓存,修改字段等
     * 所有的逻辑模型应该在这里,然后调用postChange请求新数据视图
     */
    @Override
    public void onRxAction(RxAction rxAction) {
        switch (rxAction.getType()) {
            case Actions.CONNECT_SERVER:
                break;
            case Actions.GET_DEVICE_DETAIL_BY_CODE://扫描数据返回
                Logger.e(rxAction.toString());
                handleDeviceByScan(rxAction);
                break;
            case Actions.APPLY_REPAIR:
                isApplyRepair = true;
                break;
            case Actions.CLICKED_LOGOUT:
                AppUtils.getApplicationComponent().getRxFlux().finishAllActivity();
                Context context = rxAction.get(ActionsKeys.CONTEXT);
                context.startActivity(new Intent(context, LoginActivity.class));
                break;
            case Actions.CLICKED_APPLY_REPAIR:
            case Actions.CLICKED_SERVER_SET:

            case Actions.MESSAGE_GET_NEW_MESSAGE:

            case Actions.TO_LOADING_NEXT:

            case Actions.TO_CHECK_LIST:
            case Actions.TO_FILTER_DEVICE:
            case Actions.TO_DEVICE_PART_LIST:
            case Actions.TO_DEVICE_PART_ADD_RESULT:

            case Actions.TO_REPAIR_ITEM:
            case Actions.TO_REPAIR_SCAN:
            case Actions.TO_REPAIR_START:
            case Actions.TO_REPAIR_RESTART:
            case Actions.TO_REPAIR_FINISH:
            case Actions.TO_REPAIR_PAUSE:
            case Actions.TO_REPAIR_PAUSE_SHOW_DIALOG:

            case Actions.TO_REPAIR_PASS:
            case Actions.TO_REPAIR_FAIL:
            case Actions.TO_REPAIR_FAIL_SHOW_DIALOG:

            case Actions.TO_MAINTENANCE_ITEM:
            case Actions.TO_MAINTENANCE_SCAN:
            case Actions.TO_MAINTENANCE_START:
            case Actions.TO_MAINTENANCE_RESTART:
            case Actions.TO_MAINTENANCE_FINISH:
            case Actions.TO_MAINTENANCE_PAUSE:
            case Actions.TO_MAINTENANCE_PAUSE_SHOW_DIALOG:

            case Actions.TO_MAINTENANCE_PASS:
            case Actions.TO_MAINTENANCE_FAIL:
            case Actions.TO_MAINTENANCE_FAIL_SHOW_DIALOG:

            case Actions.TO_MAINTAIN_ITEM_LIST:
            case Actions.TO_MAINTAIN_ITEM_CONTENT:
            case Actions.TO_MAINTAIN_ITEM_PAUSE:
            case Actions.TO_MAINTAIN_ITEM_SHOW_PAUSE:

            case Actions.TO_INSPECTION_ITEM:
            case Actions.TO_INSPECTION_SCAN:
            case Actions.TO_INSPECTION_START:
            case Actions.TO_INSPECTION_FINISH:
                break;
            default://必须有,接收到非自己处理的action返回
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), rxAction));//只发送自己处理的action
    }

    /**
     * 处理扫描设备返回数据
     *
     * @param action
     */
    private void handleDeviceByScan(RxAction action) {
        //String code = action.get(ActionsKeys.CODE);
        Context context = action.get(ActionsKeys.CONTEXT);
        Device device = getRightResponse(action.get(ActionsKeys.RESPONSE));
        if (device == null) {
            Toast.makeText(context, "未找到对应设备", Toast.LENGTH_SHORT).show();
            return;
        }
        //结束之前的activity
        AppUtils.getApplicationComponent().getRxFlux().finishActivity(DeviceDetailActivity.class);
        AppUtils.getApplicationComponent().getRxFlux().finishActivity(ShowImgActivity.class);
        AppUtils.getApplicationComponent().getRxFlux().finishActivity(RepairDetailActivity.class);
        AppUtils.getApplicationComponent().getRxFlux().finishActivity(MaintenanceDetailActivity.class);
        AppUtils.getApplicationComponent().getRxFlux().finishActivity(InspectionDetailActivity.class);
        //页面跳转
        context.startActivity(DeviceDetailActivity.newIntent(context, device));
    }

    public boolean isApplyRepair() {
        return isApplyRepair;
    }

    public void setApplyRepair(boolean applyRepair) {
        isApplyRepair = applyRepair;
    }
}
