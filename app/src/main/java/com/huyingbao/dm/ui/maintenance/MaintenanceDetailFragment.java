package com.huyingbao.dm.ui.maintenance;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActionActivity;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.common.ContentFragment;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.maintenance.store.MaintenanceDetailStore;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 保养详情主界面
 * Created by liujunfeng on 2017/5/31.
 */
public class MaintenanceDetailFragment extends BaseRxFluxFragment {
    @Inject
    MaintenanceDetailStore mMaintenanceDetailStore;
    @BindView(R.id.tv_maintenance_device_code)
    TextView mTvMaintenanceDeviceCode;
    @BindView(R.id.tv_maintenance_device_name)
    TextView mTvMaintenanceDeviceName;
    @BindView(R.id.tv_maintenance_time)
    TextView mTvMaintenanceTime;
    @BindView(R.id.tv_maintenance_user)
    TextView mTvMaintenanceUser;
    @BindView(R.id.tv_maintenance_duration)
    TextView mTvMaintenanceDuration;
    @BindView(R.id.tv_maintenance_plan_date)
    TextView mTvMaintenancePlanDate;
    @BindView(R.id.tv_maintenance_result)
    TextView mTvMaintenanceResult;
    @BindView(R.id.iv_maintenance_result)
    ImageView mIvMaintenanceResult;
    @BindView(R.id.tv_maintenance_rank)
    TextView mTvMaintenanceRank;
    @BindView(R.id.tv_maintenance_fault_cause)
    TextView mTvMaintenanceFaultCause;
    @BindView(R.id.tv_maintenance_pause_explain)
    TextView mTvMaintenancePauseExplain;
    private Maintenance mMaintenance;
    private String mAction;

