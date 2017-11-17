package com.huyingbao.rxflux2.api;

import android.support.v4.util.ArrayMap;

import com.huyingbao.dm.ui.check.model.Check;
import com.huyingbao.dm.ui.checkdevice.model.CheckDevice;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.devicelist.model.DeviceType;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.dm.ui.inspection.model.InspectItem;
import com.huyingbao.dm.ui.inspection.model.Inspection;
import com.huyingbao.dm.ui.main.model.Status;
import com.huyingbao.dm.ui.main.model.User;
import com.huyingbao.dm.ui.maintenance.model.MaintainItem;
import com.huyingbao.dm.ui.maintenance.model.MaintainPlan;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.message.model.MessageData;
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.rxflux2.model.HttpResponse;
import com.huyingbao.rxflux2.model.Page;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

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
public interface HttpApi {
    String SERVER_API = "BFMMS/app/";

    //region 公用
    @GET(SERVER_API + "heartbeat")
    Observable<HttpResponse> connectServer();
    //endregion

    //region 用户
    //用户登录
    @GET(SERVER_API + "login")
    Observable<HttpResponse<User>> login(@QueryMap ArrayMap<String, Object> queryMap);

    //注销
    @GET(SERVER_API + "logout")
    Observable<HttpResponse> logout();

    @GET(SERVER_API + "getAllMsgs")
    Observable<HttpResponse<Page<MessageData>>> getAllMsg(@QueryMap ArrayMap<String, Object> queryMap);
    //endregion

    //region 设备
    //获取首页设备状态数量
    @GET(SERVER_API + "homepage/getMachineStatus")
    Observable<HttpResponse<List<Status>>> getDeviceStatusList();

