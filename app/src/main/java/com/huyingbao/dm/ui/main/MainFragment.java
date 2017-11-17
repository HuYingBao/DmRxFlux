package com.huyingbao.dm.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.checkdevice.CheckDeviceActivity;
import com.huyingbao.dm.ui.devicelist.DeviceListActivity;
import com.huyingbao.dm.ui.inspection.InspectionDetailActivity;
import com.huyingbao.dm.ui.main.adapter.MessageMainAdapter;
import com.huyingbao.dm.ui.main.model.Status;
import com.huyingbao.dm.ui.main.store.MainStore;
import com.huyingbao.dm.ui.maintenance.MaintenanceDetailActivity;
import com.huyingbao.dm.ui.message.MessageDataActivity;
import com.huyingbao.dm.ui.message.model.Message;
import com.huyingbao.dm.ui.message.model.MessageData;
import com.huyingbao.dm.ui.repair.RepairDetailActivity;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页-首页
 * Created by liujunfeng on 2017/3/30.
 */
public class MainFragment extends BaseRxFluxFragment {
    @Inject
    MainStore mMainStore;

    @BindView(R.id.rv_main_notice)
    RecyclerView mRvMainNotice;
    @BindView(R.id.tv_tab_check)
    TextView mTvTabCheck;
    @BindView(R.id.tv_tab_inspection)
    TextView mTvTabInspection;
    @BindView(R.id.tv_tab_main)
    TextView mTvTabMain;
    @BindView(R.id.tv_tab_repair)
    TextView mTvTabRepair;
    @BindView(R.id.tv_tab_maintenance)
    TextView mTvTabMaintenance;
    @BindView(R.id.tv_status_normal_count)
    TextView mTvStatusNormalCount;
    @BindView(R.id.tv_status_repair_count)
    TextView mTvStatusRepairCount;
    @BindView(R.id.tv_status_check_count)
    TextView mTvStatusCheckCount;
    @BindView(R.id.tv_status_maintenance_count)
    TextView mTvStatusMaintenanceCount;
    @BindView(R.id.tv_status_maintenance_keeping_count)
    TextView mTvStatusMaintenanceKeepingCount;
    @BindView(R.id.tv_status_inspection_count)
    TextView mTvStatusInspectionCount;
    @BindView(R.id.tv_status_repair_keeping_count)
    TextView mTvStatusRepairKeepingCount;
    @BindView(R.id.tv_status_error_count)
    TextView mTvStatusErrorCount;
    @BindView(R.id.tv_status_repair_waiting_count)
    TextView mTvStatusRepairWaitingCount;

