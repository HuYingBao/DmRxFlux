package com.huyingbao.dm.ui.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.dialogfragment.BaseRxFluxDialogFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.common.adapter.DevicePartAddAdapter;
import com.huyingbao.dm.ui.common.store.DevicePartAddStore;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 备品备件添加
 * Created by liujunfeng on 2017/3/30.
 */
public class DevicePartAddFragment extends BaseRxFluxDialogFragment {
    @Inject
    DevicePartAddStore mDevicePartAddStore;

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_ok)
    TextView mTvOk;

    private DevicePartAddAdapter mAdapter;
    private List<DevicePart> mDataList = new ArrayList<>();
    private ArrayList<DevicePart> mOldList;

    /**
     * @param machineTypeId
     * @param devicePartList
     * @return
     */
    public static DevicePartAddFragment newInstance(int machineTypeId, ArrayList<DevicePart> devicePartList) {
        Bundle bundle = new Bundle();
        bundle.putInt(ActionsKeys.MACHINE_TYPE_ID, machineTypeId);
        bundle.putParcelableArrayList(ActionsKeys.DEVICE_PART_LSIT, devicePartList);
        DevicePartAddFragment fragment = new DevicePartAddFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dialog_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mActionCreator.getDevicePartListByDeviceType(getArguments().getInt(ActionsKeys.MACHINE_TYPE_ID));
        mOldList = getArguments().getParcelableArrayList(ActionsKeys.DEVICE_PART_LSIT);
        initAdapter();
        initRecyclerView();
    }

    protected void initAdapter() {
        mAdapter = new DevicePartAddAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    protected void initRecyclerView() {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRvContent.setLayoutManager(mLinearLayoutManager);
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.GET_DEVICE_PART_LIST_BY_DEVICE_TYPE:
                mDataList.clear();
                if (CommonUtils.isListAble(mDevicePartAddStore.getDevicePartList())) {
                    if (CommonUtils.isListAble(mOldList)) {
                        Observable.fromIterable(mDevicePartAddStore.getDevicePartList())
                                .flatMap(devicePart -> {
                                    for (DevicePart oldDevicePart : mOldList) {
                                        if (devicePart.getId() == oldDevicePart.getId()) {
                                            devicePart.setQuantity(oldDevicePart.getQuantity());
                                            break;
                                        }
                                    }
                                    return Observable.just(devicePart);
                                })
                                // Concat操作符将多个Observable结合成一个Observable并发射数据，
                                // 并且严格按照先后顺序发射数据，
                                // 前一个Observable的数据没有发射完，
                                // 是不能发射后面Observable的数据的。
                                .collect(
                                        () -> {
                                            return new ArrayList<DevicePart>();
                                        },
                                        (localFiles, fullPath) -> {
                                            localFiles.add(fullPath);
                                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(resultList -> {
                                    mDataList.addAll(resultList);
                                    mAdapter.notifyDataSetChanged();
                                });
                    } else {
                        mDataList.addAll(mDevicePartAddStore.getDevicePartList());
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDevicePartAddStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mDevicePartAddStore);
    }

    @OnClick(R.id.tv_cancel)
    public void toCancel() {
        dismiss();
    }

    @OnClick(R.id.tv_ok)
    public void toOk() {
        dismiss();
        Iterator<DevicePart> iterator = mDataList.iterator();
        while (iterator.hasNext()) {
            if (TextUtils.isEmpty(iterator.next().getQuantity()))
                iterator.remove();
        }
        mActionCreator.postLocalAction(Actions.TO_DEVICE_PART_ADD_RESULT, ActionsKeys.SPAREPARTS, mDataList);
    }
}
