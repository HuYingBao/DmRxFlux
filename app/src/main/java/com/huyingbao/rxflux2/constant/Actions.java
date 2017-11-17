package com.huyingbao.rxflux2.constant;

import android.content.Context;

import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.dm.ui.inspection.model.InspectItem;
import com.huyingbao.dm.ui.maintenance.model.MaintainItem;

import java.util.List;

/**
 * add:添加
 * get:获取
 * update:修改
 * delete:删除
 * apply:申请
 * confirm:确认
 * write:发送
 * check:检查
 * Created by liujunfeng on 2017/1/1.
 */
public interface Actions {
    //region 本地
    String TO_LOADING_NEXT = "to_loading_next";//到首页引导页面的下一页
    String TO_DEVICE_PART_LIST = "to_device_part_list";//到备品备件使用记录页面
    String TO_CHECK_LIST = "to_check_list";//到验收记录页面

    String TO_FILTER_DEVICE = "to_filter_device";//筛选设备

    String TO_DEVICE_PART_ADD_RESULT = "to_show_device_part_result";//显示备品备件

    String TO_REPAIR_DETAIL = "to_repair_detail";//到维修详情页面
    String TO_REPAIR_ITEM = "to_repair_item";//到设备维修页面
    String TO_REPAIR_CHECK = "to_repair_check";//到维修验收页面

    String TO_REPAIR_SCAN = "to_repair_scan";//扫描维修
    String TO_REPAIR_START = "to_repair_start";//开始维修
    String TO_REPAIR_RESTART = "to_repair_restart";//继续维修
    String TO_REPAIR_FINISH = "to_repair_finish";//维修结束
    String TO_REPAIR_PAUSE = "to_repair_pause";//维修暂停
    String TO_REPAIR_PAUSE_SHOW_DIALOG = "to_repair_pause_show_dialog";//显示输入维修暂停原因对话框

    String TO_REPAIR_PASS = "to_repair_pass";//维修验收通过
    String TO_REPAIR_FAIL = "to_repair_fail";//维修验收不通过
    String TO_REPAIR_FAIL_SHOW_DIALOG = "to_repair_fail_show_dialog";//显示维修验收不通过对话框

    String TO_MAINTENANCE_DETAIL = "to_maintenance_detail";//到保养详情页面
    String TO_MAINTENANCE_ITEM = "to_maintenance_item";//到保养页面
    String TO_MAINTENANCE_CHECK = "to_maintenance_check";//到保养验收页面

    String TO_MAINTENANCE_SCAN = "to_maintenance_scan";//扫码保养
    String TO_MAINTENANCE_START = "to_maintenance_start";//开始保养
    String TO_MAINTENANCE_RESTART = "to_maintenance_restart";//继续保养
    String TO_MAINTENANCE_FINISH = "to_maintenance_finish";//保养结束
    String TO_MAINTENANCE_PAUSE = "to_maintenance_pause";//保养暂停
    String TO_MAINTENANCE_PAUSE_SHOW_DIALOG = "to_maintenance_pause_show_dialog";//显示保养暂停原因对话框

    String TO_MAINTENANCE_PASS = "to_maintenance_pass";//保养验收通过
    String TO_MAINTENANCE_FAIL = "to_maintenance_fail";//保养验收不通过
    String TO_MAINTENANCE_FAIL_SHOW_DIALOG = "to_maintenance_fail_show_dialog";//显示保养验收不通过对话框

    String TO_MAINTAIN_ITEM_LIST = "to_maintain_item_list";//到保养项列表页面
    String TO_MAINTAIN_ITEM_CONTENT = "to_maintain_item_content";//保养项数据
    String TO_MAINTAIN_ITEM_PAUSE = "to_maintain_item_pause";//保养项未完成
    String TO_MAINTAIN_ITEM_SHOW_PAUSE = "to_maintain_item_show_pause";//显示保养项未完成原因对话框

    String TO_INSPECTION_DETAIL = "to_inspection_detail";//到巡检详情页面
    String TO_INSPECTION_ITEM = "to_inspection_item";//到巡检页面
    String TO_INSPECTION_SCAN = "to_inspection_scan";//扫码巡检
    String TO_INSPECTION_START = "to_inspection_start";//开始巡检
    String TO_INSPECTION_FINISH = "to_inspection_finish";//巡检结束

