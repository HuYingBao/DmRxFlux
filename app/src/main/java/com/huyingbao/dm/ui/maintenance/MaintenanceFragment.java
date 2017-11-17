package com.huyingbao.dm.ui.maintenance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActionActivity;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.common.ContentFragment;
import com.huyingbao.dm.ui.common.DevicePartAddFragment;
import com.huyingbao.dm.ui.common.adapter.DevicePartDeleteAdapter;
import com.huyingbao.dm.ui.device.DeviceDetailActivity;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.dm.ui.maintenance.adapter.MaintainItemAdapter;
import com.huyingbao.dm.ui.maintenance.model.MaintainItem;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.maintenance.store.MaintenanceDetailStore;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 保养详情主界面
 * Created by liujunfeng on 2017/5/31.
 */
public class MaintenanceFragment extends BaseRxFluxFragment {
    @Inject
    MaintenanceDetailStore mMaintenanceDetailStore;
    @BindView(R.id.tv_maintenance_duration)
    TextView mTvMaintenanceDuration;
    @BindView(R.id.tv_maintenance_user)
    TextView mTvMaintenanceUser;
    @BindView(R.id.tv_maintenance_plan_date)
    TextView mTvMaintenancePlanDate;
    @BindView(R.id.rv_maintain_item)
    RecyclerView mRvMaintainItem;

    TextView mTvMaintainItem;
    EditText mEtMaintenanceRemark;
    EditText mEtMaintenancePreExplain;

    TextView mTvDevicePart;
    RecyclerView mRvDevicePart;
    TextView mTvMaintenanceDevicePart;
    TextView mTvMaintenanceDeviceDetail;

    private Maintenance mMaintenance;
    private DevicePartDeleteAdapter mDevicePartDeleteAdapter;
    private ArrayList<DevicePart> mDevicePartList = new ArrayList<>();

    private MaintainItemAdapter mMaintainItemAdapter;
    private List<MultiItemEntity> mMaintainItemTitleList = new ArrayList<>();
    private int mPosition;//当前正在操作的保养项

