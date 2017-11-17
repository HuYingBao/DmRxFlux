package com.huyingbao.dm.ui.device;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.common.ConfirmFragment;
import com.huyingbao.dm.ui.device.adapter.ViewPageAdapter;
import com.huyingbao.dm.ui.device.store.DeviceDetailStore;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.devicelist.model.DeviceImg;
import com.huyingbao.dm.ui.image.ShowImgActivity;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.MockUtils;
import com.huyingbao.rxflux2.util.imageloader.ImageLoaderUtils;
import com.huyingbao.rxflux2.widget.circleindicator.CirclePageIndicator;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备基本信息
 * Created by liujunfeng on 2017/5/27.
 */
public class DeviceDetailFragment extends BaseRxFluxFragment {
    @Inject
    DeviceDetailStore mDeviceDetailStore;
    @BindView(R.id.vp_device_imgs)
    ViewPager mVpDeviceImgs;
    @BindView(R.id.cpid_device_imgs)
    CirclePageIndicator mCpidDeviceImgs;
    @BindView(R.id.tv_device_status)
    TextView mTvDeviceStatus;
    @BindView(R.id.tv_device_code)
    TextView mTvDeviceCode;
    @BindView(R.id.tv_device_name)
    TextView mTvDeviceName;
    @BindView(R.id.rb_device_degree)
    RatingBar mRbDeviceDegree;
    @BindView(R.id.tv_device_type)
    TextView mTvDeviceType;
    @BindView(R.id.tv_device_power)
    TextView mTvDevicePower;
    @BindView(R.id.tv_device_complex_jf)
    TextView mTvDeviceComplexJf;
    @BindView(R.id.tv_device_dprc_period)
    TextView mTvDeviceDprcPeriod;
    @BindView(R.id.tv_device_service_date)
    TextView mTvDeviceServiceDate;
    @BindView(R.id.tv_device_price)
    TextView mTvDevicePrice;
    @BindView(R.id.tv_device_complex_df)
    TextView mTvDeviceComplexDf;
    @BindView(R.id.tv_device_duration)
    TextView mTvDeviceDuration;
    @BindView(R.id.tv_device_org)
    TextView mTvDeviceOrg;
    @BindView(R.id.tv_device_manufacturer)
    TextView mTvDeviceManufacturer;
    @BindView(R.id.tv_device_remark)
    TextView mTvDeviceRemark;
    @BindView(R.id.tv_device_start)
    TextView mTvDeviceStart;
    @BindView(R.id.tv_device_stop)
    TextView mTvDeviceStop;
    @BindView(R.id.tv_device_disable)
    TextView mTvDeviceDisable;
    @BindView(R.id.tv_device_warning)
    TextView mTvDeviceWarning;
    @BindView(R.id.tv_device_scrap)
    TextView mTvDeviceScrap;
    @BindView(R.id.tv_device_repair)
    TextView mTvDeviceRepair;
    private Device mDevice;

    private List<View> mViewList = new ArrayList<>();
    private ViewPageAdapter mViewPageAdapter;

