/**@Description
 * @filename:ResultException.java
 * @author:Li XiangWei
 * @version:1.0
 * @date:下午6:05:26
 * 
 * 修改历史
 * 修改日期          修改人员            版本        		修改内容
 * ---------------------------------------------------
 * 2018年12月11日    Li XiangWei	1.0	 		 新增
 * 
 * 版权:   	版权所有(C)2018
 * 公司:		c
 * 
 */
package com.example.spring.security.spring.exception;

/**
 * 自定义业务异常处理
 *
 */
public class BusinessException extends RuntimeException {

	String errCode;

	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}


	public BusinessException(String message,ErrCode errCode) {
		super(message);
        this.errCode=errCode.getErrCode();
		// TODO Auto-generated constructor stub
	}

	public BusinessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }


}
