package com.huyingbao.dm.ui.message;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxListFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.dm.ui.message.adapter.MessageDataAdapter;
import com.huyingbao.dm.ui.message.model.Message;
import com.huyingbao.dm.ui.message.model.MessageData;
import com.huyingbao.dm.ui.message.store.MessageStore;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.ViewUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2017/8/14.
 */
public class MessageDataListFragment extends BaseRxFluxListFragment<MessageData> {
    @Inject
    MessageStore mMessageStore;

    public static MessageDataListFragment newInstance() {
        return new MessageDataListFragment();
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.MESSAGE_GET_NEW_MESSAGE:
                refresh();
                break;
            case Actions.GET_ALL_MSG:
                showDataList(mMessageStore.getMessageDataList());
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mMessageStore);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new MessageDataAdapter(mDataList);
        mAdapter.setEmptyView(R.layout.view_empty, (ViewGroup) mRvContent.getParent());
    }

    @Override
    protected void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRvContent.setLayoutManager(mLinearLayoutManager);
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvContent.addItemDecoration(ViewUtils.getItemDecoration(mContext));
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Message message = null;
                try {
                    message = JSONObject.parseObject(mDataList.get(position).getContent(), Message.class);
                } catch (Exception e) {
                }
                CommonUtils.handleMessage(mContext, message);
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getAllMsg(index, 20);
//        List<Message> notices = SQLite.select().from(Message.class)
//                .where(Message_Table.userId.eq(mLocalStorageUtils.getUser().getId()))
//                .orderBy(Message_Table.sendTime, false)
//                .queryList();
//        mSrlContent.setRefreshing(false);
//        if (CommonUtils.isListAble(notices))
//            mDataList.addAll(notices);
//        mAdapter.notifyDataSetChanged();
    }
}
