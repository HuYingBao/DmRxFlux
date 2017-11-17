package com.huyingbao.dm.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.dialogfragment.BaseDialogFragment;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.ServerUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 输入对话框
 * Created by liujunfeng on 2017/5/9.
 */
public class ServerSetFragment extends BaseDialogFragment {
    @BindView(R.id.et_server_in)
    EditText mEtServerIn;
    @BindView(R.id.et_server_out)
    EditText mEtServerOut;
//    @BindView(R.id.cb_server_in)
//    CheckBox mCbServerIn;
//    @BindView(R.id.cb_server_out)
//    CheckBox mCbServerOut;

    public static ServerSetFragment newInstance() {
        return new ServerSetFragment();
    }


    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_server_set;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mEtServerIn.setText(ServerUtils.getServerInUrl());
        mEtServerOut.setText(ServerUtils.getServerOutUrl());

//        mCbServerIn.setChecked(ServerUtils.getServerState());
//        mCbServerOut.setChecked(!ServerUtils.getServerState());
//
//        mCbServerIn.setOnCheckedChangeListener((compoundButton, b) -> {
//            mCbServerOut.setChecked(!b);
//        });
//        mCbServerOut.setOnCheckedChangeListener((compoundButton, b) -> {
//            mCbServerIn.setChecked(!b);
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = getResources().getDimensionPixelSize(R.dimen.dp_300);
        window.setAttributes(layoutParams);
    }

    @OnClick(R.id.tv_cancel)
    public void onCancelClicked() {
        CommonUtils.closeInputByDialogFragment(mContext, mEtServerOut);
        dismiss();
    }

    @OnClick(R.id.tv_ok)
    public void onOkClicked() {
        String serverIn = mEtServerIn.getText().toString();
        String serverOut = mEtServerOut.getText().toString();
        if (TextUtils.isEmpty(serverIn)) {
            showShortToast("请输入内网服务器地址！");
            return;
        }
        if (TextUtils.isEmpty(serverOut)) {
            showShortToast("请输入外网服务器地址！");
            return;
        }
        //ServerUtils.setServerState(mCbServerIn.isChecked());
        ServerUtils.setServerInUrl(serverIn);
        ServerUtils.setServerOutUrl(serverOut);
        mActionCreator.postLocalAction(Actions.CLICKED_SERVER_SET);
        CommonUtils.closeInputByDialogFragment(mContext, mEtServerOut);
        dismiss();
    }
}
