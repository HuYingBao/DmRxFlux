package com.huyingbao.dm.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseRxFluxFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.login.store.LoginStore;
import com.huyingbao.dm.ui.main.MainActivity;
import com.huyingbao.rxflux2.util.ServerUtils;
import com.huyingbao.rxflux2.store.RxStore;
import com.huyingbao.rxflux2.store.RxStoreChange;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 * Created by liujunfeng on 2017/1/1.
 */
public class LoginFragment extends BaseRxFluxFragment {
    @Inject
    LoginStore mLoginStore;

    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_login_server)
    TextView mTvLoginServer;
    @BindView(R.id.cb_login_auto)
    CheckBox mCbLoginAuto;
    @BindView(R.id.tv_login_server_in)
    TextView mTvLoginServerIn;
    @BindView(R.id.tv_login_server_out)
    TextView mTvLoginServerOut;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setServer();
        boolean autoBoolean = mLocalStorageUtils.getBoolean(ActionsKeys.IS_LOGIN_AUTO, true);
        mEtPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.to_login || id == EditorInfo.IME_NULL) {
                login();
                return true;
            }
            return false;
        });
        mEtUserName.setText(mLocalStorageUtils.getString(ActionsKeys.NAME, null));
        if (autoBoolean) mEtPassword.setText(mLocalStorageUtils.getString(ActionsKeys.PWD, null));
        mCbLoginAuto.setChecked(autoBoolean);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getRxAction().getType()) {
            case Actions.LOGIN:
                startActivity(MainActivity.newIntent(mContext));
                ((AppCompatActivity) mContext).finish();
                break;
            case Actions.CLICKED_SERVER_SET:
                setServer();
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return Collections.singletonList(mLoginStore);
    }

    @OnClick(R.id.tv_login_server)
    public void showServerSetFragment() {
        ServerSetFragment.newInstance().show(getChildFragmentManager(), ServerSetFragment.class.getSimpleName());
    }

    @OnClick(R.id.btn_login)
    public void login() {
        //验证用户名和密码
        mEtPassword.setError(null);
        mEtUserName.setError(null);
        String name = mEtUserName.getText().toString();
        String pwd = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mEtUserName.requestFocus();
            mEtUserName.setError(getString(R.string.error_invalid_user_name));
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            mEtPassword.requestFocus();
            mEtPassword.setError(getString(R.string.error_invalid_password));
            return;
        }
        //设置是否自动登录
        mLocalStorageUtils.setBoolean(ActionsKeys.IS_LOGIN_AUTO, mCbLoginAuto.isChecked());
        //登录
        mActionCreator.login(mContext, name, pwd, mLocalStorageUtils.getString(ActionsKeys.CHANNEL_ID, ""));
    }

    /**
     * 设置服务器地址
     */
    private void setServer() {
        //mTvLoginServer.setText("服务器设置" + (ServerUtils.getServerState() ? "(内)" : "(外)"));
        mTvLoginServerIn.setText("内网服务器：" + ServerUtils.getServerInUrl());
        mTvLoginServerOut.setText("外网服务器：" + ServerUtils.getServerOutUrl());
    }
}

