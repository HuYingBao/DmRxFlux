package com.huyingbao.dm.ui.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxFragment;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.common.ConfirmFragment;
import com.huyingbao.dm.ui.devicelist.model.DeviceImg;
import com.huyingbao.dm.ui.image.adapter.ImageDevicePagerAdapter;
import com.huyingbao.dm.ui.image.adapter.ImagePagerAdapter;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 图片展示 Created by liujunfeng on 2017/5/8.
 */
public class ShowImgFragment extends BaseRxFluxFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.vp_img)
    ViewPager mVpImg;

    private ArrayList<String> mImgPathList;
    private ArrayList<DeviceImg> mDeviceImgList;

    private String mTitle;
    private int mPosition = 0;// 图片初始位置0

    private PagerAdapter mAdapter;

    /**
     * @param deviceImgArrayList 装着的图片list
     * @return
     */
    public static ShowImgFragment newInstance(ArrayList<DeviceImg> deviceImgArrayList, int position, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt(ActionsKeys.POSITION, position);
        bundle.putString(ActionsKeys.TITLE, title);
        bundle.putParcelableArrayList(ActionsKeys.IMG_BEAN_LIST, deviceImgArrayList);
        ShowImgFragment fragment = new ShowImgFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * @param position
     * @param title
     * @param imgPathList 图片路径list,可以是本地图片、网络图片E
     * @return
     */
    public static ShowImgFragment newInstance(int position, String title, ArrayList<String> imgPathList) {
        Bundle bundle = new Bundle();
        bundle.putInt(ActionsKeys.POSITION, position);
        bundle.putStringArrayList(ActionsKeys.IMG_PATH_LIST, imgPathList);
        bundle.putString(ActionsKeys.TITLE, title);
        ShowImgFragment fragment = new ShowImgFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_show_img;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        // 获取数据
        mDeviceImgList = getArguments().getParcelableArrayList(ActionsKeys.IMG_BEAN_LIST);
        mPosition = getArguments().getInt(ActionsKeys.POSITION);
        mImgPathList = getArguments().getStringArrayList(ActionsKeys.IMG_PATH_LIST);
        String title = getArguments().getString(ActionsKeys.TITLE);
        // 初始化标题
        // 初始化adapter
        if (CommonUtils.isListAble(mDeviceImgList)) {
            mTitle = (TextUtils.isEmpty(title) ? "" : title + " ") + "%d" + "/" + mDeviceImgList.size();
            mAdapter = new ImageDevicePagerAdapter(mContext, mDeviceImgList);
        } else {
            mTitle = (TextUtils.isEmpty(title) ? "" : title + " ") + "%d" + "/" + mImgPathList.size();
            mAdapter = new ImagePagerAdapter(mContext, mImgPathList);
        }
        initActionBar(String.format(mTitle, mPosition + 1), true);
        // 设置viewpager
        mVpImg.setAdapter(mAdapter);
        mVpImg.setCurrentItem(mPosition);
        mVpImg.addOnPageChangeListener(this);
        // 设置返回值
        setResult();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuItem mainMenu = menu.addSubMenu("重拍").getItem();
//        mainMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//
//        MenuItem deleteMenu = menu.addSubMenu("删除").setIcon(R.drawable.icon_delete).getItem();
//        deleteMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        deleteMenu.setOnMenuItemClickListener(item -> showDeleteConfirmDialog());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case "delete":
                delete();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        initActionBar(String.format(mTitle, mPosition + 1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * 显示确认是否删除对话框
     *
     * @return
     */
    private boolean showDeleteConfirmDialog() {
        ConfirmFragment confirmFragment = ConfirmFragment.newInstance("是否删除第" + (mPosition + 1) + "张图片？", "delete");
        confirmFragment.show(getChildFragmentManager(), ConfirmFragment.class.getSimpleName());
        return false;
    }

    /**
     * 删除图片
     */
    private void delete() {
        // 移除数据
        mImgPathList.remove(mPosition);
        // 设置返回页面数据
        setResult();
        // 移除view
        mAdapter.notifyDataSetChanged();
        // 数据为空,结束界面
        if (mImgPathList.size() == 0)
            getActivity().finish();
            // 修改标题
        else
            getActivity().setTitle(mTitle + "  " + (mPosition + 1) + "/" + mImgPathList.size());
    }

    /**
     * 设置返回页面数据
     */
    private void setResult() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ActionsKeys.IMG_PATH_LIST, mImgPathList);
        getActivity().setResult(Activity.RESULT_OK, intent);
    }
}