    String MESSAGE_GET_NEW_MESSAGE = "message_get_new message";//接收到消息
    String CLICKED_APPLY_REPAIR = "clicked_apply_repair";//点击报修按钮
    String CLICKED_LOGOUT = "clicked_logout";//点击退出登录
    String CLICKED_SERVER_SET = "clicked_server_set";//点击设置服务器
    String NET_DISCONNECTED = "net_disconnected";
    String NET_CONNECTED = "net_connected";
    //endregion

    //region 用户
    String CONNECT_SERVER = "connectServer";//连接服务器
    String LOGIN = "login";//用户登录
    String LOGOUT = "logout";//注销
    String GET_ALL_MSG = "getAllMsg";//获取所有的推送消息
    //endregion

    //region 设备
    String GET_DEVICE_STATUS_LIST = "getDeviceStatusArrayMap";//获取首页设备状态数量
    String GET_DEVICE_LIST = "getDeviceList";//获取设备列表
    String GET_DEVICE_DETAIL_BY_CODE = "getDeviceDetailByCode";//获取设备基本信息-根据设备code
    String GET_DEVICE_DETAIL = "getDeviceDetail";//获取设备基本信息--根据设备id
    String GET_DEVICE_TYPE_LIST = "getDeviceTypeList";//获取全部设备类型
    //endregion

    //region 备品
    String GET_DEVICE_PART_LIST_BY_DEVICE = "getDevicePartListByDevice";//获取备品备件使用记录--设备
    String GET_DEVICE_PART_LIST = "getDevicePartList";//获取备品备件使用记录--维修记录/保养记录
    String GET_DEVICE_PART_LIST_BY_REPAIR = "getDevicePartListByRepair";//获取备品备件使用记录--维修记录
    String GET_DEVICE_PART_LIST_BY_MAINTAIN = "getDevicePartListByMaintain";//获取备品备件使用记录--保养记录
    String GET_DEVICE_PART_LIST_BY_DEVICE_TYPE = "getDevicePartListByDeviceType";//获取备品--设备类型
    //endregion

    //region 巡检
    String GET_INSPECTION_LIST_BY_DEVICE = "getInspectionListByDevice";//获取巡检记录--设备
    String GET_INSPECTION_DETAIL = "getInspectionDetail";//获取巡检记录详情
    String GET_INSPECTION_DETAIL_BY_DEVICE = "getInspectionDetailByDevice";//获取巡检记录详情--设备编码（设备巡检-扫码用）
    String GET_INSPECTION_LIST_BY_USER = "getInspectionListByUser";//获取未执行的巡检记录--人员
    String INSPECTION_START = "inspectionStart";//巡检开始
    String INSPECTION_FINISH = "inspectionFinish";//巡检结束
    //endregion

    //region 巡检项
    String GET_INSPECT_ITEM_LIST_BY_INSPECT = "getInspectItemListByInspect";//获取巡检项记录--巡检记录
    //endregion

    //region 维修
    String APPLY_REPAIR = "applyRepair";//申请维修设备
    String GET_REPAIR_LIST_BY_DEVICE = "getRepairListByDevice";//获取维修记录--设备
    String GET_REPAIR_DETAIL = "getRepairDetail";//获取维修记录详情
    String GET_REPAIR_LIST_BY_USER = "getRepairListByUser";//获取维修记录--人员
    String GET_REPAIR_DETAIL_BY_DEVICE = "getRepairDetailByDevice";//获取维修记录详情-设备
    String REPAIR_START = "repairStart";//维修开始
    String REPAIR_PAUSE = "repairPause";//维修暂停
    String REPAIR_RESTART = "repairRestart";//维修继续
    String REPAIR_FINISH = "repairFinish";//维修结束
    String REPAIR_APPROVE = "repairApprove";//维修验收-通过
    String REPAIR_REJECT = "repairReject";//维修验收-不通过
    //endregion

