package com.huyingbao.dm.ui.check.store;

import com.huyingbao.dm.ui.check.model.Check;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.List;

/**
 * 验收记录模块
 * Created by liujunfeng on 2017/3/29.
 */
public class CheckStore extends BaseRxStore {
    private List<Check> mCheckList;

    public CheckStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_CHECK_LIST_BY_MAINTAIN:
            case Actions.GET_CHECK_LIST_BY_REPAIR:
                mCheckList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public List<Check> getCheckList() {
        return mCheckList;
    }
}
