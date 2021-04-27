package com.example.spring.security.spring.exception;

/**
 * @ClassName:Enum
 * @Description:TODO
 * @Author:Hezeyu
 * @Date:2019/9/10 16:38
 * @Version: 1.0
 **/

public enum ErrCode {
    //系统异常
    sysException("01001"),
    //系统权限异常
    sysPowerException("02001"),
    //设备权限异常
    devPowerException("02002"),
    //缺少参数异常
    paramLackException("03001"),
    //参数验证异常
    paramVerifyException("03002"),
    //添加失败
    insetException("03004"),
    //修改失败
    updateException("03005"),
    //删除失败
    delException("03006"),
    //业务异常
    businessException("04001"),
    //cmq异常
    cmqException("05001"),
    //ckafka异常
    ckafkaException("06001"),

    //登录失效
    loginException("403");




    private String errCode;

    private ErrCode(String errCode){
        this.errCode=errCode;
    }


    public String getErrCode() {
        return errCode;
    }
}
