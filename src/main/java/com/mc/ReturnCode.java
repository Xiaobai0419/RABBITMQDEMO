package com.mc;

import org.springframework.stereotype.Component;

/** 
 * ajax统一返回对象
* @author  zcy
* @E-mail: 
* @date 创建时间：2017年3月20日11:31:35
* @version 1.0   
*/
@Component
public class ReturnCode {
	
    private boolean success;
    private String msg;
    private Object obj;
    
    public final static String ACHIEVE_SUCCESS = "获取成功！";
    public final static String ACHIEVE_FAILED = "获取失败！";
    public final static String ADD_SUCCESS = "添加成功！";
    public final static String ADD_FAILED = "添加失败！";
    public final static String UPDATE_SUCCESS = "修改成功！";
    public final static String UPDATE_FAILED = "修改失败！";
    public final static String DELETE_SUCCESS = "删除成功！";
    public final static String DELETE_FAILED = "删除失败！";
    public final static String SAVE_SUCCESS = "保存成功！";
    public final static String SAVE_FAILED = "保存失败！";
    public final static String ALREADY_EXISTS = "该对象已存在！";
    public final static String SELECT_SUCCESS = "查询成功！";
    public final static String SELECT_FAILED = "查询失败！";
    public final static String NOT_EXITS = "没有符合条件的记录！";
    
    public ReturnCode(){
    	this.success = false;
    }
    
    public ReturnCode(boolean success, String message, Object obj){
    	this.msg = message;
    	this.success = success;
    	this.obj = obj;
    }
  
    public boolean getSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Object getObj() {
		return obj;
	}


	public void setObj(Object obj) {
		this.obj = obj;
	}


	@Override
    public String toString() {
        return "ReturnCode [success=" + success + ", msg=" + msg + ", obj="
                + obj + "]";
    }
}
