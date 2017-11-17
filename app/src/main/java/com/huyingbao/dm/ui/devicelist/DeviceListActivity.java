package com.huyingbao.dm.ui.devicelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActivity;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.devicelist.store.DeviceListStore;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 设备列表页面
 * Created by liujunfeng on 2017/3/29.
 */
public class DeviceListActivity extends BaseRxFluxToolbarActivity {
    @Inject
    DeviceListStore mDeviceListStore;

    /**
     * @param context
     * @param deviceStatus 设备状态
     * @return
     */
    public static Intent newIntent(Context context, int deviceStatus) {
        Intent intent = new Intent(context, DeviceListActivity.class);
        intent.putExtra(ActionsKeys.STATUS, deviceStatus);
        return intent;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        int deviceStatus = getIntent().getIntExtra(ActionsKeys.STATUS, Constants.STATUS_DEVICE_NORMAL);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, DeviceListFragment.newInstance(deviceStatus))
                .commit();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDeviceListStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mDeviceListStore);
    }
}
