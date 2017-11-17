package com.huyingbao.dm.ui.main.store;

import com.alibaba.sdk.android.man.MANServiceProvider;
import com.huyingbao.dm.ui.main.model.Status;
import com.huyingbao.dm.ui.message.model.MessageData;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;
import com.huyingbao.rxflux2.util.AppUtils;
import com.huyingbao.rxflux2.util.LocalStorageUtils;
import com.huyingbao.rxflux2.util.ServiceUtils;

import java.util.List;

import javax.inject.Inject;


/**
 * 首页
 * Created by liujunfeng on 2017/1/1.
 */
public class MainStore extends BaseRxStore {
    @Inject
    LocalStorageUtils mLocalStorageUtils;
    private List<Status> mStatusList;
    private List<MessageData> mMessageDataList;

    public MainStore(Dispatcher dispatcher) {
        super(dispatcher);
        AppUtils.getApplicationComponent().inject(this);
    }

    /**
     * This callback will get all the actions, each store must react on the types he want and do
     * some logic with the model, for example add it to the list to cache it, modify
     * fields etc.. all the logic for the models should go here and then call postChange so the
     * view request the new data
     * 这个回调接收所有的actions(RxAction),每个store都必须根据action的type做出反应,,例如将其添加到列表缓存,修改字段等。
     * 所有的逻辑模型应该在这里,然后调用postChange请求新数据视图
     */
    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.LOGOUT:
                handleLogout();
                break;
            case Actions.GET_DEVICE_STATUS_LIST:
                mStatusList = getRightResponse(action.get(ActionsKeys.RESPONSE));
                break;
            case Actions.GET_DEVICE_TYPE_LIST:
                mLocalStorageUtils.setList(ActionsKeys.ARRAY_TYPE, getRightResponse(action.get(ActionsKeys.RESPONSE)));
                break;
            case Actions.GET_ALL_MSG://获取所有的消息
                mMessageDataList = getRightPageListResponse(action.get(ActionsKeys.RESPONSE));
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    /**
     * 退出
     */
    public void handleLogout() {
        //清除登录状态
        mLocalStorageUtils.setBoolean(ActionsKeys.IS_LOGIN, false);
        //清除当前登录用户
        mLocalStorageUtils.setUser(null);
        //停止百度推送
        //BaiduPushBase.stop(AppUtils.getApplication());
        //停止定时器
        ServiceUtils.stopTimerCheck(AppUtils.getApplication());
        //注销埋点
        MANServiceProvider.getService().getMANAnalytics().updateUserAccount("", "");
    }

    public List<Status> getStatusList() {
        return mStatusList;
    }

    public List<MessageData> getMessageDataList() {
        return mMessageDataList;
    }
}
