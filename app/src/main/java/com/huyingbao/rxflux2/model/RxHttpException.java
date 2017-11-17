package com.huyingbao.rxflux2.model;


/**
 * 自定义HttpException
 * Created by liujunfeng on 2016/11/26.
 */
public class RxHttpException extends Exception {
    private final String mCode;
    private final String mMessage;
    private final transient HttpResponse mResponse;

    public RxHttpException(HttpResponse response) {
        super("HTTP " + response.getReturnCode() + " " + response.getMsg());
        this.mCode = response.getReturnCode();
        this.mMessage = response.getMsg();
        this.mResponse = response;
    }

    public RxHttpException(String code, String message) {
        super("HTTP " + code + " " + message);
        this.mCode = code;
        this.mMessage = message;
        this.mResponse = null;
    }

    /**
     * HTTP status mCode.
     */
    public String code() {
        return mCode;
    }

    /**
     * HTTP status mMessage.
     */
    public String message() {
        return mMessage;
    }

    /**
     * The full HTTP mResponse. This may be null if the exception was serialized.
     */
    public HttpResponse<?> response() {
        return mResponse;
    }
}