    public static DeviceDetailFragment newInstance(Device device) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.DEVICE, device);
        DeviceDetailFragment fragment = new DeviceDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_device_detail;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mDevice = getArguments().getParcelable(ActionsKeys.DEVICE);
        initViewPager();
        //显示传递的设备详情
        showDeviceDetail(mDevice);
        //获取设备详情
        mActionCreator.getDeviceDetail(mContext, mDevice.getId());
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.APPLY_REPAIR:
                showShortToast("报修成功！");
                mDevice.setStatusMain(Constants.STATUS_DEVICE_REPAIR);
                showDeviceDetail(mDevice);
                break;
            case Actions.GET_DEVICE_DETAIL:
                mDevice = mDeviceDetailStore.getDevice();
                showDeviceDetail(mDevice);
                break;
            case Actions.CLICKED_APPLY_REPAIR:
                mActionCreator.applyRepair(mContext, mDevice.getId());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mDeviceDetailStore);
    }

    /**
     * 初始化页面显示数据
     */
    private void initViewPager() {
        mViewPageAdapter = new ViewPageAdapter(mViewList);
        mVpDeviceImgs.setAdapter(mViewPageAdapter);
        mCpidDeviceImgs.setViewPager(mVpDeviceImgs);
    }

    /**
     * 显示设备详情
     *
     * @param device
     */
    private void showDeviceDetail(@NonNull Device device) {
        if (device == null) return;
        mTvDeviceCode.setText(device.getCode());
        mTvDeviceName.setText(device.getName());
        mRbDeviceDegree.setRating(device.getDegree());
        mTvDeviceStatus.setText(CommonUtils.getStatusName(device.getStatusMain()));
        mTvDeviceStatus.setBackgroundResource(CommonUtils.getStatusColor(device.getStatusMain()));

        mTvDeviceType.setText(Html.fromHtml("型号：<font  color=\"#333333\">" + device.getType() + "</font>"));
        mTvDevicePower.setText(Html.fromHtml("功率：<font  color=\"#333333\">" + device.getPower() + "</font>"));
        mTvDeviceComplexJf.setText(Html.fromHtml("复杂系数：JF：<font  color=\"#333333\">" + device.getComplexJf() + "</font>"));
        mTvDeviceDprcPeriod.setText(Html.fromHtml("折旧年限：<font  color=\"#333333\">" + device.getDprcPeriod() + "</font>"));

        mTvDeviceServiceDate.setText(Html.fromHtml("投产日期：<font color=\"#333333\">" + device.getServiceDate() + "</font>"));
        mTvDevicePrice.setText(Html.fromHtml("原值：<font  color=\"#333333\">" + device.getPrice() + "</font>"));
        mTvDeviceComplexDf.setText(Html.fromHtml("DF：<font  color=\"#333333\">" + device.getComplexDf() + "</font>"));

        if (!TextUtils.isEmpty(device.getOrg()))
            mTvDeviceOrg.setText(Html.fromHtml("使用部门：<font  color=\"#333333\">" + device.getOrg() + "</font>"));
        if (!TextUtils.isEmpty(device.getManufacturer()))
            mTvDeviceManufacturer.setText(Html.fromHtml("制造厂家：<font  color=\"#333333\">" + device.getManufacturer() + "</font>"));
        if (!TextUtils.isEmpty(device.getRemark()))
            mTvDeviceRemark.setText(Html.fromHtml("备注：<font  color=\"#333333\">" + device.getRemark() + "</font>"));

        showDeviceImg(MockUtils.mockDeviceImgList());
        //当前设备不是故障状态，当前用户是设备管理员，显示报修按钮
        if (device.getStatusMain() != Constants.STATUS_DEVICE_REPAIR && mLocalStorageUtils.getUser().getRole() == Constants.ROLE_USER_MACHINE_ADMIN) {
            mTvDeviceRepair.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示设备图片数据
     */
    private void showDeviceImg(List<DeviceImg> deviceImgList) {
        mViewList.clear();
        if (!CommonUtils.isListAble(deviceImgList)) return;
        for (DeviceImg deviceImg : deviceImgList) {
            LinearLayout layout = new LinearLayout(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            final ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoaderUtils.loadImage(mContext, deviceImg.getFilePath(), imageView);
            layout.addView(imageView, layoutParams);
            layout.setOnClickListener(v -> startActivity(ShowImgActivity.newIntent(mContext, MockUtils.mockDeviceImgList(), deviceImgList.indexOf(deviceImg), "设备照片")));
            mViewList.add(layout);
        }
        if (deviceImgList.size() > 10) mCpidDeviceImgs.setVisibility(View.INVISIBLE);
        mViewPageAdapter.notifyDataSetChanged();
    }

    /**
     * 技术合同
     */
    @OnClick(R.id.tv_device_contract)
    public void toDeviceContract(TextView view) {
        startActivity(ShowImgActivity.newIntent(mContext, MockUtils.mockDeviceImgList(), 0, view.getText().toString()));
    }

    /**
     * 技术文档
     */
    @OnClick(R.id.tv_device_document)
    public void toDeviceDocument(TextView view) {
        startActivity(ShowImgActivity.newIntent(mContext, MockUtils.mockDeviceImgList(), 0, view.getText().toString()));
    }

    /**
     * 安装调试记录
     */
    @OnClick(R.id.tv_device_record)
    public void toDeviceRecord(TextView view) {
        startActivity(ShowImgActivity.newIntent(mContext, MockUtils.mockDeviceImgList(), 0, view.getText().toString()));
    }

    @OnClick(R.id.tv_device_start)
    public void applyStart() {
    }

    @OnClick(R.id.tv_device_stop)
    public void applyStop() {
    }

    @OnClick(R.id.tv_device_disable)
    public void applyDisable() {
    }

    @OnClick(R.id.tv_device_warning)
    public void applyWarning() {
    }

    @OnClick(R.id.tv_device_scrap)
    public void applyScrap() {
    }

    @OnClick(R.id.tv_device_repair)
    public void applyRepair() {
        ConfirmFragment confirmFragment = ConfirmFragment.newInstance(Actions.CLICKED_APPLY_REPAIR, "是否报修？");
        confirmFragment.show(getChildFragmentManager(), ConfirmFragment.class.getSimpleName());
    }
}
