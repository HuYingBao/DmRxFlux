package com.huyingbao.dm.ui.maintenance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActionActivity;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.check.CheckListFragment;
import com.huyingbao.dm.ui.devicepart.DevicePartListFragment;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.maintenance.store.MaintenanceDetailStore;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 保养详情
 * Created by liujunfeng on 2017/5/27.
 */
public class MaintenanceDetailActivity extends BaseRxFluxToolbarActionActivity {
    @Inject
    MaintenanceDetailStore mMaintenanceDetailStore;

    public static Intent newIntent(Context context, @NonNull Maintenance maintenance, @NonNull String action) {
        Intent intent = new Intent(context, MaintenanceDetailActivity.class);
        intent.putExtra(ActionsKeys.MAINTENANCE, maintenance);
        intent.putExtra(ActionsKeys.BASE_ACTION, action);
        return intent;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MaintenanceDetailActivity.class);
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        Maintenance maintenance = getIntent().getParcelableExtra(ActionsKeys.MAINTENANCE);
        if (maintenance != null) {
            toMaintenanceDetailFragment(maintenance, getIntent().getStringExtra(ActionsKeys.BASE_ACTION));
        } else {
            toMaintenanceListByUser();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置扫描返回
        IntentIntegrator.REQUEST_CODE = Constants.GET_MAINTENANCE_DETAIL_BY_DEVICE;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || requestCode != Constants.GET_MAINTENANCE_DETAIL_BY_DEVICE)
            return;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result == null || TextUtils.isEmpty(result.getContents())) {
            showShortToast("取消扫描");
            return;
        }
        mActionCreator.getMaintenanceDetailByDevice(mContext, result.getContents());
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.TO_MAINTENANCE_SCAN://去扫码
                new IntentIntegrator(this).initiateScan();
                break;
            case Actions.TO_DEVICE_PART_LIST://查看备品备件使用
                toDevicePartList(change.getRxAction().get(ActionsKeys.MAINTENANCE));
                break;
            case Actions.TO_CHECK_LIST://查看验收记录
                toCheckList(change.getRxAction().get(ActionsKeys.MAINTENANCE));
                break;
            case Actions.TO_MAINTAIN_ITEM_LIST://查看保养项记录
                toMaintainItemList(change.getRxAction().get(ActionsKeys.MAINTENANCE));
                break;
            case Actions.TO_MAINTENANCE_ITEM://点击维修记录,进行维修
                toHandleMaintenanceFragment(change.getRxAction().get(ActionsKeys.MAINTENANCE));
                break;
            case Actions.GET_MAINTENANCE_DETAIL_BY_DEVICE://扫描获取到该设备的维修记录,进行维修
                toHandleMaintenanceFragment(mMaintenanceDetailStore.getMaintenance());
                break;
            case Actions.MAINTAIN_START:
            case Actions.MAINTAIN_RESTART:
                //先把原来的fragment弹出
                if (getSupportFragmentManager().popBackStackImmediate())
                    toMaintenanceFragment(mMaintenanceDetailStore.getMaintenance());
                break;
            case Actions.MAINTAIN_PAUSE:
            case Actions.MAINTAIN_FINISH:
                getSupportFragmentManager().popBackStack();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMaintenanceDetailStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mMaintenanceDetailStore);
    }

    /**
     * 到故障列表列表页面
     */
    private void toMaintenanceListByUser() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, MaintenanceListByUserFragment.newInstance())
                .commit();
    }

    /**
     * 根据保养记录不同的result,跳转不同的页面
     */
    private void toHandleMaintenanceFragment(Maintenance maintenance) {
        switch (maintenance.getResult()) {
            case Constants.TYPE_RESULT_KEEPING://保养进行中
                if (maintenance.getMaintainerId() == mLocalStorageUtils.getUser().getId())
                    toMaintenanceFragment(maintenance);
                else
                    toMaintenanceDetailFragment(maintenance, Actions.TO_MAINTENANCE_ITEM);
                break;
            case Constants.TYPE_RESULT_NO_START://保养未开始
            case Constants.TYPE_RESULT_PAUSE://保养暂停
                toMaintenanceDetailFragment(maintenance, Actions.TO_MAINTENANCE_ITEM);
                break;
            default://其他状态仅是查看
                toMaintenanceDetailFragment(maintenance, Actions.TO_MAINTENANCE_DETAIL);
                break;
        }
    }

    /**
     * 到保养页面
     */
    private void toMaintenanceFragment(Maintenance maintenance) {
        getFragmentTransaction(R.id.fl_content).add(R.id.fl_content, MaintenanceFragment.newInstance(maintenance)).commit();
    }

    /**
     * 到保养详情页面
     * 1:查看详情
     * 2:开始保养
     */
    private void toMaintenanceDetailFragment(Maintenance maintenance, String action) {
        getFragmentTransaction(R.id.fl_content)
                .add(R.id.fl_content, MaintenanceDetailFragment.newInstance(maintenance, action))
                .commit();
    }

    /**
     * 到备品备件使用记录页面
     */
    private void toDevicePartList(Maintenance maintenance) {
        getFragmentTransaction(R.id.fl_content)
                .add(R.id.fl_content, DevicePartListFragment.newInstance(maintenance.getId(), Actions.GET_DEVICE_PART_LIST_BY_MAINTAIN))
                .commit();
    }

    /**
     * 到验收记录页面
     */
    private void toCheckList(Maintenance maintenance) {
        getFragmentTransaction(R.id.fl_content)
                .add(R.id.fl_content, CheckListFragment.newInstance(maintenance.getId(), Actions.GET_CHECK_LIST_BY_MAINTAIN))
                .commit();
    }

    /**
     * 到保养项列表页面
     */
    private void toMaintainItemList(Maintenance maintenance) {
        getFragmentTransaction(R.id.fl_content)
                .add(R.id.fl_content, MaintainItemListFragment.newInstance(maintenance))
                .commit();
    }
}
