package com.huyingbao.dm.ui.checkdevice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.dm.ui.checkdevice.adapter.CheckDeviceAdapter;
import com.huyingbao.dm.ui.checkdevice.model.CheckDevice;
import com.huyingbao.dm.ui.checkdevice.store.CheckDeviceStore;
import com.huyingbao.dm.ui.maintenance.MaintenanceDetailActivity;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.repair.RepairDetailActivity;
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 设备列表可以根据状态筛选
 * Created by liujunfeng on 2017/3/29.
 */
public class CheckDeviceListFragment extends BaseRxFluxListFragment<CheckDevice> implements View.OnClickListener {
    @Inject
    CheckDeviceStore mCheckDeviceStore;

    private TextView mTvDeviceStatusNormal;
    private TextView mTvDeviceStatusRepair;
    private TextView mTvDeviceStatusRepairKeeping;

    private ArrayList<TextView> mTabViewList = new ArrayList<>();
    private Boolean mIsRepair = null;

    public static CheckDeviceListFragment newInstance() {
        CheckDeviceListFragment fragment = new CheckDeviceListFragment();
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar(getString(R.string.title_check));
        initAdapter();
        initRecyclerView();
        initSwipRefreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新数据
        refresh();
    }

    /**
     * 设置的头部view
     */
    @Override
    protected void initAdapter() {
        mAdapter = new CheckDeviceAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
        View deviceFilterView = initHeadTabView();

        mTabViewList.clear();
        mTabViewList.add(mTvDeviceStatusNormal);
        mTabViewList.add(mTvDeviceStatusRepair);
        mTabViewList.add(mTvDeviceStatusRepairKeeping);

        mAdapter.addHeaderView(deviceFilterView);
        mAdapter.setHeaderAndEmpty(true);
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.dp_20);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(mContext)
                .visibilityProvider((position, parent) -> position == 0)
                .color(mContext.getResources().getColor(R.color.divider))
                .margin(dimensionPixelOffset, dimensionPixelOffset)
                .sizeResId(R.dimen.dp_04)
                .showLastDivider();
        mRvContent.addItemDecoration(builder.build());
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CheckDevice checkDevice = mDataList.get(position);
                if (checkDevice.isIsRepair()) {
                    Repair repair = new Repair();
                    repair.setId(checkDevice.getId());
                    repair.setMachineId(checkDevice.getMachineId());
                    repair.setMachineCode(checkDevice.getCode());
                    repair.setMachineName(checkDevice.getName());
                    startActivity(RepairDetailActivity.newIntent(mContext, repair, Actions.TO_REPAIR_CHECK));
                } else {
                    Maintenance maintenance = new Maintenance();
                    maintenance.setId(checkDevice.getId());
                    maintenance.setMachineId(checkDevice.getMachineId());
                    maintenance.setMachineCode(checkDevice.getCode());
                    maintenance.setMachineName(checkDevice.getName());
                    startActivity(MaintenanceDetailActivity.newIntent(mContext, maintenance, Actions.TO_MAINTENANCE_CHECK));
                }
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getCheckListByUser(mIsRepair, index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_CHECK_LIST_BY_USER:
                showDataList(mCheckDeviceStore.getCheckDeviceList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mCheckDeviceStore);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_device_status_normal:
                mIsRepair = null;
                setViewSelected(0);
                refresh();
                break;
            case R.id.tv_device_status_repair:
                mIsRepair = true;
                setViewSelected(1);
                refresh();
                break;
            case R.id.tv_device_status_repair_keeping:
                mIsRepair = false;
                setViewSelected(2);
                refresh();
                break;
        }
    }

    /**
     * view选中
     *
     * @param position
     */
    private void setViewSelected(int position) {
        for (int i = 0; i < mTabViewList.size(); i++)
            mTabViewList.get(i).setSelected(i == position);
    }

    /**
     * 实例化头部选择view
     *
     * @return
     */
    private View initHeadTabView() {
        View deviceFilterView = LayoutInflater.from(mContext).inflate(R.layout.view_device_header, null, false);
        deviceFilterView.findViewById(R.id.tv_device_status_all).setVisibility(View.GONE);
        deviceFilterView.findViewById(R.id.tv_device_status_filter).setVisibility(View.GONE);

        mTvDeviceStatusNormal = deviceFilterView.findViewById(R.id.tv_device_status_normal);
        mTvDeviceStatusRepair = deviceFilterView.findViewById(R.id.tv_device_status_repair);
        mTvDeviceStatusRepairKeeping = deviceFilterView.findViewById(R.id.tv_device_status_repair_keeping);

        mTvDeviceStatusNormal.setText("全部待验收");
        mTvDeviceStatusRepair.setText("维修待验收");
        mTvDeviceStatusRepairKeeping.setText("保养待验收");

        mTvDeviceStatusNormal.setOnClickListener(this);
        mTvDeviceStatusRepair.setOnClickListener(this);
        mTvDeviceStatusRepairKeeping.setOnClickListener(this);

        mTvDeviceStatusNormal.setSelected(true);
        return deviceFilterView;
    }
}
