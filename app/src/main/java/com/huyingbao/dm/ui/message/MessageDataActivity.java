package com.huyingbao.dm.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.activity.BaseRxFluxToolbarActivity;
import com.huyingbao.dm.ui.message.store.MessageStore;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * 消息模块
 * Created by liujunfeng on 2017/5/27.
 */
public class MessageDataActivity extends BaseRxFluxToolbarActivity {
    @Inject
    MessageStore mMessageStore;

    public static Intent newIntent(Context context) {
        return new Intent(context, MessageDataActivity.class);
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar();
        toMessageList();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMessageStore);
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return Collections.singletonList(mMessageStore);
    }

    /**
     * 到故障列表列表页面
     */
    private void toMessageList() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, MessageDataListFragment.newInstance())
                .commit();
    }
}
