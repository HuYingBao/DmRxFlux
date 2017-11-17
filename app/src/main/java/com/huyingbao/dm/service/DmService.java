package com.huyingbao.dm.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.huyingbao.rxflux2.base.service.BaseService;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 隔29分钟连接一次服务器,保持session不过期
 * Created by liujunfeng on 2016/11/21.
 */
public class DmService extends BaseService {
    private Disposable mDisposable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void initInjector() {
        mServiceComponent.inject(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.v("服务创建");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.v("服务启动");
        startRepeatConnectServer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.v("服务销毁,停止session保活,停止网络状态监听");
        stopRepeatConnectServer();
    }


    /**
     * 开始定时HTTP请求服务器
     * TODO 心跳未加失败处理
     */
    private void startRepeatConnectServer() {
        mDisposable = Observable.interval(29, TimeUnit.MINUTES)
                .flatMap(aLong -> mHttpApi.connectServer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpResponse -> {
                });
    }

    /**
     * 停止定时HTTP请求服务器
     */
    private void stopRepeatConnectServer() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }
}