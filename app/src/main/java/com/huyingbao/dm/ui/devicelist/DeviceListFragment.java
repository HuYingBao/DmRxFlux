package com.huyingbao.dm.ui.devicelist;

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
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.device.DeviceDetailActivity;
import com.huyingbao.dm.ui.devicelist.adapter.DeviceAdapter;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.devicelist.model.DeviceType;
import com.huyingbao.dm.ui.devicelist.store.DeviceListStore;
import com.huyingbao.rxflux2.util.CommonUtils;
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
public class DeviceListFragment extends BaseRxFluxListFragment<Device> implements View.OnClickListener {
    @Inject
    DeviceListStore mDeviceListStore;
    private String mArrayStatus = "";//设备状态
    private String mArrayType = "";//设备类型
    private int mDeviceStatus;

    private TextView mTvDeviceStatusAll;
    private TextView mTvDeviceStatusNormal;
    private TextView mTvDeviceStatusRepair;
    private TextView mTvDeviceStatusRepairKeeping;
    private TextView mTvDeviceStatusFilter;
    private ArrayList<TextView> mTabViewList = new ArrayList<>();
    private int mPosition;

    public static DeviceListFragment newInstance(int deviceStatus) {
        Bundle bundle = new Bundle();
        bundle.putInt(ActionsKeys.STATUS, deviceStatus);
        DeviceListFragment fragment = new DeviceListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mDeviceStatus = getArguments().getInt(ActionsKeys.STATUS);
        mArrayStatus = getDeviceArray(mDeviceStatus);
        initActionBar(getString(R.string.title_device_list));

        super.afterCreate(savedInstanceState);
        initFilterView(mDeviceStatus);
    }

    @Override
    public void onResume() {
        super.onResume();
        //报修之后刷新
        try {
            if (mAppStore.isApplyRepair()) {
                mAppStore.setApplyRepair(false);
                mDataList.remove(mPosition);
                mAdapter.notifyDataSetChanged();
//                mAdapter.notifyItemRemoved(mPosition);
//                // 如果移除的是最后一个，忽略
//                if (mPosition != mDataList.size()) {
//                    mAdapter.notifyItemRangeChanged(mPosition, mDataList.size() - mPosition);
//                }
            }
        } catch (Exception e) {
            //刷新
            isRefresh = true;
            mNextIndex = mFirstIndex;
            getDataList(mFirstIndex);
        }
    }

