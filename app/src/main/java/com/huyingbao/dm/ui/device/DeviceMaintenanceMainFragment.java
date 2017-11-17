package com.huyingbao.dm.ui.device;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.SegmentTabLayout;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseFragment;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.devicelist.model.Device;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 保养记录主页面 Created by liujunfeng on 2017/5/31.
 */
public class DeviceMaintenanceMainFragment extends BaseFragment {
    @BindView(R.id.stl_maintenance_tab)
    SegmentTabLayout mStlMaintenanceTab;

    private Device mDevice;

    public static DeviceMaintenanceMainFragment newInstance(Device device) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.DEVICE, device);
        DeviceMaintenanceMainFragment fragment = new DeviceMaintenanceMainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_maintenance_main;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mDevice = getArguments().getParcelable(ActionsKeys.DEVICE);
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(DeviceMaintenanceListFragment.newInstance(mDevice));
        arrayList.add(DeviceMaintainPlanListFragment.newInstance(mDevice));
        mStlMaintenanceTab.setTabData(new String[]{"保养记录", "保养计划"}, getActivity(), R.id.fl_maintenance_content, arrayList);
    }
}
