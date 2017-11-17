package com.huyingbao.dm.ui.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActivity;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.devicelist.model.DeviceImg;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.ArrayList;
import java.util.List;

/**
 * 拍照及图片展示 Created by liujunfeng on 2017/5/5.
 */
public class ShowImgActivity extends BaseRxFluxToolbarActivity {
    /**
     * @param context
     * @param deviceImgArrayList 封装的图片
     * @return
     */
    public static Intent newIntent(Context context, ArrayList<DeviceImg> deviceImgArrayList, int position, String title) {
        Intent intent = new Intent(context, ShowImgActivity.class);
        intent.putExtra(ActionsKeys.POSITION, position);
        intent.putExtra(ActionsKeys.TITLE, title);
        intent.putParcelableArrayListExtra(ActionsKeys.IMG_BEAN_LIST, deviceImgArrayList);
        return intent;
    }

    /**
     * @param context
     * @param position    当前图片位置
     * @param imgPathList 图片路径list
     * @param title       图片所属名称
     * @return
     */
    public static Intent newIntent(Context context, int position, String title, ArrayList<String> imgPathList) {
        Intent intent = new Intent(context, ShowImgActivity.class);
        intent.putExtra(ActionsKeys.POSITION, position);
        intent.putExtra(ActionsKeys.TITLE, title);
        intent.putStringArrayListExtra(ActionsKeys.IMG_PATH_LIST, imgPathList);
        return intent;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_base;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        ShowImgFragment showImgFragment;
        ArrayList<DeviceImg> deviceImgArrayList = getIntent().getParcelableArrayListExtra(ActionsKeys.IMG_BEAN_LIST);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(ActionsKeys.IMG_PATH_LIST);
        int position = getIntent().getIntExtra(ActionsKeys.POSITION, 0);
        String title = getIntent().getStringExtra(ActionsKeys.TITLE);
        if (CommonUtils.isListAble(deviceImgArrayList)) {
            showImgFragment = ShowImgFragment.newInstance(deviceImgArrayList, position, title);
        } else {
            showImgFragment = ShowImgFragment.newInstance(position, title, stringArrayListExtra);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, showImgFragment).commit();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }
}
