package com.huyingbao.dm.ui.login.store;

import com.alibaba.sdk.android.man.MANServiceProvider;
import com.huyingbao.dm.ui.main.model.User;
import com.huyingbao.rxflux2.action.RxAction;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;
import com.huyingbao.rxflux2.store.BaseRxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;
import com.huyingbao.rxflux2.util.AppUtils;
import com.huyingbao.rxflux2.util.LocalStorageUtils;
import com.huyingbao.rxflux2.util.ServiceUtils;

import javax.inject.Inject;

/**
 * 注册
 * Created by liujunfeng on 2017/2/7.
 */
public class LoginStore extends BaseRxStore {
    @Inject
    LocalStorageUtils mLocalStorageUtils;

    public LoginStore(Dispatcher dispatcher) {
        super(dispatcher);
        AppUtils.getApplicationComponent().inject(this);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.LOGIN:
                handleLogin(action);
                break;
            default:
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    /**
     * 登录
     *
     * @param action
     */
    private void handleLogin(RxAction action) {
        User user = getRightResponse(action.get(ActionsKeys.RESPONSE));
        if (user == null) return;
        //保存登录状态
        mLocalStorageUtils.setBoolean(ActionsKeys.IS_LOGIN, true);
        //保存登录账号
        mLocalStorageUtils.setString(ActionsKeys.NAME, action.get(ActionsKeys.NAME));
        //保存登录密码
        mLocalStorageUtils.setString(ActionsKeys.PWD, action.get(ActionsKeys.PWD));
        //保存用户信息
        mLocalStorageUtils.setUser(user);
        //开始后台服务
        ServiceUtils.startTimerCheck(AppUtils.getApplication());
        //登录埋点
        MANServiceProvider.getService().getMANAnalytics().updateUserAccount(user.getAlias(), "" + user.getId());
    }
}
