package com.huyingbao.dm.ui.check;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.check.adapter.CheckAdapter;
import com.huyingbao.dm.ui.check.model.Check;
import com.huyingbao.dm.ui.check.store.CheckStore;
import com.huyingbao.dm.ui.common.ShowInfoFragment;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 验收记录列表
 * (一:维修验收记录,二:保养验收记录)
 * Created by liujunfeng on 2017/3/30.
 */
public class CheckListFragment extends BaseRxFluxListFragment<Check> {
    @Inject
    CheckStore mCheckStore;

    private int mId;

    /**
     * @param id         维修记录id或者保养记录id
     * @param httpAction 调用哪个接口,区分获取维修验收记录或者保养验收记录
     * @return
     */
    public static CheckListFragment newInstance(int id, @NonNull String httpAction) {
        Bundle bundle = new Bundle();
        bundle.putInt(ActionsKeys.ID, id);
        bundle.putString(ActionsKeys.BASE_ACTION, httpAction);
        CheckListFragment fragment = new CheckListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar(getString(R.string.title_check_list));
        mId = getArguments().getInt(ActionsKeys.ID);
        super.afterCreate(savedInstanceState);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new CheckAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRvContent.addItemDecoration(ViewUtils.getItemDecoration(mContext));
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Check check = mDataList.get(position);
                if (check.isCheckResult()) return;
                String checkOpinion = check.getCheckOpinion();
                ShowInfoFragment.newInstance("验收未通过原因", TextUtils.isEmpty(checkOpinion) ? "未知原因！" : checkOpinion)
                        .show(getChildFragmentManager(), ShowInfoFragment.class.getSimpleName());
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        switch (getArguments().getString(ActionsKeys.BASE_ACTION)) {
            default:
            case Actions.GET_CHECK_LIST_BY_REPAIR:
                mActionCreator.getCheckListByRepair(mId, index);
                break;
            case Actions.GET_CHECK_LIST_BY_MAINTAIN:
                mActionCreator.getCheckListByMaintain(mId, index);
                break;
        }
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_CHECK_LIST_BY_MAINTAIN:
            case Actions.GET_CHECK_LIST_BY_REPAIR:
                showDataList(mCheckStore.getCheckList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mCheckStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mCheckStore);
    }

}
