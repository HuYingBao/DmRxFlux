package com.huyingbao.rxflux2.inject.module;

import com.huyingbao.dm.ui.check.store.CheckStore;
import com.huyingbao.dm.ui.common.store.DevicePartAddStore;
import com.huyingbao.dm.ui.devicepart.store.DevicePartStore;
import com.huyingbao.rxflux2.RxFlux;
import com.huyingbao.rxflux2.inject.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Module是一个依赖的制造工厂
 * Created by liujunfeng on 2017/1/1.
 */
@Module
public class FragmentModule {
    @Provides
    @PerFragment
    public DevicePartStore provideDevicePartStore(RxFlux rxFlux) {
        return new DevicePartStore(rxFlux.getDispatcher());
    }

    @Provides
    @PerFragment
    public CheckStore provideCheckStore(RxFlux rxFlux) {
        return new CheckStore(rxFlux.getDispatcher());
    }

    @Provides
    @PerFragment
    public DevicePartAddStore provideDevicePartAddStore(RxFlux rxFlux) {
        return DevicePartAddStore.getInstance(rxFlux.getDispatcher());
    }
}
