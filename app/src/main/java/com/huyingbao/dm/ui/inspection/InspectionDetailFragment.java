package com.huyingbao.dm.ui.inspection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActionActivity;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.inspection.adapter.InspectionItemAdapter;
import com.huyingbao.dm.ui.inspection.model.InspectItem;
import com.huyingbao.dm.ui.inspection.model.Inspection;
import com.huyingbao.dm.ui.inspection.store.InspectionDetailStore;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 巡检详情
 * Created by liujunfeng on 2017/5/27.
 */
public class InspectionDetailFragment extends BaseRxFluxFragment {
    @Inject
    InspectionDetailStore mInspectionDetailStore;

    @BindView(R.id.tv_inspection_device_code)
    TextView mTvInspectionDeviceCode;
    @BindView(R.id.tv_inspection_device_name)
    TextView mTvInspectionDeviceName;
    @BindView(R.id.tv_inspection_user)
    TextView mTvInspectionUser;
    @BindView(R.id.tv_inspection_time)
    TextView mTvInspectionTime;
    @BindView(R.id.iv_inspection_result)
    ImageView mIvInspectionResult;
    @BindView(R.id.tv_inspection_item)
    TextView mTvInspectionItem;
    @BindView(R.id.rv_inspection_item)
    RecyclerView mRvInspectionItem;

    private Inspection mInspection;
    private InspectionItemAdapter mInspectionItemAdapter;
    private List<InspectItem> mInspectItemList = new ArrayList<>();
    private String mAction;

    public static InspectionDetailFragment newInstance(Inspection inspection, String action) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.INSPECTION, inspection);
        bundle.putString(ActionsKeys.BASE_ACTION, action);
        InspectionDetailFragment fragment = new InspectionDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_inspection_detail;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mAction = getArguments().getString(ActionsKeys.BASE_ACTION);
        mInspection = getArguments().getParcelable(ActionsKeys.INSPECTION);
        initActionBar(mInspection.getMachineCode() + "巡检");
        showInspectionInfo();
        initAdapter();
        initRecyclerView();
        getDataList(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!(mContext instanceof BaseRxFluxToolbarActionActivity)) return;
        if (TextUtils.equals(mAction, Actions.TO_INSPECTION_ITEM)) {
            initBottom(mInspection);
        } else {
            ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
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
        if (hidden) return;
        if (!(mContext instanceof BaseRxFluxToolbarActionActivity)) return;
        if (TextUtils.equals(mAction, Actions.TO_INSPECTION_ITEM)) {
            initBottom(mInspection);
        } else {
            ((BaseRxFluxToolbarActionActivity) mContext).invisibilityBottomView();
        }
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_INSPECT_ITEM_LIST_BY_INSPECT:
                mInspectItemList.clear();
                if (CommonUtils.isListAble(mInspectionDetailStore.getInspectItemList()))
                    mInspectItemList.addAll(mInspectionDetailStore.getInspectItemList());
                mInspectionItemAdapter.notifyDataSetChanged();
                break;
            case Actions.TO_INSPECTION_START://调用开始巡检
                mActionCreator.inspectionStart(mContext, mInspection.getId());
                break;
            case Actions.TO_INSPECTION_FINISH://调用结束巡检
                mActionCreator.inspectionFinish(mContext, mInspection.getId(), mInspectItemList);
                break;
            case Actions.INSPECTION_START://点击开始巡检
                //更新数据
                mInspection = mInspectionDetailStore.getInspection();
                if (CommonUtils.isListAble(mInspection.getItems())) {
                    mInspectItemList.clear();
                    mInspectItemList.addAll(mInspection.getItems());
                }
                //更新底部view
                ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_INSPECTION_FINISH, R.string.action_inspection_finish, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
                //更新数据显示
                mInspectionItemAdapter.setChangeAble(true);
                mInspectionItemAdapter.setStart(true);
                mInspectionItemAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mInspectionDetailStore);
    }

    /**
     * 显示巡检基本信息
     */
    private void showInspectionInfo() {
        CommonUtils.setTextViewValue(mTvInspectionDeviceCode, mInspection.getMachineCode(), "设备编号：");
        CommonUtils.setTextViewValue(mTvInspectionDeviceName, mInspection.getMachineName(), "设备名称：");
        CommonUtils.setTextViewValue(mTvInspectionTime, TimeUtils.formatTime(mInspection.getPlanDate()), "巡检日期：");
        CommonUtils.setTextViewValue(mTvInspectionUser, mInspection.getInspectorName(), "巡检员：");
        mIvInspectionResult.setImageResource(CommonUtils.getInspectionResultImg(mInspection.getResult()));
    }

    protected void initAdapter() {
        mInspectionItemAdapter = new InspectionItemAdapter(mInspectItemList);
        mInspectionItemAdapter.setStart(mInspection.getResult() != Constants.TYPE_RESULT_INSPECTION_NO_START);
        mInspectionItemAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvInspectionItem.getParent());
    }

    protected void initRecyclerView() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRvInspectionItem.setLayoutManager(mLinearLayoutManager);
        mRvInspectionItem.setHasFixedSize(true);
        mRvInspectionItem.setAdapter(mInspectionItemAdapter);
        mRvInspectionItem.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvInspectionItem.addItemDecoration(ViewUtils.getItemDecorationNoMargin(mContext));
    }

    protected void getDataList(int index) {
        mActionCreator.getInspectItemListByInspect(mInspection.getId());
    }

    /**
     * 设置底部view
     */
    private void initBottom(Inspection inspection) {
        if (inspection.getResult() == Constants.TYPE_RESULT_INSPECTION_NO_START)
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_INSPECTION_START, R.string.action_inspection_start, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
        else
            ((BaseRxFluxToolbarActionActivity) mContext).initBottomView1(Actions.TO_INSPECTION_FINISH, R.string.action_inspection_finish, R.drawable.ic_action_done, R.drawable.bg_c_so_primary_normal);
    }
}
