package com.kimson.kcframeofandroid.model;

/**
 * Created by zhujianheng on 5/30/16.
 */
public class Result<D> {
    /**
     * 结果是否正确*
     *
     * @return
     */
    public boolean isSuccess() {
        return this.status != null && this.status.equals("success");
    }

    private String status;
    private int code;
    private String msg;
    private D data;
    private int total;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
