package com.huyingbao.dm.ui.repair;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.dm.ui.repair.store.RepairDetailStore;
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
 * 维修详情主界面
 * Created by liujunfeng on 2017/5/31.
 */
public class RepairFragment extends BaseRxFluxFragment {
    @Inject
    RepairDetailStore mRepairDetailStore;
    @BindView(R.id.tv_repair_duration)
    TextView mTvRepairDuration;
    @BindView(R.id.tv_repair_informant_name)
    TextView mTvRepairInformantName;
    @BindView(R.id.tv_repair_informant_time)
    TextView mTvRepairInformantTime;
    @BindView(R.id.rv_device_part)
    RecyclerView mRvDevicePart;

    EditText mEtRepairScheme;
    EditText mEtRepairRemark;
    EditText mEtRepairFaultCause;
    TextView mTvDevicePart;

    TextView mTvRepairDevicePart;
    TextView mTvRepairDeviceDetail;
    private Repair mRepair;
    private DevicePartDeleteAdapter mAdapter;
    private ArrayList<DevicePart> mDevicePartList = new ArrayList<>();

    public static RepairFragment newInstance(@NonNull Repair repair) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.REPAIR, repair);
        RepairFragment fragment = new RepairFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_repair;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mRepair = getArguments().getParcelable(ActionsKeys.REPAIR);
        //获取该维修记录对应的备品备件
        mActionCreator.getDevicePartListByRepair(mRepair.getId(), 0);
        initActionBar(mRepair.getMachineCode() + "维修");
        initAdapter();
        showRepairDetail(mRepair);
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
            case Actions.GET_DEVICE_PART_LIST_BY_REPAIR:
                updateData(mRepairDetailStore.getDeviceResultPartList());
                break;
            case Actions.TO_DEVICE_PART_ADD_RESULT:
                updateData(change.getRxAction().get(ActionsKeys.SPAREPARTS));
                break;
            case Actions.TO_REPAIR_PAUSE_SHOW_DIALOG://显示暂停原因
                ContentFragment contentFragment = ContentFragment.newInstance(Actions.TO_REPAIR_PAUSE, null, "请输入暂停原因!");
                contentFragment.show(getChildFragmentManager(), ContentFragment.class.getSimpleName());
                break;
            case Actions.TO_REPAIR_PAUSE://点击暂停
                mActionCreator.repairPause(mContext,
                        mRepair.getId(),
                        mEtRepairFaultCause.getText().toString(),
                        mEtRepairScheme.getText().toString(),
                        mEtRepairRemark.getText().toString(),
                        change.getRxAction().get(ActionsKeys.CONTENT),
                        mDevicePartList);
                break;
            case Actions.TO_REPAIR_FINISH:
                mActionCreator.repairFinish(mContext,
                        mRepair.getId(),
                        mEtRepairFaultCause.getText().toString(),
                        mEtRepairScheme.getText().toString(),
                        mEtRepairRemark.getText().toString(),
                        null,
                        mDevicePartList);
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mRepairDetailStore);
    }

    /**
     * 到备件备件添加dialog
     */
    public void toRepairDevicePartAdd() {
        DevicePartAddFragment.newInstance(mRepair.getMachineType(), mDevicePartList)
                .show(getChildFragmentManager(), DevicePartAddFragment.class.getSimpleName());
    }

    public void toDeviceDetail() {
        Device device = new Device();
        device.setCode(mRepair.getMachineCode());
        device.setName(mRepair.getMachineName());
        device.setId(mRepair.getMachineId());
        device.setHeadImg(mRepair.getMachineHeadImg());
        startActivity(DeviceDetailActivity.newIntent(mContext, device));
    }

    /**
     * 显示设备详情
     *
     * @param repair
     */
    private void showRepairDetail(@NonNull Repair repair) {
        CommonUtils.setTextViewValue(mTvRepairInformantName, repair.getInformantName(), "报修人：");
        CommonUtils.setTextViewValue(mTvRepairInformantTime, TimeUtils.formatTime(repair.getCreateTime()), "报修日期：");
        mEtRepairFaultCause.setText(repair.getFaultCause());
        mEtRepairRemark.setText(repair.getRemark());
        mEtRepairScheme.setText(repair.getScheme());
        long duration = repair.getDuration() > 0
                ? (System.currentTimeMillis() - TimeUtils.stringToLong(repair.getContinueTime(), "yyyy-MM-dd HH:mm:ss")) / 1000 + repair.getDuration()
                : (System.currentTimeMillis() - TimeUtils.stringToLong(repair.getStartTime(), "yyyy-MM-dd HH:mm:ss")) / 1000;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())// 操作UI主要在UI线程
                .subscribe(aLong -> mTvRepairDuration.setText("维修用时：" + TimeUtils.secondConvertDayHourMin(duration + aLong)));
    }

    protected void initAdapter() {
        mAdapter = new DevicePartDeleteAdapter(mDevicePartList);
        mAdapter.addHeaderView(initHeaderView());
        mAdapter.addFooterView(initFooterView());
        mAdapter.setHeaderFooterEmpty(true, true);

        mRvDevicePart.setLayoutManager(new LinearLayoutManager(mContext));
        mRvDevicePart.setItemAnimator(new DefaultItemAnimator());// 设置item动画
        mRvDevicePart.setHasFixedSize(true);
        mRvDevicePart.setAdapter(mAdapter);
        mRvDevicePart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvDevicePart.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mDevicePartList.remove(position);
                mAdapter.notifyItemRemoved(position + mAdapter.getHeaderLayoutCount());
                int visibility = CommonUtils.isListAble(mDevicePartList) ? View.VISIBLE : View.GONE;
                mTvDevicePart.setVisibility(visibility);
            }
        });
    }

    @NonNull
    private View initHeaderView() {
        View viewHeader = LayoutInflater.from(mContext).inflate(R.layout.view_repair_header, null, false);
        mEtRepairScheme = viewHeader.findViewById(R.id.et_repair_scheme);
        mEtRepairRemark = viewHeader.findViewById(R.id.et_repair_remark);
        mEtRepairFaultCause = viewHeader.findViewById(R.id.et_repair_fault_cause);
        mTvDevicePart = viewHeader.findViewById(R.id.tv_device_part);
        return viewHeader;
    }

    @NonNull
    private View initFooterView() {
        View viewHeader = LayoutInflater.from(mContext).inflate(R.layout.view_repair_footer, null, false);
        mTvRepairDevicePart = viewHeader.findViewById(R.id.tv_repair_device_part);
        mTvRepairDeviceDetail = viewHeader.findViewById(R.id.tv_repair_device_detail);

        mTvRepairDevicePart.setOnClickListener(view -> toRepairDevicePartAdd());
        mTvRepairDeviceDetail.setOnClickListener(view -> toDeviceDetail());
        return viewHeader;
    }

    /**
     * 设置底部view
     */
    private void initBottom() {
        if (mRepair.getRepairMan() == mLocalStorageUtils.getUser().getId()) {
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_REPAIR_PAUSE_SHOW_DIALOG, R.string.action_repair_pause, R.drawable.ic_action_pause, R.drawable.bg_c_so_primary_normal);
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView2(Actions.TO_REPAIR_FINISH, R.string.action_repair_finish, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
        } else {//非操作用户不显示操作按钮
            ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
        }
    }

    /**
     * 更新数据
     *
     * @param devicePartList
     */
    private void updateData(List<DevicePart> devicePartList) {
        mDevicePartList.clear();
        if (CommonUtils.isListAble(devicePartList)) {
            mDevicePartList.addAll(devicePartList);
            mAdapter.notifyDataSetChanged();
            mTvDevicePart.setVisibility(View.VISIBLE);
        } else {
            mTvDevicePart.setVisibility(View.GONE);
        }
    }
}