    private MessageMainAdapter mNoticeMainAdapter;
    private List<Message> mMessageList = new ArrayList<>();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        getDeviceTypeList();
        switch (mLocalStorageUtils.getUser().getRole()) {
            case Constants.ROLE_USER_MACHINE_REPAIR:
                mTvTabCheck.setVisibility(View.GONE);
                mTvTabInspection.setVisibility(View.GONE);
                break;
            case Constants.ROLE_USER_MACHINE_INSPECTION:
                mTvTabCheck.setVisibility(View.GONE);
                mTvTabRepair.setVisibility(View.GONE);
                mTvTabMaintenance.setVisibility(View.GONE);
                break;
        }
        initAdapter();
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getStatusList();
        getMainMessageList();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_DEVICE_STATUS_LIST:
                showDeviceStatus(mMainStore.getStatusList());
                break;
            case Actions.MESSAGE_GET_NEW_MESSAGE:
                getMainMessageList();
                break;
            case Actions.GET_ALL_MSG:
                if (CommonUtils.isListAble(mMainStore.getMessageDataList())) {
                    mMessageList.clear();
                    try {
                        for (MessageData messageData : mMainStore.getMessageDataList()) {
                            Message message = JSONObject.parseObject(messageData.getContent(), Message.class);
                            message.setSendTime(TextUtils.isEmpty(messageData.getSendTime()) ? messageData.getCreateTime() : messageData.getSendTime());
                            mMessageList.add(message);
                        }
                    } catch (Exception e) {
                    }
                    if (mNoticeMainAdapter != null)
                        mNoticeMainAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMainStore);
    }

    @OnClick(R.id.tv_tab_check)
    public void toCheck() {
        startActivity(CheckDeviceActivity.newIntent(mContext));
    }

    @OnClick(R.id.tv_tab_inspection)
    public void toInspection() {
        startActivity(InspectionDetailActivity.newIntent(mContext));
    }

    @OnClick(R.id.tv_tab_repair)
    public void toRepair() {
        startActivity(RepairDetailActivity.newIntent(mContext));
    }

    @OnClick(R.id.tv_tab_maintenance)
    public void toMaintenance() {
        startActivity(MaintenanceDetailActivity.newIntent(mContext));
    }

    /**
     * 到设备列表界面
     *
     * @param view
     */
    @OnClick({R.id.rl_status_normal, R.id.rl_status_reapir, R.id.rl_status_check,
            R.id.rl_status_maintenance, R.id.rl_status_maintenance_keeping, R.id.rl_status_inspection,
            R.id.rl_status_repair_keeping, R.id.rl_status_error, R.id.rl_status_repair_waiting})
    public void toDeviceList(View view) {
        startActivity(DeviceListActivity.newIntent(mContext, CommonUtils.getStatusByView(view.getId())));
    }

    /**
     * 获取全部设备类型
     */
    private void getDeviceTypeList() {
        mActionCreator.getDeviceTypeList();
    }

    /**
     * 获取设备状态列表
     */
    private void getStatusList() {
        mActionCreator.getDeviceStatusList();
    }

    /**
     * 获取消息列表
     */
    private void getMainMessageList() {
        mActionCreator.getAllMsg(0, 10);
    }

    /**
     * 显示设备状态列表
     *
     * @param statusList
     */
    private void showDeviceStatus(List<Status> statusList) {
        if (!CommonUtils.isListAble(statusList)) return;
        for (Status status : statusList) {
            int count = status.getCount();
            TextView textView = getViewByStatus(status.getStatus());
            if (textView != null) {
                textView.setVisibility(count == 0 ? View.INVISIBLE : View.VISIBLE);
                textView.setText(count > 999 ? "•••" : count + "");
            }
        }
    }


    /**
     * 状态 View 映射
     *
     * @return
     */
    private TextView getViewByStatus(int deviceStatus) {
        switch (deviceStatus) {
            case Constants.STATUS_DEVICE_NORMAL://正常
                return mTvStatusNormalCount;
            case Constants.STATUS_DEVICE_REPAIR://故障
                return mTvStatusRepairCount;
            case Constants.STATUS_DEVICE_CHECK://待验收
                return mTvStatusCheckCount;

            case Constants.STATUS_DEVICE_MAINTENANCE_WAITING://待保养
                return mTvStatusMaintenanceCount;
            case Constants.STATUS_DEVICE_MAINTENANCE_KEEPING://保养中
                return mTvStatusMaintenanceKeepingCount;
            case Constants.STATUS_DEVICE_INSPECTION://待验收
                return mTvStatusInspectionCount;

            case Constants.STATUS_DEVICE_REPAIR_KEEPING://维修中
                return mTvStatusRepairKeepingCount;
            case Constants.STATUS_DEVICE_ERROR://停用报废
                return mTvStatusErrorCount;
            case Constants.STATUS_DEVICE_REPAIR_WAITING://待维修
                return mTvStatusRepairWaitingCount;
            default:
                return null;
        }
    }

//    private void refreshNoticeData() {
//        mMessageList.clear();
//        List<Message> notices = SQLite.select().from(Message.class)
//                .where(Message_Table.userId.eq(mLocalStorageUtils.getUser().getId()))
//                .limit(2)
//                .orderBy(Message_Table.sendTime, false)
//                .queryList();
//        if (CommonUtils.isListAble(notices))
//            mMessageList.addAll(notices);
//        if (mNoticeMainAdapter != null)
//            mNoticeMainAdapter.notifyDataSetChanged();
//    }

    private void initAdapter() {
        mNoticeMainAdapter = new MessageMainAdapter(mMessageList);
        mNoticeMainAdapter.setEmptyView(R.layout.view_empty_small, (ViewGroup) mRvMainNotice.getParent());
    }

    private void initRecyclerView() {
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        HorizontalDividerItemDecoration.Builder builder = new HorizontalDividerItemDecoration.Builder(mContext)
                .color(mContext.getResources().getColor(R.color.primary))
                .margin(dimensionPixelOffset, dimensionPixelOffset)
                .sizeResId(R.dimen.dp_04);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);

        mRvMainNotice.setLayoutManager(mLinearLayoutManager);
        mRvMainNotice.setHasFixedSize(true);
        mRvMainNotice.setAdapter(mNoticeMainAdapter);
        mRvMainNotice.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速

        mRvMainNotice.addItemDecoration(builder.build());
        mRvMainNotice.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                CommonUtils.handleMessage(mContext, mMessageList.get(position));
                startActivity(MessageDataActivity.newIntent(mContext));
            }
        });
    }
}
