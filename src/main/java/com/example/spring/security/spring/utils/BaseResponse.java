package com.example.spring.security.spring.utils;



/**
 * @ClassName:Base_Response
 * @Description:统一返回封装
 * @Author:lxw
 * @Date:2019/8/19 15:20
 * @Version: 1.0
 **/
public class BaseResponse<T> {

    private String requestId;
    /**
     * 业务响应码
     */
    private int code = StatusCode.SUCCESS ;
    /**
     * 返回信息描述
     */
    private String msg ;
    /**
     * 异常code
     */
    private String errCode="" ;

    /**
     * 返回参数
      */
    private T data;

    /**
     * 设置成功数据
     * @param msg 提示信息
     * @param data 数据
     */
    public void setSuccess(String msg,T data,String requestId) {
        this.requestId=requestId ;
        this.setCode(StatusCode.SUCCESS);
        this.setMsg(msg);
        this.setData(data);

    }

    /**
     * 设置失败数据
     * @param msg 提示信息
     * @param data 数据
     */
    public void setFail(String msg,T data,String errCode,String requestId) {
        this.requestId=requestId ;
        this.setCode(StatusCode.FAIL);
        this.setMsg(msg);
        this.setData(data);
        this.setErrCode(errCode);
    }


    /**
     * 响应状态码
     */
    private static class StatusCode {
        // 成功
        static final int SUCCESS = 0;
        // 失败
        static final int FAIL = -1;
        // 信息过期
        static final int EXPIRE = -2;
    }


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "requestId='" + requestId + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", errCode='" + errCode + '\'' +
                ", data=" + data +
                '}';
    }

    public BaseResponse() {
    }

    public BaseResponse(String requestId, int code, String msg, String errCode, T data) {
        this.requestId = requestId;
        this.code = code;
        this.msg = msg;
        this.errCode = errCode;
        this.data = data;
    }
}
