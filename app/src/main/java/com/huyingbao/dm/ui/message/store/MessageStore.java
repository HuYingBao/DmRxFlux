package com.huyingbao.dm.ui.message.store;

import com.huyingbao.dm.ui.message.model.MessageData;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.List;

/**
 * 消息模块
 * Created by liujunfeng on 2017/1/1.
 */
public class MessageStore extends BaseRxStore {
    private List<MessageData> mMessageDataList;

    public MessageStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_ALL_MSG://获取所有的消息
                mMessageDataList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            default://必须有,接收到非自己处理的action返回
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));//只发送自己处理的action
    }

    public List<MessageData> getMessageDataList() {
        return mMessageDataList;
    }
}
