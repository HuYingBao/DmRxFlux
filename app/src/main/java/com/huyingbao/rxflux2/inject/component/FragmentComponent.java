package com.huyingbao.rxflux2.inject.component;

import com.huyingbao.dm.ui.check.CheckListFragment;
import com.huyingbao.dm.ui.checkdevice.CheckDeviceListFragment;
import com.huyingbao.dm.ui.common.ConfirmFragment;
import com.huyingbao.dm.ui.common.ContentFragment;
import com.huyingbao.dm.ui.common.DevicePartAddFragment;
import com.huyingbao.dm.ui.common.ShowInfoFragment;
import com.huyingbao.dm.ui.device.DeviceDetailFragment;
import com.huyingbao.dm.ui.device.DeviceInspectionListFragment;
import com.huyingbao.dm.ui.device.DeviceMainFragment;
import com.huyingbao.dm.ui.device.DeviceMaintainPlanListFragment;
import com.huyingbao.dm.ui.device.DeviceMaintenanceListFragment;
import com.huyingbao.dm.ui.device.DeviceMaintenanceMainFragment;
import com.huyingbao.dm.ui.device.DevicePartListFragment;
import com.huyingbao.dm.ui.device.DeviceRepairListFragment;
import com.huyingbao.dm.ui.devicelist.DeviceFilterFragment;
import com.huyingbao.dm.ui.devicelist.DeviceListFragment;
import com.huyingbao.dm.ui.image.ShowImgFragment;
import com.huyingbao.dm.ui.inspection.InspectionDetailFragment;
import com.huyingbao.dm.ui.inspection.InspectionListByUserFragment;
import com.huyingbao.dm.ui.loading.LoadingGuideFragment;
import com.huyingbao.dm.ui.loading.LoadingMainFragment;
import com.huyingbao.dm.ui.login.LoginFragment;
import com.huyingbao.dm.ui.login.ServerSetFragment;
import com.huyingbao.dm.ui.main.MainFragment;
import com.huyingbao.dm.ui.maintenance.MaintainItemListFragment;
import com.huyingbao.dm.ui.maintenance.MaintenanceDetailFragment;
import com.huyingbao.dm.ui.maintenance.MaintenanceFragment;
import com.huyingbao.dm.ui.maintenance.MaintenanceListByUserFragment;
import com.huyingbao.dm.ui.message.MessageDataListFragment;
import com.huyingbao.dm.ui.repair.RepairDetailFragment;
import com.huyingbao.dm.ui.repair.RepairFragment;
import com.huyingbao.dm.ui.repair.RepairListByUserFragment;
import com.huyingbao.rxflux2.inject.module.FragmentModule;
import com.huyingbao.rxflux2.inject.scope.PerFragment;

import dagger.Subcomponent;

/**
 * fragment注入器
 * 子Component:
 * 注意子Component的Scope范围小于父Component
 * Created by liujunfeng on 2017/1/1.
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(LoadingMainFragment loadingMainFragment);

    void inject(LoadingGuideFragment loadingInfoFragment);

    void inject(LoginFragment loginFragment);

    void inject(MainFragment mainFragment);

    void inject(DeviceInspectionListFragment inspectionListFragment);

    void inject(DeviceListFragment deviceListFragment);

    void inject(DeviceDetailFragment deviceDetailFragment);

    void inject(ConfirmFragment confirmFragment);

    void inject(DeviceMainFragment deviceMainFragment);

    void inject(DeviceRepairListFragment repairListFragment);

    void inject(CheckListFragment checkListFragment);

    void inject(com.huyingbao.dm.ui.devicepart.DevicePartListFragment devicePartListFragment);

    void inject(DeviceMaintenanceMainFragment maintenanceMainFragment);

    void inject(DeviceMaintenanceListFragment maintenanceListFragment);

    void inject(MaintainItemListFragment maintainItemListFragment);

    void inject(ShowImgFragment showImgFragment);

    void inject(DeviceMaintainPlanListFragment maintainPlanListFragment);

    void inject(InspectionDetailFragment inspectionDetailFragment);

    void inject(RepairDetailFragment repairDetailFragment);

    void inject(MaintenanceDetailFragment maintenanceDetailFragment);

    void inject(DevicePartListFragment partListFragment);

    void inject(ShowInfoFragment showInfoFragment);

    void inject(DeviceFilterFragment deviceFilterFragment);

    void inject(RepairListByUserFragment repairListByUserFragment);

    void inject(RepairFragment repairFragment);

    void inject(DevicePartAddFragment repairDevicePartListFragment);

    void inject(MaintenanceListByUserFragment maintenanceListByUserFragment);

    void inject(MaintenanceFragment maintenanceFragment);

    void inject(InspectionListByUserFragment inspectionListByUserFragment);

    void inject(ContentFragment contentFragment);

    void inject(CheckDeviceListFragment checkDeviceListFragment);

    void inject(MessageDataListFragment messageListFragment);

    void inject(ServerSetFragment serverSetFragment);
}