    //region 保养
    String GET_MAINTENANCE_LIST_BY_DEVICE = "getMaintenanceListByDevice";//获取保养记录--设备
    String GET_MAINTAIN_PLAN_LIST_BY_DEVICE = "getMaintainPlanListByDevice";//获取保养计划--设备
    String GET_MAINTENANCE_DETAIL = "getMaintenanceDetail";//获取保养记录详情
    String GET_MAINTENANCE_LIST_BY_USER = "getMaintenanceListByUser";//获取保养记录--人员(未开始、暂停、进行中、待验收)
    String GET_MAINTENANCE_DETAIL_BY_DEVICE = "getMaintenanceDetailByDevice";//获取保养记录详情-设备
    String MAINTAIN_START = "maintainStart";//保养开始
    String MAINTAIN_PAUSE = "maintainPause";//保养暂停
    String MAINTAIN_RESTART = "maintainRestart";//保养继续
    String MAINTAIN_FINISH = "maintainFinish";//保养结束
    String MAINTAIN_APPROVE = "maintainApprove";//保养验收-通过
    String MAINTAIN_REJECT = "maintainReject";//保养验收-不通过
    //endregion

    //region 保养项
    String GET_MAINTAIN_ITEM_LIST_BY_MAINTAIN = "getMaintainItemListByMaintain";//获取保养项纪录--保养记录
    //endregion

    //region 验收
    String GET_CHECK_LIST_BY_REPAIR = "getCheckListByRepair";//获取验收记录--维修记录
    String GET_CHECK_LIST_BY_MAINTAIN = "getCheckListByMaintain";//获取验收记录--保养记录
    String GET_CHECK_LIST_BY_USER = "getCheckListByUser";//获取所有待验收的申请记录（维修待验收、保养待验收）--人员
    //endregion

    void connectServer();

    void login(Context context, String name, String pwd, String channelId);

    void logout(Context context);

    void getAllMsg(int page, int rows);

    void getDeviceStatusList();

    void getDeviceList(String arrayType, String arrayStatus, int page);

    void getDeviceDetailByCode(Context context, String code);

    void getDeviceDetail(Context context, int deviceId);

    void getDeviceTypeList();

    void getDevicePartListByDevice(int deviceId, int page);

    void getDevicePartList(String actionType, int id, int page);

    void getDevicePartListByRepair(int repairId, int page);

    void getDevicePartListByMaintain(int maintenanceId, int page);

    void getDevicePartListByDeviceType(int machineTypeId);

    void getInspectionListByDevice(int deviceId, int page);

    void getInspectionDetail(Context context, int id);

    void getInspectionDetailByDevice(Context context, String machineCode);

    void getInspectionListByUser(int page);

    void inspectionStart(Context context, int inspectId);

    void inspectionFinish(Context context, int inspectId, List<InspectItem> items);

    void getInspectItemListByInspect(int inspectId);

    void applyRepair(Context context, int deviceId);

    void getRepairListByDevice(int deviceId, int page);

    void getRepairDetail(Context context, int repairId);

    void getRepairListByUser(int page);

    void getRepairDetailByDevice(Context context, String machineCode);

    void repairStart(Context context, int repairId);

    void repairPause(Context context, int repairId, String faultCause, String scheme, String remark, String pauseExplain, List<DevicePart> devicePartList);

    void repairRestart(Context context, int repairId);

    void repairFinish(Context context, int repairId, String faultCause, String scheme, String remark, String pauseExplain, List<DevicePart> devicePartList);

    void repairApprove(Context context, int repairId, String opinion);

    void repairReject(Context context, int repairId, String opinion);

    void getMaintenanceListByDevice(int deviceId, int page);

    void getMaintainPlanListByDevice(int deviceId);

    void getMaintenanceDetail(Context context, int maintenanceId);

    void getMaintenanceListByUser(int page);

    void getMaintenanceDetailByDevice(Context context, String machineCode);

    void maintainStart(Context context, int maintainId);

    void maintainPause(Context context, int maintainId, String preExplain, String remark, String pauseExplain, List<MaintainItem> maintainItems, List<DevicePart> devicePartList);

    void maintainRestart(Context context, int maintainId);

    void maintainFinish(Context context, int maintainId, String preExplain, String remark, String pauseExplain, List<MaintainItem> maintainItems, List<DevicePart> devicePartList);

    void maintainApprove(Context context, int maintainId, String opinion);

    void maintainReject(Context context, int maintainId, String opinion);

    void getMaintainItemListByMaintain(int maintenanceId);

    void getCheckListByRepair(int repairId, int page);

    void getCheckListByMaintain(int maintenanceId, int page);

    void getCheckListByUser(Boolean isRepair, int page);
}
