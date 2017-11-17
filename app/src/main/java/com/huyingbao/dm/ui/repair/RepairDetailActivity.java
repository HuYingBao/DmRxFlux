package com.huyingbao.dm.ui.repair;

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
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.dm.ui.repair.store.RepairDetailStore;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 维修详情
 * Created by liujunfeng on 2017/5/27.
 */
public class RepairDetailActivity extends BaseRxFluxToolbarActionActivity {
    @Inject
    RepairDetailStore mRepairDetailStore;

    public static Intent newIntent(Context context, Repair repair, String action) {
        Intent intent = new Intent(context, RepairDetailActivity.class);
        intent.putExtra(ActionsKeys.REPAIR, repair);
        intent.putExtra(ActionsKeys.BASE_ACTION, action);
        return intent;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RepairDetailActivity.class);
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        Repair repair = getIntent().getParcelableExtra(ActionsKeys.REPAIR);
        if (repair != null) {
            toRepairDetailFragment(repair, getIntent().getStringExtra(ActionsKeys.BASE_ACTION));
        } else {
            toRepairListByUser();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置扫描返回
        IntentIntegrator.REQUEST_CODE = Constants.GET_REPAIR_DETAIL_BY_DEVICE;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || requestCode != Constants.GET_REPAIR_DETAIL_BY_DEVICE)
            return;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result == null || TextUtils.isEmpty(result.getContents())) {
            showShortToast("取消扫描");
            return;
        }
        mActionCreator.getRepairDetailByDevice(mContext, result.getContents());
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.TO_REPAIR_SCAN://去扫码
                new IntentIntegrator(this).initiateScan();
                break;
            case Actions.TO_DEVICE_PART_LIST://查看备品备件使用
                toDeviceDepartList(change.getRxAction().get(ActionsKeys.REPAIR));
                break;
            case Actions.TO_CHECK_LIST://查看验收记录
                toCheckList(change.getRxAction().get(ActionsKeys.REPAIR));
                break;
            case Actions.TO_REPAIR_ITEM://点击维修记录,进行维修
                toHandleRepairFragment(change.getRxAction().get(ActionsKeys.REPAIR));
                break;
            case Actions.GET_REPAIR_DETAIL_BY_DEVICE://扫描获取到该设备的维修记录,进行维修
                toHandleRepairFragment(mRepairDetailStore.getRepair());
                break;
            case Actions.REPAIR_START:
            case Actions.REPAIR_RESTART:
                //先把原来的fragment弹出
                if (getSupportFragmentManager().popBackStackImmediate())
                    toRepairFragment(mRepairDetailStore.getRepair());
                break;
            case Actions.REPAIR_PAUSE:
            case Actions.REPAIR_FINISH:
                getSupportFragmentManager().popBackStack();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mRepairDetailStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mRepairDetailStore);
    }

    /**
     * 到故障列表列表页面
     */
    private void toRepairListByUser() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, RepairListByUserFragment.newInstance())
                .commit();
    }


    /**
     * 根据维修记录不同的result,跳转不同的页面
     */
    private void toHandleRepairFragment(Repair repair) {
        switch (repair.getResult()) {
            case Constants.TYPE_RESULT_KEEPING://维修进行中
                if (repair.getRepairMan() == mLocalStorageUtils.getUser().getId())
                    toRepairFragment(repair);
                else
                    toRepairDetailFragment(repair, Actions.TO_REPAIR_ITEM);
                break;
            case Constants.TYPE_RESULT_NO_START://维修未开始
            case Constants.TYPE_RESULT_PAUSE://维修暂停
                toRepairDetailFragment(repair, Actions.TO_REPAIR_ITEM);
                break;
            default://其他状态仅是查看
                toRepairDetailFragment(repair, Actions.TO_REPAIR_DETAIL);
                break;
        }
    }

    /**
     * 到维修页面
     */
    private void toRepairFragment(Repair repair) {
        getFragmentTransaction(R.id.fl_content).add(R.id.fl_content, RepairFragment.newInstance(repair)).commit();
    }

    /**
     * 到维修详情页面
     * 1:查看详情
     * 2:开始维修
     */
    private void toRepairDetailFragment(Repair repair, String action) {
        getFragmentTransaction(R.id.fl_content)
                .add(R.id.fl_content, RepairDetailFragment.newInstance(repair, action))
                .commit();
    }

    /**
     * 到备品备件使用记录页面
     */
    private void toDeviceDepartList(Repair repair) {
        getFragmentTransaction(R.id.fl_content)
                .add(R.id.fl_content, DevicePartListFragment.newInstance(repair.getId(), Actions.GET_DEVICE_PART_LIST_BY_REPAIR))
                .commit();
    }

    /**
     * 到验收记录页面
     */
    private void toCheckList(Repair repair) {
        getFragmentTransaction(R.id.fl_content)
                .add(R.id.fl_content, CheckListFragment.newInstance(repair.getId(), Actions.GET_CHECK_LIST_BY_REPAIR))
                .commit();
    }
}
