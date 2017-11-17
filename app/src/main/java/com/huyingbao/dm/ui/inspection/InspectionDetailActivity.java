package com.huyingbao.dm.ui.inspection;

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
import com.huyingbao.dm.ui.inspection.model.Inspection;
import com.huyingbao.dm.ui.inspection.store.InspectionDetailStore;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 巡检详情
 * Created by liujunfeng on 2017/5/27.
 */
public class InspectionDetailActivity extends BaseRxFluxToolbarActionActivity {
    @Inject
    InspectionDetailStore inspectionDetailStore;

    public static Intent newIntent(Context context, Inspection inspection) {
        Intent intent = new Intent(context, InspectionDetailActivity.class);
        intent.putExtra(ActionsKeys.INSPECTION, inspection);
        return intent;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, InspectionDetailActivity.class);
        return intent;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        Inspection inspection = getIntent().getParcelableExtra(ActionsKeys.INSPECTION);
        if (inspection != null) {
            toInspectionDetailFragment(inspection, Actions.TO_INSPECTION_DETAIL);
        } else {
            toInspectionListByUser();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置扫描返回
        IntentIntegrator.REQUEST_CODE = Constants.GET_INSPECTION_DETAIL_BY_DEVICE;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || requestCode != Constants.GET_INSPECTION_DETAIL_BY_DEVICE)
            return;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result == null || TextUtils.isEmpty(result.getContents())) {
            showShortToast("取消扫描");
            return;
        }
        mActionCreator.getInspectionDetailByDevice(mContext, result.getContents());
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.TO_INSPECTION_SCAN://去扫码
                new IntentIntegrator(this).initiateScan();
                break;
            case Actions.TO_INSPECTION_ITEM://点击巡检记录,进行巡检
                toHandleInspectionFragment(change.getRxAction().get(ActionsKeys.INSPECTION));
                break;
            case Actions.GET_INSPECTION_DETAIL_BY_DEVICE://扫描获取到该设备的巡检记录,进行巡检
                toHandleInspectionFragment(inspectionDetailStore.getInspection());
                break;
            case Actions.INSPECTION_FINISH:
                getSupportFragmentManager().popBackStack();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(inspectionDetailStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(inspectionDetailStore);
    }

    /**
     * 到巡检列表列表页面
     */
    private void toInspectionListByUser() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, InspectionListByUserFragment.newInstance())
                .commit();
    }

    /**
     * 根据巡检记录不同的result,跳转不同的页面
     */
    private void toHandleInspectionFragment(Inspection inspection) {
        switch (inspection.getResult()) {
            case Constants.TYPE_RESULT_INSPECTION_NO_START://巡检未开始
                toInspectionDetailFragment(inspection, Actions.TO_INSPECTION_ITEM);
                break;
            default://其他状态仅是查看
                toInspectionDetailFragment(inspection, Actions.TO_INSPECTION_DETAIL);
                break;
        }
    }

    /**
     * 到巡检详情页面
     * 1:查看详情
     * 2:开始巡检
     */
    private void toInspectionDetailFragment(Inspection inspection, String action) {
        getFragmentTransaction(R.id.fl_content)
                .add(R.id.fl_content, InspectionDetailFragment.newInstance(inspection, action))
                .commit();
    }
}
