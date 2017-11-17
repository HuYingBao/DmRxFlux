package com.huyingbao.dm.ui.maintenance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.common.ShowInfoFragment;
import com.huyingbao.dm.ui.maintenance.adapter.MaintainItemAdapter;
import com.huyingbao.dm.ui.maintenance.model.MaintainItem;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.maintenance.store.MaintenanceDetailStore;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 保养项列表
 * Created by liujunfeng on 2017/3/30.
 */
public class MaintainItemListFragment extends BaseRxFluxListFragment<MultiItemEntity> {
    @Inject
    MaintenanceDetailStore mMaintainItemStore;

    private Maintenance mMaintenance;

    /**
     * @param maintenance 保养记录
     * @return
     */
    public static MaintainItemListFragment newInstance(@NonNull Maintenance maintenance) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.MAINTENANCE, maintenance);
        MaintainItemListFragment fragment = new MaintainItemListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar(getString(R.string.title_maintenance_item));
        mMaintenance = getArguments().getParcelable(ActionsKeys.MAINTENANCE);
        super.afterCreate(savedInstanceState);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new MaintainItemAdapter(mDataList);
        ((MaintainItemAdapter) mAdapter).setStart(mMaintenance.getResult() != Constants.TYPE_RESULT_INSPECTION_NO_START);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        ViewUtils.setViewMargin(mRvContent, true, 10, 10, 10, 10);
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getMaintainItemListByMaintain(mMaintenance.getId());
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_MAINTAIN_ITEM_LIST_BY_MAINTAIN:
                showDataList(mMaintainItemStore.getMaintainItemTitleList());
                break;
            case Actions.TO_MAINTAIN_ITEM_CONTENT:
                MaintainItem maintainItem = change.getRxAction().get(ActionsKeys.MAINTAIN_ITEM_CONTENT);
                ShowInfoFragment.newInstance("未完成原因", maintainItem.getResultCause())
                        .show(getChildFragmentManager(), ShowInfoFragment.class.getSimpleName());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMaintainItemStore);
    }
}