    public static MaintenanceDetailFragment newInstance(@NonNull Maintenance maintenance, @NonNull String action) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.MAINTENANCE, maintenance);
        bundle.putString(ActionsKeys.BASE_ACTION, action);
        MaintenanceDetailFragment fragment = new MaintenanceDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_maintenance_detail;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mAction = getArguments().getString(ActionsKeys.BASE_ACTION);
        mMaintenance = getArguments().getParcelable(ActionsKeys.MAINTENANCE);
        //获取保养详情
        mActionCreator.getMaintenanceDetail(mContext, mMaintenance.getId());
        initActionBar(getTitle());
        showMaintenanceDetail(mMaintenance);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView(mMaintenance);
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
        initView(mMaintenance);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_MAINTENANCE_DETAIL://获取保养详情
                mMaintenance = mMaintenanceDetailStore.getMaintenance();
                showMaintenanceDetail(mMaintenance);
                initView(mMaintenance);
                break;
            case Actions.TO_MAINTENANCE_START://调用开始保养
                mActionCreator.maintainStart(mContext, mMaintenance.getId());
                break;
            case Actions.TO_MAINTENANCE_RESTART://调用重新开始保养
                mActionCreator.maintainRestart(mContext, mMaintenance.getId());
                break;
            case Actions.TO_MAINTENANCE_PASS://验收通过
                mActionCreator.maintainApprove(mContext, mMaintenance.getId(), null);
                break;
            case Actions.TO_MAINTENANCE_FAIL_SHOW_DIALOG://显示验收不通过对话框
                ContentFragment contentFragment = ContentFragment.newInstance(Actions.TO_MAINTENANCE_FAIL, null, "请输入验收不通过原因！");
                contentFragment.show(getChildFragmentManager(), ContentFragment.class.getSimpleName());
                break;
            case Actions.TO_MAINTENANCE_FAIL://验收不通过
                mActionCreator.maintainReject(mContext, mMaintenance.getId(), change.getRxAction().get(ActionsKeys.CONTENT));
                break;
            case Actions.MAINTAIN_APPROVE:
            case Actions.MAINTAIN_REJECT:
                ((Activity) mContext).finish();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMaintenanceDetailStore);
    }

    /**
     * 显示设备详情
     *
     * @param maintenance
     */
    private void showMaintenanceDetail(@NonNull Maintenance maintenance) {
        CommonUtils.setTextViewValue(mTvMaintenanceDeviceCode, maintenance.getMachineCode(), "设备编号：");
        CommonUtils.setTextViewValue(mTvMaintenanceDeviceName, maintenance.getMachineName(), "设备名称：");
        CommonUtils.setTextViewValue(mTvMaintenanceTime, TimeUtils.formatTime(maintenance.getStartTime()), "保养时间：");
        CommonUtils.setTextViewValue(mTvMaintenanceUser, maintenance.getMaintainerName(), "保养员：");
        CommonUtils.setTextViewValue(mTvMaintenancePlanDate, maintenance.getPlanDate(), "计划日期：");
        mTvMaintenanceResult.setText(CommonUtils.getResultName(maintenance.getResult()));
        mTvMaintenanceResult.setTextColor(getResources().getColor(CommonUtils.getResultColor(maintenance.getResult())));
        mTvMaintenanceDuration.setText("保养用时：" + TimeUtils.secondConvertDayHourMin(maintenance.getDuration()));
        mTvMaintenanceRank.setText(maintenance.getRank() + "");
        mTvMaintenanceRank.setBackgroundResource(CommonUtils.getRankImg(maintenance.getRank()));
        mIvMaintenanceResult.setImageResource(CommonUtils.getResultSmallImg(maintenance.getResult()));

        String faultCause = TextUtils.isEmpty(maintenance.getPreExplain()) ? "未知" : maintenance.getPreExplain();
        mTvMaintenanceFaultCause.setText(Html.fromHtml("保养前情况：<br/><font color=\"#333333\">" + faultCause + "</font>"));

        Spanned pauseExplain = null;
        if (!TextUtils.isEmpty(maintenance.getPauseExplain()))
            pauseExplain = Html.fromHtml("暂停原因：<br/><font color=\"#333333\">" + maintenance.getPauseExplain() + "</font>");
        CommonUtils.setTextViewValue(mTvMaintenancePauseExplain, pauseExplain);
    }

    /**
     * 到保养项使用记录
     */
    @OnClick(R.id.tv_maintenance_item)
    public void toMaintainItem() {
        mActionCreator.postLocalAction(Actions.TO_MAINTAIN_ITEM_LIST, ActionsKeys.MAINTENANCE, mMaintenance);
    }

    /**
     * 到保养备品使用记录
     */
    @OnClick(R.id.tv_maintenance_device_part)
    public void toDevicePart() {
        mActionCreator.postLocalAction(Actions.TO_DEVICE_PART_LIST, ActionsKeys.MAINTENANCE, mMaintenance);
    }

    /**
     * 到保养验收记录
     */
    @OnClick(R.id.tv_maintenance_check)
    public void toMaintenanceCheck() {
        mActionCreator.postLocalAction(Actions.TO_CHECK_LIST, ActionsKeys.MAINTENANCE, mMaintenance);
    }

    /**
     * 获取标题
     *
     * @return
     */
    @Nullable
    private String getTitle() {
        String title = null;
        switch (mAction) {
            case Actions.TO_MAINTENANCE_ITEM:
                title = mMaintenance.getMachineCode() + "保养";
                break;
            case Actions.TO_MAINTENANCE_DETAIL:
                title = getString(R.string.title_maintenance_detail) + mMaintenance.getId();
                break;
            case Actions.TO_MAINTENANCE_CHECK:
                title = mMaintenance.getMachineCode() + "验收";
                break;
        }
        return title;
    }


    /**
     * 初始化view
     */
    private void initView(Maintenance maintenance) {
        if (!(mContext instanceof BaseRxFluxToolbarActionActivity)) return;
        switch (mAction) {
            case Actions.TO_MAINTENANCE_ITEM:
                initActionBar(maintenance.getMachineCode() + "保养");
                if (maintenance.getResult() == Constants.TYPE_RESULT_NO_START) {
                    ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_MAINTENANCE_START, R.string.action_maintenance_start, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
                } else if (maintenance.getResult() == Constants.TYPE_RESULT_PAUSE) {
                    if (maintenance.getMaintainerId() == mLocalStorageUtils.getUser().getId()) {
                        ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_MAINTENANCE_RESTART, R.string.action_maintenance_restart, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
                    } else {//非操作用户不显示操作按钮
                        ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
                    }
                } else {
                    ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
                }
                break;
            case Actions.TO_MAINTENANCE_DETAIL:
                initActionBar(getString(R.string.title_maintenance_detail) + maintenance.getId());
                ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
                break;
            case Actions.TO_MAINTENANCE_CHECK:
                initActionBar(maintenance.getMachineCode() + "验收");
                if (maintenance.getResult() == Constants.TYPE_RESULT_CHECK) {
                    ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_MAINTENANCE_FAIL_SHOW_DIALOG, R.string.action_check_fail, R.drawable.ic_action_fail, R.drawable.bg_c_so_primary_normal);
                    ((BaseRxFluxToolbarActionActivity) mContext).initBottomView2(Actions.TO_MAINTENANCE_PASS, R.string.action_check_pass, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
                } else {
                    ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
                }
                break;
        }
    }
}
