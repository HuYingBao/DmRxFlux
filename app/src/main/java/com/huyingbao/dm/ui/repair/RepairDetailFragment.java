package com.huyingbao.dm.ui.repair;

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
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.dm.ui.repair.store.RepairDetailStore;
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
 * 维修详情主界面
 * Created by liujunfeng on 2017/5/31.
 */
public class RepairDetailFragment extends BaseRxFluxFragment {
    @Inject
    RepairDetailStore mRepairDetailStore;
    @BindView(R.id.tv_repair_device_code)
    TextView mTvRepairDeviceCode;
    @BindView(R.id.tv_repair_device_name)
    TextView mTvRepairDeviceName;
    @BindView(R.id.tv_repair_informant_name)
    TextView mTvRepairInformantName;
    @BindView(R.id.tv_repair_informant_time)
    TextView mTvRepairInformantTime;
    @BindView(R.id.tv_repair_user)
    TextView mTvRepairUser;
    @BindView(R.id.tv_repair_duration)
    TextView mTvRepairDuration;
    @BindView(R.id.tv_repair_result)
    TextView mTvRepairResult;
    @BindView(R.id.iv_repair_result)
    ImageView mIvRepairResult;
    @BindView(R.id.tv_repair_type)
    TextView mTvRepairType;
    @BindView(R.id.tv_repair_fault_cause)
    TextView mTvRepairFaultCause;
    @BindView(R.id.tv_repair_remark)
    TextView mTvRepairRemark;
    @BindView(R.id.tv_repair_pause_explain)
    TextView mTvRepairPauseExplain;
    @BindView(R.id.tv_repair_scheme)
    TextView mTvRepairScheme;
    @BindView(R.id.tv_repair_check_opinion)
    TextView mTvRepairCheckOpinion;
    private Repair mRepair;
    private String mAction;

