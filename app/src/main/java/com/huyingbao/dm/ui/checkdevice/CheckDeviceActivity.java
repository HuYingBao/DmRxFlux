package com.huyingbao.dm.ui.checkdevice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActionActivity;
import com.huyingbao.dm.ui.checkdevice.store.CheckDeviceStore;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2017/8/5.
 */
public class CheckDeviceActivity extends BaseRxFluxToolbarActionActivity {
    @Inject
    CheckDeviceStore mCheckDeviceStore;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CheckDeviceActivity.class);
        return intent;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, CheckDeviceListFragment.newInstance())
                .commit();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mCheckDeviceStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mCheckDeviceStore);
    }
}
