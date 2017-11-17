package com.huyingbao.dm.ui.maintenance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActionActivity;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.maintenance.adapter.MaintenanceAdapter;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.maintenance.store.MaintenanceDetailStore;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 维修记录列表
 * Created by liujunfeng on 2017/3/30.
 */
public class MaintenanceListByUserFragment extends BaseRxFluxListFragment<Maintenance> {
    @Inject
    MaintenanceDetailStore mMaintenanceDetailStore;

    public static MaintenanceListByUserFragment newInstance() {
        MaintenanceListByUserFragment fragment = new MaintenanceListByUserFragment();
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar(getString(R.string.title_maintenance));
        super.afterCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mContext instanceof BaseRxFluxToolbarActionActivity) {
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_MAINTENANCE_SCAN, R.string.action_maintenance_scan, R.drawable.ic_device_maintenance, R.drawable.bg_c_so_primary_normal);
        }
    }

    /**
     * 隐藏状态改变回调方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //从隐藏转为非隐藏的时候调用
        if (!hidden && mContext instanceof BaseRxFluxToolbarActionActivity) {
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_MAINTENANCE_SCAN, R.string.action_maintenance_scan, R.drawable.ic_device_maintenance, R.drawable.bg_c_so_primary_normal);
            //刷新
            isRefresh = true;
            mNextIndex = mFirstIndex;
            getDataList(mFirstIndex);
        }
    }

    @Override
    protected void initAdapter() {
        mAdapter = new MaintenanceAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRvContent.addItemDecoration(ViewUtils.getItemDecoration(mContext));
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mActionCreator.postLocalAction(Actions.TO_MAINTENANCE_ITEM, ActionsKeys.MAINTENANCE, mDataList.get(position));
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getMaintenanceListByUser(index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_MAINTENANCE_LIST_BY_USER:
                showDataList(mMaintenanceDetailStore.getMaintenanceList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMaintenanceDetailStore);
    }
}
