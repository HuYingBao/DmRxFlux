package com.huyingbao.dm.ui.repair;

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
import com.huyingbao.dm.ui.repair.adapter.RepairAdapter;
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.dm.ui.repair.store.RepairDetailStore;
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
public class RepairListByUserFragment extends BaseRxFluxListFragment<Repair> {
    @Inject
    RepairDetailStore mRepairDetailStore;

    public static RepairListByUserFragment newInstance() {
        RepairListByUserFragment fragment = new RepairListByUserFragment();
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar(getString(R.string.title_repair));
        super.afterCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mContext instanceof BaseRxFluxToolbarActionActivity) {
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_REPAIR_SCAN, R.string.action_repair_scan, R.drawable.ic_device_repair, R.drawable.bg_c_so_primary_normal);
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
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_REPAIR_SCAN, R.string.action_repair_scan, R.drawable.ic_device_repair, R.drawable.bg_c_so_primary_normal);
            //刷新
            isRefresh = true;
            mNextIndex = mFirstIndex;
            getDataList(mFirstIndex);
        }
    }

    @Override
    protected void initAdapter() {
        mAdapter = new RepairAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRvContent.addItemDecoration(ViewUtils.getItemDecoration(mContext));
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mActionCreator.postLocalAction(Actions.TO_REPAIR_ITEM, ActionsKeys.REPAIR, mDataList.get(position));
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getRepairListByUser(index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_REPAIR_LIST_BY_USER:
                showDataList(mRepairDetailStore.getRepairList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mRepairDetailStore);
    }
}
