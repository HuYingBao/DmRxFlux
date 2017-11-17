package com.huyingbao.dm.ui.inspection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActionActivity;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.dm.ui.inspection.adapter.InspectionUserAdapter;
import com.huyingbao.dm.ui.inspection.store.InspectionDetailStore;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 巡检记录列表
 * Created by liujunfeng on 2017/3/30.
 */
public class InspectionListByUserFragment extends BaseRxFluxListFragment<MultiItemEntity> {
    @Inject
    InspectionDetailStore mInspectionDetailStore;

    public static InspectionListByUserFragment newInstance() {
        InspectionListByUserFragment fragment = new InspectionListByUserFragment();
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar(getString(R.string.title_inspection));
        super.afterCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mContext instanceof BaseRxFluxToolbarActionActivity) {
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_INSPECTION_SCAN, R.string.action_inspection_scan, R.drawable.ic_device_inspection, R.drawable.bg_c_so_primary_normal);
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
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_INSPECTION_SCAN, R.string.action_inspection_scan, R.drawable.ic_device_inspection, R.drawable.bg_c_so_primary_normal);
            //刷新
            isRefresh = true;
            mNextIndex = mFirstIndex;
            getDataList(mFirstIndex);
        }
    }

    @Override
    protected void initAdapter() {
        mAdapter = new InspectionUserAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRvContent.addItemDecoration(ViewUtils.getItemDecoration(mContext));
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getInspectionListByUser(index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_INSPECTION_LIST_BY_USER:
                showDataList(mInspectionDetailStore.getInspectionList());
                mAdapter.expandAll();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mInspectionDetailStore);
    }
}
