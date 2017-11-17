package com.huyingbao.rxflux2.model;

/**
 * 接口返回封装
 * Created by liujunfeng on 2017/5/26.
 */
public class HttpResponse<T> {

    /**
     * msg : 操作成功。
     * returnCode : 0
     */
    private String msg;
    private String returnCode;
    private T result;

    public HttpResponse(String returnCode, String msg) {
        this.returnCode = returnCode;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
