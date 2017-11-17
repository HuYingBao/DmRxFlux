package com.huyingbao.rxflux2.inject.component;

import android.app.Activity;
import android.content.Context;

import com.huyingbao.dm.ui.checkdevice.CheckDeviceActivity;
import com.huyingbao.dm.ui.device.DeviceDetailActivity;
import com.huyingbao.dm.ui.devicelist.DeviceListActivity;
import com.huyingbao.dm.ui.image.ShowImgActivity;
import com.huyingbao.dm.ui.inspection.InspectionDetailActivity;
import com.huyingbao.dm.ui.loading.LoadingActivity;
import com.huyingbao.dm.ui.login.LoginActivity;
import com.huyingbao.dm.ui.main.MainActivity;
import com.huyingbao.dm.ui.maintenance.MaintenanceDetailActivity;
import com.huyingbao.dm.ui.message.MessageDataActivity;
import com.huyingbao.dm.ui.repair.RepairDetailActivity;
import com.huyingbao.rxflux2.inject.module.ActivityModule;
import com.huyingbao.rxflux2.inject.qualifier.ContextLife;
import com.huyingbao.rxflux2.inject.scope.PerActivity;

import dagger.Component;

/**
 * activity注入器
 * 两个Component间有依赖关系，那么它们不能使用相同的Scope
 * Created by liujunfeng on 2017/1/1.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    /**
     * 通过自定义的@ContextLife区分返回类型相同的@Provides 方法
     *
     * @return
     */
    @ContextLife("Activity")
    Context getActivityContext();

    /**
     * 从对应的ActivityModule中找不到,从dependencies的ApplicationComponent中找得到
     *
     * @return
     */
    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    /**
     * 需要在父Component(ActivityComponent)添加返回子Component(FragmentComponent)的方法
     *
     * @return
     */
    FragmentComponent getFragmentComponent();

    void inject(LoginActivity loginActivity);

    void inject(LoadingActivity loadingAty);

    void inject(DeviceListActivity deviceListActivity);

    void inject(MainActivity mainActivity);

    void inject(MessageDataActivity messageActivity);

    void inject(DeviceDetailActivity deviceDetailActivity);

    void inject(ShowImgActivity showImgActivity);

    void inject(RepairDetailActivity repairDetailActivity);

    void inject(MaintenanceDetailActivity maintenanceDetailActivity);

    void inject(InspectionDetailActivity inspectionDetailActivity);

    void inject(CheckDeviceActivity checkDeviceActivity);
}
