package com.huyingbao.dm.ui.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActivity;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.device.store.DeviceDetailStore;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 设备详情
 * Created by liujunfeng on 2017/5/27.
 */
public class DeviceDetailActivity extends BaseRxFluxToolbarActivity {
    @Inject
    DeviceDetailStore mDeviceDetailStore;
    private Device mDevice;

    public static Intent newIntent(Context context, Device device) {
        Intent intent = new Intent(context, DeviceDetailActivity.class);
        intent.putExtra(ActionsKeys.DEVICE, device);
        return intent;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mDevice = getIntent().getParcelableExtra(ActionsKeys.DEVICE);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, DeviceMainFragment.newInstance(mDevice))
                .commit();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDeviceDetailStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mDeviceDetailStore);
    }
}
