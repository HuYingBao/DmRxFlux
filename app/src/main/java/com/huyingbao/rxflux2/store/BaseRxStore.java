package com.huyingbao.rxflux2.store;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.rxflux2.model.HttpResponse;
import com.huyingbao.rxflux2.model.Page;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.dispatcher.Dispatcher;

import java.util.List;

/**
 * store 父类
 * Created by liujunfeng on 2016/12/20.
 */
public abstract class BaseRxStore extends RxStore {

    public BaseRxStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    /**
     * 获取response中封装的page中的list数据
     *
     * @param httpHttpResponse
     * @param <T>
     * @return
     */
    protected <T> List<T> getRightPageListResponse(@NonNull HttpResponse<Page<T>> httpHttpResponse) {
        return getPageList(getRightResponse(httpHttpResponse));
    }

    /**
     * 从返回数据中取出需要的信息
     *
     * @param httpHttpResponse
     * @param <T>
     * @return
     */
    protected <T> T getRightResponse(@NonNull HttpResponse<T> httpHttpResponse) {
        return TextUtils.equals(httpHttpResponse.getReturnCode(), Constants.SUCCESS_CODE) && httpHttpResponse.getResult() != null
                ? httpHttpResponse.getResult() : null;
    }

    /**
     * 从返回数据中取出需要的信息
     *
     * @param page
     * @param <T>
     * @return
     */
    private <T> List<T> getPageList(Page<T> page) {
        return page != null && CommonUtils.isListAble(page.getRows()) ? page.getRows() : null;
    }
}