    /**
     * 设置的头部view
     */
    @Override
    protected void initAdapter() {
        mAdapter = new DeviceAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
        View deviceFilterView = initHeadTabView();

        mTabViewList.clear();
        mTabViewList.add(mTvDeviceStatusAll);
        mTabViewList.add(mTvDeviceStatusNormal);
        mTabViewList.add(mTvDeviceStatusRepair);
        mTabViewList.add(mTvDeviceStatusRepairKeeping);
        mTabViewList.add(mTvDeviceStatusFilter);

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
                mPosition = position;
                startActivity(DeviceDetailActivity.newIntent(mContext, mDataList.get(mPosition)));
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getDeviceList(mArrayType, mArrayStatus, index);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_DEVICE_LIST:
                showDataList(mDeviceListStore.getDeviceList());
                break;
            case Actions.TO_FILTER_DEVICE:
                mArrayStatus = getArrayValue(mDeviceListStore.getDeviceStatusList());
                mArrayType = getArrayValue(mDeviceListStore.getDeviceTypeList());
                refresh();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDeviceListStore);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_device_status_all:
                setViewSelected(0);
                selectStatus(Constants.STATUS_DEVICE_ALL);
                mArrayStatus = "";
                mArrayType = "";
                refresh();
                break;
            case R.id.tv_device_status_normal:
                setViewSelected(1);
                selectStatus(Constants.STATUS_DEVICE_NORMAL);
                mArrayStatus = Constants.STATUS_DEVICE_NORMAL + "";
                mArrayType = "";
                refresh();
                break;
            case R.id.tv_device_status_repair:
                setViewSelected(2);
                selectStatus(Constants.STATUS_DEVICE_REPAIR);
                mArrayStatus = Constants.STATUS_DEVICE_REPAIR + "";
                mArrayType = "";
                refresh();
                break;
            case R.id.tv_device_status_repair_keeping:
                setViewSelected(3);
                selectStatus(Constants.STATUS_DEVICE_REPAIR_KEEPING);
                mArrayStatus = Constants.STATUS_DEVICE_REPAIR_KEEPING + "";
                mArrayType = "";
                refresh();
                break;
            case R.id.tv_device_status_filter:
                setViewSelected(4);
                DeviceFilterFragment deviceFilterFragment = DeviceFilterFragment.newInstance();
                deviceFilterFragment.show(getChildFragmentManager(), DeviceListFragment.class.getSimpleName());
                break;
        }
    }

    /**
     * 当状态大于1000,不初始化选中状态
     * 初始化选中状态
     *
     * @param deviceStatus
     */
    private void initFilterView(int deviceStatus) {
        selectStatus(deviceStatus);
        switch (deviceStatus) {
            case Constants.STATUS_DEVICE_NORMAL:
                setViewSelected(1);
                break;
            case Constants.STATUS_DEVICE_REPAIR:
                setViewSelected(2);
                break;
            case Constants.STATUS_DEVICE_REPAIR_KEEPING:
                setViewSelected(3);
                break;
            default:
                mDeviceListStore.getDeviceStatusList();
                setViewSelected(4);
                break;
        }
    }

    /**
     * 修改筛选结果
     *
     * @param deviceStatus
     */
    private void selectStatus(int deviceStatus) {
        //修改选中设备状态
        if (CommonUtils.isListAble(mDeviceListStore.getDeviceStatusList()))
            for (DeviceType deviceType : mDeviceListStore.getDeviceStatusList())
                deviceType.setSelected(deviceType.getId() == deviceStatus);
        //修改选中设备类型
        if (CommonUtils.isListAble(mDeviceListStore.getDeviceTypeList()))
            for (DeviceType deviceType : mDeviceListStore.getDeviceTypeList())
                deviceType.setSelected(false);
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
     * 获取list中的选中值
     *
     * @param deviceTypeList
     * @return "1,2,3"或者"1"
     */
    @NonNull
    private String getArrayValue(List<DeviceType> deviceTypeList) {
        StringBuffer typeBuffer = new StringBuffer();
        if (CommonUtils.isListAble(deviceTypeList))
            for (DeviceType deviceType : deviceTypeList)
                if (deviceType.isSelected())
                    typeBuffer.append(typeBuffer.length() == 0 ? getDeviceArray(deviceType.getId()) : ("," + getDeviceArray(deviceType.getId())));
        return typeBuffer.toString();
    }

    /**
     * 根据传入的status转换成对应的status array String
     *
     * @param deviceStatus
     * @return
     */
    @NonNull
    private String getDeviceArray(int deviceStatus) {
        switch (deviceStatus) {
            case Constants.STATUS_DEVICE_ERROR:
                return Constants.STATUS_DEVICE_DISABLE + "," + Constants.STATUS_DEVICE_SCRAP;
            case Constants.STATUS_DEVICE_CHECK:
                return Constants.STATUS_DEVICE_CHECK_MAINTENANCE + "," + Constants.STATUS_DEVICE_CHECK_REPAIR;
            default:
                return deviceStatus + "";
        }
    }

    /**
     * 实例化头部选择view
     *
     * @return
     */
    @NonNull
    private View initHeadTabView() {
        View deviceFilterView = LayoutInflater.from(mContext).inflate(R.layout.view_device_header, null, false);
        mTvDeviceStatusAll = deviceFilterView.findViewById(R.id.tv_device_status_all);
        mTvDeviceStatusNormal = deviceFilterView.findViewById(R.id.tv_device_status_normal);
        mTvDeviceStatusRepair = deviceFilterView.findViewById(R.id.tv_device_status_repair);
        mTvDeviceStatusRepairKeeping = deviceFilterView.findViewById(R.id.tv_device_status_repair_keeping);
        mTvDeviceStatusFilter = deviceFilterView.findViewById(R.id.tv_device_status_filter);

        mTvDeviceStatusAll.setOnClickListener(this);
        mTvDeviceStatusNormal.setOnClickListener(this);
        mTvDeviceStatusRepair.setOnClickListener(this);
        mTvDeviceStatusRepairKeeping.setOnClickListener(this);
        mTvDeviceStatusFilter.setOnClickListener(this);
        return deviceFilterView;
    }
}