    public static MaintenanceFragment newInstance(@NonNull Maintenance maintenance) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.MAINTENANCE, maintenance);
        MaintenanceFragment fragment = new MaintenanceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_maintenance;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mMaintenance = getArguments().getParcelable(ActionsKeys.MAINTENANCE);
        //获取该保养记录对应的备品备件
        mActionCreator.getDevicePartListByMaintain(mMaintenance.getId(), 0);
        //获取该保养记录对应的保养项
        mActionCreator.getMaintainItemListByMaintain(mMaintenance.getId());
        initActionBar(mMaintenance.getMachineCode() + "保养");
        initAdapter();
        showMaintenanceDetail(mMaintenance);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!(mContext instanceof BaseRxFluxToolbarActionActivity)) return;
        initBottom();
    }

    /**
     * 隐藏状态改变回调方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) return;
        if (!(mContext instanceof BaseRxFluxToolbarActionActivity)) return;
        initBottom();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_DEVICE_PART_LIST_BY_MAINTAIN:
                updateDevicePartData(mMaintenanceDetailStore.getDeviceResultPartList());
                break;
            case Actions.GET_MAINTAIN_ITEM_LIST_BY_MAINTAIN:
                updateMainItemTitleData(mMaintenanceDetailStore.getMaintainItemTitleList());
                break;
            case Actions.TO_DEVICE_PART_ADD_RESULT:
                updateDevicePartData(change.getRxAction().get(ActionsKeys.SPAREPARTS));
                break;
            case Actions.TO_MAINTAIN_ITEM_SHOW_PAUSE:
                mPosition = change.getRxAction().get(ActionsKeys.POSITION);
                ContentFragment contentFragment = ContentFragment.newInstance(Actions.TO_MAINTAIN_ITEM_PAUSE, null, "请输入保养项未通过原因！");
                contentFragment.show(getChildFragmentManager(), ContentFragment.class.getSimpleName());
                break;
            case Actions.TO_MAINTAIN_ITEM_PAUSE:
                String content = change.getRxAction().get(ActionsKeys.CONTENT);
                ((MaintainItem) mMaintainItemTitleList.get(mPosition)).setResultCause(content);
                break;
            case Actions.TO_MAINTENANCE_PAUSE_SHOW_DIALOG:
                contentFragment = ContentFragment.newInstance(Actions.TO_MAINTENANCE_PAUSE, null, "请输入暂停原因！");
                contentFragment.show(getChildFragmentManager(), ContentFragment.class.getSimpleName());
                break;
            case Actions.TO_MAINTENANCE_PAUSE:
                mActionCreator.maintainPause(mContext,
                        mMaintenance.getId(),
                        mEtMaintenancePreExplain.getText().toString(),
                        mEtMaintenanceRemark.getText().toString(),
                        change.getRxAction().get(ActionsKeys.CONTENT),
                        getMaintainItems(),
                        mDevicePartList);
                break;
            case Actions.TO_MAINTENANCE_FINISH:
                mActionCreator.maintainFinish(mContext,
                        mMaintenance.getId(),
                        mEtMaintenancePreExplain.getText().toString(),
                        mEtMaintenanceRemark.getText().toString(),
                        null,
                        getMaintainItems(),
                        mDevicePartList);
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMaintenanceDetailStore);
    }

    /**
     * 到备件备件添加dialog
     */
    public void toMaintenanceDevicePartAdd() {
        DevicePartAddFragment.newInstance(mMaintenance.getMachineType(), mDevicePartList)
                .show(getChildFragmentManager(), DevicePartAddFragment.class.getSimpleName());
    }

    /**
     * 到设备详情
     */
    public void toDeviceDetail() {
        Device device = new Device();
        device.setCode(mMaintenance.getMachineCode());
        device.setName(mMaintenance.getMachineName());
        device.setId(mMaintenance.getMachineId());
        device.setHeadImg(mMaintenance.getMachineHeadImg());
        startActivity(DeviceDetailActivity.newIntent(mContext, device));
    }

    /**
     * 显示设备详情
     *
     * @param maintenance
     */
    private void showMaintenanceDetail(@NonNull Maintenance maintenance) {
        CommonUtils.setTextViewValue(mTvMaintenanceUser, maintenance.getMaintainerName(), "保养员：");
        CommonUtils.setTextViewValue(mTvMaintenancePlanDate, maintenance.getPlanDate(), "计划日期：");
        mEtMaintenancePreExplain.setText(maintenance.getPreExplain());
        mEtMaintenanceRemark.setText(maintenance.getRemark());

        long duration = maintenance.getDuration() > 0
                ? (System.currentTimeMillis() - TimeUtils.stringToLong(maintenance.getContinueTime(), "yyyy-MM-dd HH:mm:ss")) / 1000 + maintenance.getDuration()
                : (System.currentTimeMillis() - TimeUtils.stringToLong(maintenance.getStartTime(), "yyyy-MM-dd HH:mm:ss")) / 1000;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())// 操作UI主要在UI线程
                .subscribe(aLong -> mTvMaintenanceDuration.setText("保养用时：" + TimeUtils.secondConvertDayHourMin(duration + aLong)));
    }

    protected void initAdapter() {
        mMaintainItemAdapter = new MaintainItemAdapter(mMaintainItemTitleList);
        mMaintainItemAdapter.setChangeAble(true);
        mMaintainItemAdapter.addHeaderView(initHeadView());
        mMaintainItemAdapter.addFooterView(initFooterView());
        mMaintainItemAdapter.setHeaderFooterEmpty(true, true);

        mRvMaintainItem.setLayoutManager(new LinearLayoutManager(mContext));
        mRvMaintainItem.setHasFixedSize(true);
        mRvMaintainItem.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRvMaintainItem.setAdapter(mMaintainItemAdapter);
    }

    @NonNull
    private View initHeadView() {
        View viewHeader = LayoutInflater.from(mContext).inflate(R.layout.view_maintain_item_header, null, false);
        mTvMaintainItem = viewHeader.findViewById(R.id.tv_maintain_item);
        mEtMaintenanceRemark = viewHeader.findViewById(R.id.et_maintenance_remark);
        mEtMaintenancePreExplain = viewHeader.findViewById(R.id.et_maintenance_pre_explain);
        return viewHeader;
    }

    @NonNull
    private View initFooterView() {
        View viewFooter = LayoutInflater.from(mContext).inflate(R.layout.view_maintain_item_footer, null, false);
        mTvDevicePart = viewFooter.findViewById(R.id.tv_device_part);
        mRvDevicePart = viewFooter.findViewById(R.id.rv_device_part);
        mTvMaintenanceDevicePart = viewFooter.findViewById(R.id.tv_maintenance_device_part);
        mTvMaintenanceDeviceDetail = viewFooter.findViewById(R.id.tv_maintenance_device_detail);

        mTvMaintenanceDevicePart.setOnClickListener(view -> toMaintenanceDevicePartAdd());
        mTvMaintenanceDeviceDetail.setOnClickListener(view -> toDeviceDetail());

        mDevicePartDeleteAdapter = new DevicePartDeleteAdapter(mDevicePartList);
        mRvDevicePart.setLayoutManager(new LinearLayoutManager(mContext));
        mRvDevicePart.setHasFixedSize(true);
        mRvDevicePart.setAdapter(mDevicePartDeleteAdapter);
        mRvDevicePart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvDevicePart.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mDevicePartList.remove(position);
                mDevicePartDeleteAdapter.notifyItemRemoved(position);
                int visibility = CommonUtils.isListAble(mDevicePartList) ? View.VISIBLE : View.GONE;
                mTvDevicePart.setVisibility(visibility);
                mRvDevicePart.setVisibility(visibility);
            }
        });
        return viewFooter;
    }

    /**
     * 设置底部view
     */
    private void initBottom() {
        if (mMaintenance.getMaintainerId() == mLocalStorageUtils.getUser().getId()) {
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_MAINTENANCE_PAUSE_SHOW_DIALOG, R.string.action_maintenance_pause, R.drawable.ic_action_pause, R.drawable.bg_c_so_primary_normal);
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView2(Actions.TO_MAINTENANCE_FINISH, R.string.action_maintenance_finish, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
        } else {//非操作用户不显示操作按钮
            ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
        }
    }

    /**
     * 更新数据
     *
     * @param devicePartList
     */
    private void updateDevicePartData(List<DevicePart> devicePartList) {
        mDevicePartList.clear();
        if (CommonUtils.isListAble(devicePartList)) {
            mDevicePartList.addAll(devicePartList);
            mDevicePartDeleteAdapter.notifyDataSetChanged();
            mTvDevicePart.setVisibility(View.VISIBLE);
            mRvDevicePart.setVisibility(View.VISIBLE);
        } else {
            mTvDevicePart.setVisibility(View.GONE);
            mRvDevicePart.setVisibility(View.GONE);
        }
    }

    /**
     * 更新数据
     *
     * @param maintainItemTitleList
     */
    private void updateMainItemTitleData(List<MultiItemEntity> maintainItemTitleList) {
        mMaintainItemTitleList.clear();
        if (CommonUtils.isListAble(maintainItemTitleList)) {
            mMaintainItemTitleList.addAll(maintainItemTitleList);
            mMaintainItemAdapter.notifyDataSetChanged();
            mMaintainItemAdapter.expandAll();
            mTvMaintainItem.setVisibility(View.VISIBLE);
        } else {
            mTvMaintainItem.setVisibility(View.GONE);
        }
    }

    /**
     * 获取maintainItem list数据
     *
     * @return
     */
    @Nullable
    private List<MaintainItem> getMaintainItems() {
        List<MaintainItem> maintainItemList = null;
        if (CommonUtils.isListAble(mMaintainItemTitleList)) {
            maintainItemList = new ArrayList<>();
            for (MultiItemEntity multiItemEntity : mMaintainItemTitleList) {
                if (multiItemEntity instanceof MaintainItem) {
                    maintainItemList.add((MaintainItem) multiItemEntity);
                }
            }
        }
        return maintainItemList;
    }
}