    //获取设备列表
    @GET(SERVER_API + "machineMgr/getMachines")
    Observable<HttpResponse<Page<Device>>> getDeviceList(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取设备基本信息-根据设备code
    @GET(SERVER_API + "machineMgr/getMachineDetailByCode")
    Observable<HttpResponse<Device>> getDeviceDetailByCode(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取设备基本信息
    @GET(SERVER_API + "machineMgr/getMachineDetail")
    Observable<HttpResponse<Device>> getDeviceDetail(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取全部设备类型
    @GET(SERVER_API + "machineMgr/getAllMachineTypes")
    Observable<HttpResponse<List<DeviceType>>> getDeviceTypeList();
    //endregion

    //region 备品
    //获取备品备件使用记录--设备
    @GET(SERVER_API + "machineMgr/getSparepartByMachine")
    Observable<HttpResponse<Page<DevicePart>>> getDevicePartListByDevice(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取备品备件使用记录--维修记录
    @GET(SERVER_API + "machineMgr/getSparepartByRepair")
    Observable<HttpResponse<Page<DevicePart>>> getDevicePartListByRepair(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取备品备件使用记录--保养记录
    @GET(SERVER_API + "machineMgr/getSparepartByMaintain")
    Observable<HttpResponse<Page<DevicePart>>> getDevicePartListByMaintain(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取备品--设备类型
    @GET(SERVER_API + "sparepart/getSparepartsByMachineType")
    Observable<HttpResponse<Page<DevicePart>>> getDevicePartListByDeviceType(@QueryMap ArrayMap<String, Object> arrayMap);
    //endregion

    //region 巡检
    //获取巡检记录--设备
    @GET(SERVER_API + "machineMgr/getInspectsByMachine")
    Observable<HttpResponse<Page<Inspection>>> getInspectionListByDevice(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取巡检记录详情
    @GET(SERVER_API + "machineInspect/getInspectDetail")
    Observable<HttpResponse<Inspection>> getInspectionDetail(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取巡检记录详情--设备编码（设备巡检-扫码用）
    @GET(SERVER_API + "machineInspect/getInspectDetailByMachine")
    Observable<HttpResponse<Inspection>> getInspectionDetailByDevice(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取未执行的巡检记录--人员
    @GET(SERVER_API + "machineInspect/getUnexecutedInspects")
    Observable<HttpResponse<Page<Inspection>>> getInspectionListByUser(@QueryMap ArrayMap<String, Object> arrayMap);

    //巡检开始
    @GET(SERVER_API + "machineInspect/startInspect")
    Observable<HttpResponse<Inspection>> inspectionStart(@QueryMap ArrayMap<String, Object> arrayMap);

    //巡检结束
    @POST(SERVER_API + "machineInspect/finishInspect")
    Observable<HttpResponse<Inspection>> inspectionFinish(@Body ArrayMap<String, Object> arrayMap);
    //endregion

    //region 巡检项
    //获取巡检项记录--巡检记录
    @GET(SERVER_API + "machineMgr/getInspectItemsByInspect")
    Observable<HttpResponse<Page<InspectItem>>> getInspectItemListByInspect(@QueryMap ArrayMap<String, Object> arrayMap);
    //endregion

    //region 维修
    //申请维修设备
    @GET(SERVER_API + "machineMgr/notifyMachineFault")
    Observable<HttpResponse> applyRepair(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取维修记录--设备
    @GET(SERVER_API + "machineMgr/getRepairsByMachine")
    Observable<HttpResponse<Page<Repair>>> getRepairListByDevice(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取维修记录详情-维修记录
    @GET(SERVER_API + "machineRepair/getRepairDetail")
    Observable<HttpResponse<Repair>> getRepairDetail(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取维修记录--人员(未开始、暂停、进行中、待验收)
    @GET(SERVER_API + "machineRepair/getFaultedRepairs")
    Observable<HttpResponse<Page<Repair>>> getRepairListByUser(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取维修记录详情-设备
    @GET(SERVER_API + "machineRepair/getRepairDetailByMachine")
    Observable<HttpResponse<Repair>> getRepairDetailByDevice(@QueryMap ArrayMap<String, Object> arrayMap);

    //维修开始
    @GET(SERVER_API + "machineRepair/startRepair")
    Observable<HttpResponse<Repair>> repairStart(@QueryMap ArrayMap<String, Object> arrayMap);

    //维修暂停
    @POST(SERVER_API + "machineRepair/pauseRepair")
    Observable<HttpResponse<Repair>> repairPause(@Body ArrayMap<String, Object> arrayMap);

    //维修继续
    @GET(SERVER_API + "machineRepair/continueRepair")
    Observable<HttpResponse<Repair>> repairRestart(@QueryMap ArrayMap<String, Object> arrayMap);

    //维修结束
    @POST(SERVER_API + "machineRepair/finishRepair")
    Observable<HttpResponse<Repair>> repairFinish(@Body ArrayMap<String, Object> arrayMap);

    //维修验收-通过
    @GET(SERVER_API + "machineRepair/approveRepair")
    Observable<HttpResponse<Repair>> repairApprove(@QueryMap ArrayMap<String, Object> arrayMap);

    //维修验收-不通过
    @GET(SERVER_API + "machineRepair/rejectRepair")
    Observable<HttpResponse<Repair>> repairReject(@QueryMap ArrayMap<String, Object> arrayMap);
    //endregion

    //region 保养
    //获取保养记录--设备
    @GET(SERVER_API + "machineMgr/getMaintainsByMachine")
    Observable<HttpResponse<Page<Maintenance>>> getMaintenanceListByDevice(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取保养计划--设备
    @GET(SERVER_API + "machineMgr/getMaintainPlansByMachine")
    Observable<HttpResponse<Page<MaintainPlan>>> getMaintainPlanListByDevice(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取保养记录详情
    @GET(SERVER_API + "machineMaintain/getMaintainDetail")
    Observable<HttpResponse<Maintenance>> getMaintenanceDetail(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取保养记录--人员(未开始、暂停、进行中、待验收)
    @GET(SERVER_API + "machineMaintain/getUnfinishedMaintains")
    Observable<HttpResponse<Page<Maintenance>>> getMaintenanceListByUser(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取保养记录详情-设备
    @GET(SERVER_API + "machineMaintain/getMaintainDetailByMachine")
    Observable<HttpResponse<Maintenance>> getMaintenanceDetailByDevice(@QueryMap ArrayMap<String, Object> arrayMap);

    //保养开始
    @GET(SERVER_API + "machineMaintain/startMaintain")
    Observable<HttpResponse<Maintenance>> maintainStart(@QueryMap ArrayMap<String, Object> arrayMap);

    //保养暂停
    @POST(SERVER_API + "machineMaintain/pauseMaintain")
    Observable<HttpResponse<Maintenance>> maintainPause(@Body ArrayMap<String, Object> arrayMap);

    //保养继续
    @GET(SERVER_API + "machineMaintain/continueMaintain")
    Observable<HttpResponse<Maintenance>> maintainRestart(@QueryMap ArrayMap<String, Object> arrayMap);

    //保养结束
    @POST(SERVER_API + "machineMaintain/finishMaintain")
    Observable<HttpResponse<Maintenance>> maintainFinish(@Body ArrayMap<String, Object> arrayMap);

    //保养验收-通过
    @GET(SERVER_API + "machineMaintain/approveMaintain")
    Observable<HttpResponse<Maintenance>> maintainApprove(@QueryMap ArrayMap<String, Object> arrayMap);

    //保养验收-不通过
    @GET(SERVER_API + "machineMaintain/rejectMaintain")
    Observable<HttpResponse<Maintenance>> maintainReject(@QueryMap ArrayMap<String, Object> arrayMap);
    //endregion

    //region 保养项
    //获取保养项纪录--保养记录
    @GET(SERVER_API + "machineMgr/getItemsByMaintain")
    Observable<HttpResponse<Page<MaintainItem>>> getMaintainItemListByMaintain(@QueryMap ArrayMap<String, Object> arrayMap);
    //endregion

    //region 验收
    //获取验收记录--维修记录
    @GET(SERVER_API + "machineMgr/getChecksByRepair")
    Observable<HttpResponse<Page<Check>>> getCheckListByRepair(@QueryMap ArrayMap<String, Object> arrayMap);

    //获取验收记录--保养记录
    @GET(SERVER_API + "machineMgr/getChecksByMaintain")
    Observable<HttpResponse<Page<Check>>> getCheckListByMaintain(@QueryMap ArrayMap<String, Object> arrayMap);


    //获取所有待验收的申请记录（维修待验收、保养待验收）--人员
    @GET(SERVER_API + "machineCheck/getUnapprovedChecks")
    Observable<HttpResponse<Page<CheckDevice>>> getCheckListByUser(@QueryMap ArrayMap<String, Object> arrayMap);
    //endregion
}