    public static RepairDetailFragment newInstance(@NonNull Repair repair, @NonNull String action) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.REPAIR, repair);
        bundle.putString(ActionsKeys.BASE_ACTION, action);
        RepairDetailFragment fragment = new RepairDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_repair_detail;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mAction = getArguments().getString(ActionsKeys.BASE_ACTION);
        mRepair = getArguments().getParcelable(ActionsKeys.REPAIR);
        //获取维修详情
        mActionCreator.getRepairDetail(mContext, mRepair.getId());
        showRepairDetail(mRepair);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView(mRepair);
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
        initView(mRepair);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_REPAIR_DETAIL://获取维修详情
                mRepair = mRepairDetailStore.getRepair();
                showRepairDetail(mRepair);
                initView(mRepair);
                break;
            case Actions.TO_REPAIR_START://调用开始维修
                mActionCreator.repairStart(mContext, mRepair.getId());
                break;
            case Actions.TO_REPAIR_RESTART://调用重新开始维修
                mActionCreator.repairRestart(mContext, mRepair.getId());
                break;
            case Actions.TO_REPAIR_PASS://验收通过
                mActionCreator.repairApprove(mContext, mRepair.getId(), null);
                break;
            case Actions.TO_REPAIR_FAIL_SHOW_DIALOG://显示验收不通过对话框
                ContentFragment contentFragment = ContentFragment.newInstance(Actions.TO_REPAIR_FAIL, null, "请输入验收不通过原因！");
                contentFragment.show(getChildFragmentManager(), ContentFragment.class.getSimpleName());
                break;
            case Actions.TO_REPAIR_FAIL://验收不通过
                mActionCreator.repairReject(mContext, mRepair.getId(), change.getRxAction().get(ActionsKeys.CONTENT));
                break;
            case Actions.REPAIR_APPROVE:
            case Actions.REPAIR_REJECT:
                ((Activity) mContext).finish();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mRepairDetailStore);
    }

    /**
     * 显示设备详情
     *
     * @param repair
     */
    private void showRepairDetail(@NonNull Repair repair) {
        CommonUtils.setTextViewValue(mTvRepairDeviceCode, repair.getMachineCode(), "设备编号：");
        CommonUtils.setTextViewValue(mTvRepairDeviceName, repair.getMachineName(), "设备名称：");
        CommonUtils.setTextViewValue(mTvRepairInformantName, repair.getInformantName(), "报修人：");
        CommonUtils.setTextViewValue(mTvRepairInformantTime, TimeUtils.formatTime(repair.getCreateTime()), "报修日期：");
        CommonUtils.setTextViewValue(mTvRepairUser, repair.getRepairManName(), "维修员：");
        mTvRepairResult.setText(CommonUtils.getResultName(repair.getResult()));
        mTvRepairResult.setTextColor(getResources().getColor(CommonUtils.getResultColor(repair.getResult())));
        mTvRepairDuration.setText("维修用时：" + TimeUtils.secondConvertDayHourMin(repair.getDuration()));
        mTvRepairType.setText(CommonUtils.getRepairTypeName(repair.getRepairType()));
        mTvRepairType.setBackgroundResource(CommonUtils.getStatusColor(repair.getRepairType()));
        mIvRepairResult.setImageResource(CommonUtils.getResultSmallImg(repair.getResult()));

        String faultCause = TextUtils.isEmpty(repair.getFaultCause()) ? "未知" : repair.getFaultCause();
        mTvRepairFaultCause.setText(Html.fromHtml("故障问题及原因：<br/><font color=\"#333333\">" + faultCause + "</font>"));

        Spanned scheme = null;
        if (!TextUtils.isEmpty(repair.getScheme()))
            scheme = Html.fromHtml("维修方案：<br/><font color=\"#333333\">" + repair.getScheme() + "</font>");
        CommonUtils.setTextViewValue(mTvRepairScheme, scheme);

        Spanned remark = null;
        if (!TextUtils.isEmpty(repair.getRemark()))
            remark = Html.fromHtml("备注：<br/><font color=\"#333333\">" + repair.getRemark() + "</font>");
        CommonUtils.setTextViewValue(mTvRepairRemark, remark);

        Spanned pauseExplain = null;
        if (!TextUtils.isEmpty(repair.getPauseExplain()))
            pauseExplain = Html.fromHtml("暂停原因：<br/><font color=\"#333333\">" + repair.getPauseExplain() + "</font>");
        CommonUtils.setTextViewValue(mTvRepairPauseExplain, pauseExplain);

        Spanned check = null;
        if (!repair.isCheckResult() && !TextUtils.isEmpty(repair.getCheckOpinion()))
            check = Html.fromHtml("验收不通过原因：<br/><font color=\"#333333\">" + repair.getCheckOpinion() + "</font>");
        CommonUtils.setTextViewValue(mTvRepairCheckOpinion, check);
    }

    /**
     * 到维修备品使用记录
     */
    @OnClick(R.id.tv_repair_device_part)
    public void toDevicePart() {
        mActionCreator.postLocalAction(Actions.TO_DEVICE_PART_LIST, ActionsKeys.REPAIR, mRepair);
    }

    /**
     * 到维修验收记录
     */
    @OnClick(R.id.tv_repair_check)
    public void toRepairCheck() {
        mActionCreator.postLocalAction(Actions.TO_CHECK_LIST, ActionsKeys.REPAIR, mRepair);
    }

    /**
     * 初始化view
     */
    private void initView(Repair repair) {
        if (!(mContext instanceof BaseRxFluxToolbarActionActivity)) return;
        switch (mAction) {
            case Actions.TO_REPAIR_ITEM:
                initActionBar(repair.getMachineCode() + "维修");
                if (repair.getResult() == Constants.TYPE_RESULT_NO_START) {
                    ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_REPAIR_START, R.string.action_repair_start, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
                } else if (repair.getResult() == Constants.TYPE_RESULT_PAUSE) {
                    if (repair.getRepairMan() == mLocalStorageUtils.getUser().getId()) {
                        ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_REPAIR_RESTART, R.string.action_repair_restart, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
                    } else {//非操作用户不显示操作按钮
                        ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
                    }
                } else {
                    ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
                }
                break;
            case Actions.TO_REPAIR_DETAIL:
                initActionBar(getString(R.string.title_repair_detail) + repair.getId());
                ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
                break;
            case Actions.TO_REPAIR_CHECK:
                initActionBar(repair.getMachineCode() + "验收");
                if (repair.getResult() == Constants.TYPE_RESULT_CHECK) {
                    ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_REPAIR_FAIL_SHOW_DIALOG, R.string.action_check_fail, R.drawable.ic_action_fail, R.drawable.bg_c_so_primary_normal);
                    ((BaseRxFluxToolbarActionActivity) mContext).initBottomView2(Actions.TO_REPAIR_PASS, R.string.action_check_pass, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
                } else {
                    ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
                }
                break;
        }
    }
}
