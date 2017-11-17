package com.huyingbao.rxflux2.inject.module;

import android.app.Activity;
import android.content.Context;

import com.huyingbao.dm.BuildConfig;
import com.huyingbao.dm.ui.checkdevice.store.CheckDeviceStore;
import com.huyingbao.dm.ui.device.store.DeviceDetailStore;
import com.huyingbao.dm.ui.devicelist.store.DeviceListStore;
import com.huyingbao.dm.ui.inspection.store.InspectionDetailStore;
import com.huyingbao.dm.ui.login.store.LoginStore;
import com.huyingbao.dm.ui.main.store.MainStore;
import com.huyingbao.dm.ui.maintenance.store.MaintenanceDetailStore;
import com.huyingbao.dm.ui.message.store.MessageStore;
import com.huyingbao.dm.ui.repair.store.RepairDetailStore;
import com.huyingbao.rxflux2.RxFlux;
import com.huyingbao.rxflux2.inject.qualifier.ContextLife;
import com.huyingbao.rxflux2.inject.scope.PerActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * activity中的依赖制造类
 * Module是一个依赖的制造工厂
 * 保存在ActivityComponent
 * Created by liujunfeng on 2017/1/1.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    /**
     * 通过自定义的@ContextLife区分返回类型相同的@Provides 方法
     *
     * @return
     */
    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideContext() {
        //使用弱引用,消除内存泄漏
        return new WeakReference<>(mActivity).get();
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        //使用弱引用,消除内存泄漏
        return new WeakReference<>(mActivity).get();
    }

    @Provides
    @PerActivity
    public FragmentModule provideFragmentModule() {
        return new FragmentModule();
    }

    @Provides
    @PerActivity
    public RxPermissions provideRxPermissions() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.setLogging(BuildConfig.DEBUG);
        return rxPermissions;
    }

    @Provides
    @PerActivity
    public LoginStore provideLoginStore(RxFlux rxFlux) {
        return new LoginStore(rxFlux.getDispatcher());
    }

    @Provides
    @PerActivity
    public MainStore provideMainStore(RxFlux rxFlux) {
        return new MainStore(rxFlux.getDispatcher());
    }

    @Provides
    @PerActivity
    public DeviceListStore provideDeviceStore(RxFlux rxFlux) {
        return new DeviceListStore(rxFlux.getDispatcher());
    }

    @Provides
    @PerActivity
    public DeviceDetailStore provideDeviceDetailStore(RxFlux rxFlux) {
        return DeviceDetailStore.getInstance(rxFlux.getDispatcher());
    }

    @Provides
    @PerActivity
    public MessageStore provideMessageStore(RxFlux rxFlux) {
        return new MessageStore(rxFlux.getDispatcher());
    }

    @Provides
    @PerActivity
    public RepairDetailStore provideRepairDetailStore(RxFlux rxFlux) {
        return RepairDetailStore.getInstance(rxFlux.getDispatcher());
    }

    @Provides
    @PerActivity
    public MaintenanceDetailStore provideMaintenanceDetailStore(RxFlux rxFlux) {
        return MaintenanceDetailStore.getInstance(rxFlux.getDispatcher());
    }

    @Provides
    @PerActivity
    public InspectionDetailStore provideInspectionDetailStore(RxFlux rxFlux) {
        return InspectionDetailStore.getInstance(rxFlux.getDispatcher());
    }

    @Provides
    @PerActivity
    public CheckDeviceStore provideCheckDeviceStore(RxFlux rxFlux) {
        return CheckDeviceStore.getInstance(rxFlux.getDispatcher());
    }
}
